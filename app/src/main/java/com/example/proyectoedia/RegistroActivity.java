package com.example.proyectoedia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    //Vistas
    EditText mEmailEt, mContrasenaET;
    Button mRegistroBtn;
    //Progress bar para el registro
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //---->>ACCIONES DE MENÚ + TITULO<<---//

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Crear cuenta");

        //Botón volver

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        //Vista boton y textfield
        mEmailEt = findViewById(R.id.emailEt);
        mContrasenaET = findViewById(R.id.contrasenaEt);
        mRegistroBtn = findViewById(R.id.registro_btn);

        //Vista ProgressBar
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando usuario, espere...");


        //ON CLICK

        mRegistroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Guardamos los datos en un String
                String email = mEmailEt.getText().toString().trim();
                String contrasena = mContrasenaET.getText().toString().trim();

                //Validacion

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { //-- Si el email no es igual

                    //Alertamos del error
                    mEmailEt.setError("El e-mail no es válido");
                    mEmailEt.setFocusable(true);

                }else if(contrasena.length()<6){ //-- Si la contrasena es más corta que 6 digitos

                    //Alertamos del error
                    mContrasenaET.setError("La contraseña es demasiado corta, debe contener al menos 6 caracteres");
                    mContrasenaET.setFocusable(true);

                }else{
                    registrarUsuario(email,contrasena);
                }
            }
        });

    }

    private void registrarUsuario(String email, String contrasena) {
        //-- Si el email y la contrasena es valida, mostramos la barra de progreso y el usuario se registra


    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed(); //-->> Para ir al activity anterior
        return super.onSupportNavigateUp();
    }
}