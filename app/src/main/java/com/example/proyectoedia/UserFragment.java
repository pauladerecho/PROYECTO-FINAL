package com.example.proyectoedia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ImageView avatarIv, predeterminadaIv;
    TextView nombreTv, descripcionTv;
    FloatingActionButton boton_flotante;

    ProgressDialog pd;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        avatarIv = view.findViewById(R.id.avatar);
        predeterminadaIv = view.findViewById(R.id.converIv);
        descripcionTv = view.findViewById(R.id.descripcion);
        nombreTv = view.findViewById(R.id.nombre);
        boton_flotante = view.findViewById(R.id.boton_flotante);

        pd = new ProgressDialog(getActivity());

        //Optenemos la informacion del usuario actualmente registrado.
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Verificamos hasta que obtenemos los datos que necesitamos.
                for(DataSnapshot ds: snapshot.getChildren()){
                    String nombre = "" + ds.child("name").getValue();
                    String descripcion = "" + ds.child("descripcion").getValue();
                    String imagen = "" + ds.child("imagen").getValue();

                    nombreTv.setText(nombre);
                    descripcionTv.setText(descripcion);

                    try {
                        Picasso.get().load(imagen).into(avatarIv);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.ic_add_image).into(avatarIv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Boton flotante.
        boton_flotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditUserDialog();
            }
        });
        return view;
    }

    private void showEditUserDialog() {

        //Diferentes opciones.
        String opciones[] = {"Editar imagen", "Editar fondo", "Editar nombre", "Editar descripcion"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Opciones");

        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0){
                //Modificar la foto.
                    

                }else if (which == 1){
                //Modificar el fondo

                }else if (which == 2){
                //Modificar el nombre

                }else if (which == 3){
                //Modificar la descripcion

                }
            }
        });

        builder.create().show();
    }
}