package com.example.proyectoedia.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoedia.R;
import com.example.proyectoedia.menu.Buscador.AdaptadorUsuarios;
import com.example.proyectoedia.menu.Buscador.ModeloUsuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ModeloUsuarios modeloUsuarios;
    AdaptadorUsuarios adaptadorUsuarios;
    List<ModeloUsuarios> usuariosList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // -->> El inflador
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        //-->>Conseguir la informacion de esos usuarios:
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuariosList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ModeloUsuarios modeloUsuarios = ds.getValue(ModeloUsuarios.class);

                    if(!modeloUsuarios.getUid().equals(fUser.getUid())){
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

