package com.example.proyectoedia.menu.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;




import com.example.proyectoedia.R;

public class ChatActivity extends AppCompatActivity {

    //Vistas
    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView perfilIv;
    TextView nombreTv, estadoUsuarioTv;
    EditText mensajeEt;
    ImageButton enviarBtn;


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

    }
}