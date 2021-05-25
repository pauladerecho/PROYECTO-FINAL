package com.example.proyectoedia.menu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoedia.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AjustesFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    public AjustesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ajustes, container, false);

        return view;
    }

    public void mostrarCambioContrasena(){

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ajustes, null);

        final EditText editTextContrasenaActual = view.findViewById(R.id.editTextContrasenaActual);
        final EditText editTextContrasenaNueva = view.findViewById(R.id.editTextContrasenaNueva);
        Button botonCambiarContrasena = view.findViewById(R.id.cambiarContrasena);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();
        //builder.show();

        botonCambiarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contrasenaAntigua = editTextContrasenaActual.getText().toString().trim();
                String contrasenaNueva = editTextContrasenaNueva.getText().toString().trim();
                
                if(TextUtils.isEmpty(contrasenaAntigua)){
                    Toast.makeText(getActivity(), "Introduzca la contraseña actual", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(contrasenaNueva.length() < 6){
                    Toast.makeText(getActivity(), "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.dismiss();
                actualizarContrasena(contrasenaAntigua, contrasenaNueva);
            }
        });
    }

    private void actualizarContrasena(String contrasenaAntigua, final String contrasenaNueva) {
        progressDialog.show();

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), contrasenaAntigua);
        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //user.actualizarContrasena(contrasenaNueva)
                        //        .addOn
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}