package com.example.proyectoedia.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.proyectoedia.MainActivity;
import com.example.proyectoedia.menu.Buscador.UsuariosFragment;
import com.example.proyectoedia.menu.HomeFragment;
import com.example.proyectoedia.menu.NotificacionesFragment;
import com.example.proyectoedia.publicacion.PublicacionFragment;
import com.example.proyectoedia.R;
import com.example.proyectoedia.menu.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioActivity extends AppCompatActivity {

    //Autentificacion de FireBase
    FirebaseAuth firebaseAuth;

    ActionBar actionBar;
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //---->>ACCIONES DE MENÚ + TITULO<<---//
        actionBar = getSupportActionBar();

        //Inicializar la autentificacion
        firebaseAuth = FirebaseAuth.getInstance();

        //Boton de navegacion.
        navigationView = findViewById(R.id.navegacion);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);


        //Para que empieze por defecto en esta pantalla.
        actionBar.setTitle("Home");
        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, fragment1, "");
        ft1.commit();
    }

        private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //Cambios de los elementos de la barra de navegacion.
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        actionBar.setTitle("Home");
                        HomeFragment fragment1 = new HomeFragment();
                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                        ft1.replace(R.id.content, fragment1, "");
                        ft1.commit();
                        return true;

                    case R.id.nav_notificaciones:
                        actionBar.setTitle("Notificaciones");
                        NotificacionesFragment fragment2 = new NotificacionesFragment();
                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                        ft2.replace(R.id.content, fragment2, "");
                        ft2.commit();
                        return true;

                    case R.id.nav_publicacion:
                        actionBar.setTitle("Publicacion");
                        PublicacionFragment fragment3 = new PublicacionFragment();
                        FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                        ft3.replace(R.id.content, fragment3, "");
                        ft3.commit();
                        return true;

                  /*  case R.id.nav_ajustes:
                        actionBar.setTitle("Ajustes");
                        AjustesFragment fragment4 = new AjustesFragment();
                        FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                        ft4.replace(R.id.content, fragment4, "");
                        ft4.commit();
                        return true;*/

                    case R.id.nav_perfil:
                        actionBar.setTitle("Perfil");
                        PerfilFragment fragment5 = new PerfilFragment();
                        FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                        ft5.replace(R.id.content, fragment5, "");
                        ft5.commit();
                        return true;

                    case R.id.nav_buscador:
                        actionBar.setTitle("Buscador");
                        UsuariosFragment fragment6 = new UsuariosFragment();
                        FragmentTransaction ft6 = getSupportFragmentManager().beginTransaction();
                        ft6.replace(R.id.content, fragment6, "");
                        ft6.commit();
                        return true;
                }
                return false;
            }
        };

    ////----- VERIFICAR QUE EL USUARIO EXISTE-----///

    private void verificarUsuarios(){

        //--Obtener el usuario con fireBase:
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){ //-- Si el usuario está en la bbdd de FireBase:
            //mPerfilTv.setText(user.getEmail());
        }else { //-- Sino, no está registrado en la app, vuelve a la pagina principal para que se registre

            startActivity(new Intent(InicioActivity.this, MainActivity.class));
            finish();
        }
    }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
        }

        @Override
    protected void onStart() { //-->> Al iniciar, verificar si existe el usuario
            verificarUsuarios();
            super.onStart();
            }


}