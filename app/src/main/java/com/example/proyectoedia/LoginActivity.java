package com.example.proyectoedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    //Vistas
    EditText mEmailEt, mContrasenaEt;
    TextView mnoestar_registradoTv;
    Button mLoginBtn;

    //Declarar fireBase
    private FirebaseAuth mAuth;

    //Texto de progreso

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //---->>ACCIONES DE MENÚ + TITULO<<---//

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");

        //Botón volver

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        //Inicializarlo
        mEmailEt = findViewById(R.id.emailEt);
        mContrasenaEt = findViewById(R.id.contrasenaEt);
        mnoestar_registradoTv = findViewById(R.id.noestar_registradoTv);
        mLoginBtn = findViewById(R.id.loginBtn);
        //--Inicializar el dialogo de progreso

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando...");

        //Inicializar la variable de fireBase
        mAuth = FirebaseAuth.getInstance();

        // -- ON CLICK DEL LOGIN
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmailEt.getText().toString();
                String contrasena = mContrasenaEt.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ //-- Si el correo no coincide con la estructura de un email:

                    //--Lanzamos el error
                    mEmailEt.setError("El correo no es válido");
                    mEmailEt.setFocusable(true);
                }else{

                    //-- Si el correo es valido:
                    loginUser(email,contrasena);
                }

            }
        });

        //No tener cuenta, onClick
        mnoestar_registradoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistroActivity.class));
            }
        });
    }

    private void loginUser(String email, String contrasena) {

        //--Vista del dialogo de progreso

        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Para que acabe el progressDialog
                            progressDialog.dismiss();

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            //Si el usuario se ha logueado, entra en la pagina de perfil
                            startActivity(new Intent(LoginActivity.this,PerfilActivity.class));
                            finish();
                        } else {
                            //Para que acabe el progressDialog
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "oh oh, algo salió mal.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Para que acabe el progressDialog
                progressDialog.dismiss();
                //Toast para que aparezca el mensaje
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed(); //-->> Para ir al activity anterior
        return super.onSupportNavigateUp();
    }
}