package com.example.proyectoedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    //TextView mPerfil;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Perfil");

        //Instanciamos firebaseAuth.
        firebaseAuth = FirebaseAuth.getInstance();

        //Boton de navegacion.
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        //Visitas de inicio.
        //mPerfil = findViewById(R.id.perfilTV);

        //Para que empieze por defecto en esta pantalla.
       /* actionBar.setTitle("Home");
        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, fragment1, "");
        ft1.commit();*/
        actionBar.setTitle("Perfil");
        PerfilFragment fragment5 = new PerfilFragment();
        FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
        ft5.replace(R.id.content, fragment5, "");
        ft5.commit();

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

                case R.id.nav_ajustes:

                    actionBar.setTitle("Ajustes");
                    AjustesFragment fragment4 = new AjustesFragment();
                    FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                    ft4.replace(R.id.content, fragment4, "");
                    ft4.commit();
                    return true;

                case R.id.nav_perfil:

                    actionBar.setTitle("Perfil");
                    PerfilFragment fragment5 = new PerfilFragment();
                    FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                    ft5.replace(R.id.content, fragment5, "");
                    ft5.commit();
                    return true;
            }
            return false;
        }
    };

    private void checkUsersStatus(){

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){

        }
    }
}