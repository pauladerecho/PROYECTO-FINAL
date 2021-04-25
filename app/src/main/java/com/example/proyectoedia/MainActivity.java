package com.example.proyectoedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  FirebaseUser user = mAuth.getCurrentUser();

        String email = user.getEmail();
        String uid = user.getUid();

        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("uid", uid);
        hashMap.put("telefono", "");
        hashMap.put("imagen", "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        reference.child(uid).setValue(hashMap);

*/
    }

    public void botonEntrar(View v){

        Intent i = new Intent(this, PerfilActivity.class);
        startActivity(i);
    }
}