package com.example.proyectoedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //VISTAS//
    Button mRegistroBtn,mLoginBtn; //--> la M es para que sepamos que es del main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //VISTAS de los botones //
        mRegistroBtn = findViewById(R.id.registro_btn);
        mLoginBtn = findViewById(R.id.login_btn);

        //El onclick

        mRegistroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Para ir a RegistroActivity

                startActivity(new Intent(MainActivity.this,RegistroActivity.class));

            }
        });

    }
}