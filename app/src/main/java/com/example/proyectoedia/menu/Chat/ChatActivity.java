package com.example.proyectoedia.menu.Chat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoedia.MainActivity;
import com.example.proyectoedia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import static com.squareup.picasso.Picasso.get;

public class ChatActivity extends AppCompatActivity {

    //Vistas
    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView perfilIv;
    TextView nombreTv, estadoUsuarioTv;
    EditText mensajeEt;
    ImageButton enviarBtn;

    //Autentificacion de FireBase
    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersDbRef;

    //Comprobar si se ha visto el mensaje o no
    ValueEventListener vistoListener;
    DatabaseReference userRefVisto;

    List<ModeloChat> chatList;
    AdaptadorChat adaptadorChat;



    String idUsuario1;
    String idUsuario2;
    String imagenU2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Inicializar las vistas

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView = findViewById(R.id.chat_recyclerView);
        perfilIv = findViewById(R.id.perfilIv);
        nombreTv = findViewById(R.id.nameTv);
        estadoUsuarioTv = findViewById(R.id.estadoUsuarioTV);
        mensajeEt = findViewById(R.id.mensajeEt);
        enviarBtn = findViewById(R.id.enviarBtn);

        //El recycler

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        //propiedades del recycler

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Intent para recuperar a través del id del usuario el resto de su informacion
        Intent intent = getIntent();
        idUsuario2 = intent.getStringExtra("idUsuario");

        //Inicializar el fireBase
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        usersDbRef = firebaseDatabase.getReference("Users");

        //----->>BUSCAR en la BBDD para la informacion del usuario<<<---//

        Query usuarioQuery = usersDbRef.orderByChild("uid").equalTo(idUsuario2); //--ID del Usuario con el que hablas

        usuarioQuery.addValueEventListener(new ValueEventListener() { //-->>obtener el nombre y la foto del usuario

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    //Obtener los datos
                    String nombre =""+ ds.child("name").getValue();
                    imagenU2 =""+ ds.child("imagen").getValue();

                    //Settearlos
                    nombreTv.setText(nombre);
                    try{
                        //La imagen que llega la ponemos en el imageview

                        Picasso.get().load(imagenU2).placeholder(R.drawable.icon_default).into(perfilIv);

                    }catch(Exception e){ //--> La excepcion pone la foto por defecto si no tiene
                        get().load(R.drawable.icon_default).into(perfilIv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //OnClick para enviar el mensaje

        enviarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obtener el texto desde el editor
                String mensaje = mensajeEt.getText().toString().trim();

                //Comprobar si el mensaje está vacío o no
                if(TextUtils.isEmpty(mensaje)){

                    Toast.makeText(ChatActivity.this, "No puedes enviar un mensaje vacío!", Toast.LENGTH_SHORT).show();

                }else {

                    enviarMensaje(mensaje);
                }
            }
        });

        leerMensaje();

        mensajeVisto();
    }

    private void mensajeVisto() {

        userRefVisto = FirebaseDatabase.getInstance().getReference("Chats");
        vistoListener = userRefVisto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot ds: datasnapshot.getChildren()){

                    ModeloChat chat = ds.getValue(ModeloChat.class);
                    if(chat.getRecibido().equals(idUsuario1) && chat.getEnviado().equals(idUsuario2)){

                        HashMap<String, Object> hasVistoHasgMap = new HashMap<>();
                        hasVistoHasgMap.put("isVisto",true); /// no se si es isVisto o Visto
                        ds.getRef().updateChildren(hasVistoHasgMap);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void leerMensaje() {

        chatList = new ArrayList<>();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                chatList.clear();

                for (DataSnapshot ds: datasnapshot.getChildren()){
                    ModeloChat chat = ds.getValue(ModeloChat.class);
                    if(chat.getRecibido().equals(idUsuario1) && chat.getEnviado().equals(idUsuario2) ||
                            chat.getRecibido().equals(idUsuario2) && chat.getEnviado().equals(idUsuario1)){

                        chatList.add(chat);
                    }

                    //Adaptador
                    adaptadorChat = new AdaptadorChat(ChatActivity.this, chatList, imagenU2);
                    adaptadorChat.notifyDataSetChanged();
                    // settear adaptador al recycler

                    recyclerView.setAdapter(adaptadorChat);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void enviarMensaje(String mensaje) {

        String fechaHora = String.valueOf(System.currentTimeMillis());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("enviado",idUsuario1);
        hashMap.put("recibido", idUsuario2);
        hashMap.put("mensaje",mensaje);
        hashMap.put("horadia",fechaHora);
        hashMap.put("isVisto",false);

        databaseReference.child("Chats").push().setValue(hashMap);

        //Resetear el editor de texto
        mensajeEt.setText("");
    }

    //Verificar que el usuario existe:
    private void verificarUsuarios(){

        //--Obtener el usuario con fireBase:
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){ //-- Si el usuario está en la bbdd de FireBase:

            //mPerfilTv.setText(user.getEmail());

            idUsuario1 = user.getUid();//--> Usuario que está logueado en ese momento

        }else { //-- Sino, no está registrado en la app, vuelve a la pagina principal para que se registre

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        verificarUsuarios();
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        userRefVisto.removeEventListener(vistoListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.action_logout){
            firebaseAuth.signOut();
            verificarUsuarios();
        }

        return super.onOptionsItemSelected(item);
    }
}