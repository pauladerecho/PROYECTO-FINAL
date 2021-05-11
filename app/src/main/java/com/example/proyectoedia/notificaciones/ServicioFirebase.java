package com.example.proyectoedia.notificaciones;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class ServicioFirebase extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        String refrescarToken = FirebaseInstanceId.getInstance().getToken();
        if(usuario != null){

            actualizarToken(refrescarToken);
        }
    }

    private void actualizarToken(String refrescarToken) {

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");

        Token token = new Token(refrescarToken);
        ref.child(usuario.getUid()).setValue(token);
    }
}
