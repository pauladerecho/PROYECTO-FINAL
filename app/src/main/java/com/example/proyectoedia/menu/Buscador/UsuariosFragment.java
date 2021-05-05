package com.example.proyectoedia.menu.Buscador;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoedia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.firebase.database.FirebaseDatabase.*;


public class UsuariosFragment extends Fragment {
    RecyclerView recyclerView;
    AdaptadorUsuarios adaptadorUsuarios;
    List<ModeloUsuarios> usuariosList;
    FirebaseUser firebaseUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // -->> El inflador
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        //-->El recycler view de la lista de usuarios
        recyclerView = view.findViewById(R.id.usuarios_recyclerView);

        //--> las propiedades del recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //-->>ArrayList de usuarios
        usuariosList = new ArrayList<>();

        //-->> recorrer todos los usuarios para listarlos

        getTodosLosUsuarios();

        return view;

    }
    private void getTodosLosUsuarios() {

        //-->> Traernos los usuarios de FireBase:

         final FirebaseUser fUser;
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        //-->>Conseguir la informacion de esos usuarios en firebase:
        DatabaseReference ref;
        ref = getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT) //-->> Hace falta esto para que coja bien los IDs de la BBDD de fireBase
                                                            //-->> Y además poner 'Objects' en el if

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuariosList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ModeloUsuarios modeloUsuarios = ds.getValue(ModeloUsuarios.class);

                    if(!Objects.equals(modeloUsuarios.getUid(), fUser.getUid())){
                        usuariosList.add(modeloUsuarios);
                    }

                    //-->> Adaptador
                    adaptadorUsuarios = new AdaptadorUsuarios(getActivity(),usuariosList);

                    recyclerView.setAdapter(adaptadorUsuarios);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}