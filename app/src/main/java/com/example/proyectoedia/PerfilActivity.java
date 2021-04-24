    package com.example.proyectoedia;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.ActionBar;
    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.widget.TextView;

    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;

    public class PerfilActivity extends AppCompatActivity {

            //Autentificacion de FireBase

            FirebaseAuth firebaseAuth;

            //Vista
            TextView mperfilTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_perfil);

            //---->>ACCIONES DE MENÚ + TITULO<<---//

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Perfil");

            //Inicializar la autentificacion
            firebaseAuth = FirebaseAuth.getInstance();

            //Inicializar vista

            mperfilTv = findViewById(R.id.perfilTv);

            }

    ////----- VERIFICAR QUE EL USUARIO EXISTE-----///

    private void verificarUsuarios(){

            //--Obtener el usuario con fireBase:
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if(user != null){ //-- Si el usuario está en la bbdd de FireBase:
            mperfilTv.setText(user.getEmail());
            }else { //-- Sino, no está registrado en la app, vuelve a la pagina principal para que se registre

            startActivity(new Intent(PerfilActivity.this,MainActivity.class));
            finish();
            }
            }

    @Override
    protected void onStart() { //-->> Al iniciar, verificar si existe el usuario
            verificarUsuarios();
            super.onStart();
            }

            //--Inflador de opciones del menú


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu_main,menu);
            return super.onCreateOptionsMenu(menu);
        }

        //-- On click del menu


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
