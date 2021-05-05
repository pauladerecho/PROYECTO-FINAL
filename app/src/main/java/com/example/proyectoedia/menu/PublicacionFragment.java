package com.example.proyectoedia.menu;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectoedia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PublicacionFragment extends Fragment {

    FirebaseAuth firebaseAuth;

    //Permisos.
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;

    String[] permisosCamara;
    String[] permisosGaleria;

    private static final int IMAGEN_CAMARA_CODE = 300;
    private static final int IMAGEN_GALERIA_CODE = 400;


    EditText tituloEt, descripcionEt;
    ImageView imagenIv;
    Button publicarBtn;

    Uri image_rui;

    public PublicacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicacion, container, false);

        //Permisos.
        permisosCamara = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        permisosGaleria = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};


        firebaseAuth = FirebaseAuth.getInstance();
        comprobarEstadoUsuario();

        tituloEt = view.findViewById(R.id.pTitulo);
        descripcionEt = view.findViewById(R.id.pDescripcionEt);
        imagenIv = view.findViewById(R.id.pImagenIv);
        publicarBtn = view.findViewById(R.id.pPublicarBtn);

        //Para obtener la imagen de la camara o galeria.
        imagenIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSeleccionImagen();
            }
        });

        publicarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloEt.getText().toString().trim();
                String descripcion = descripcionEt.getText().toString().trim();
            }
        });
        return view;
    }

    private void mostrarSeleccionImagen() {

        String[] opciones = {"Camara", "Galeria"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elegir imagen de");

        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0){
                    //Si pulsas en la camara
                    if(!permisoCamara()){
                        solicitarPermisoCamara();
                    }else{
                        cogerDeLaCamara();
                    }
                }

                if(which == 1){
                    //Si pulsas en la galeria.
                    if(!permisoAlmacenamiento()){
                        solicitarPermisoAlmacenamiento();
                    }else{
                        cogerDeLaGaleria();
                    }
                }
            }
        });

        builder.create().show();
    }

    private void cogerDeLaGaleria() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGEN_GALERIA_CODE);
    }

    private void cogerDeLaCamara() {

        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "Selección temporal");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Descripción temporal");
        //image_rui = getContentResilver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_rui);
        startActivityForResult(intent, IMAGEN_CAMARA_CODE);

    }

    private boolean permisoAlmacenamiento(){

        boolean result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void solicitarPermisoAlmacenamiento(){
        ActivityCompat.requestPermissions(getActivity(), permisosGaleria, GALLERY_REQUEST_CODE);
    }


    private boolean permisoCamara(){

        boolean result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void solicitarPermisoCamara(){
        ActivityCompat.requestPermissions(getActivity(), permisosCamara, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onStart() {
        super.onStart();
        comprobarEstadoUsuario();
    }

    @Override
    public void onResume() {
        super.onResume();
        comprobarEstadoUsuario();
    }

    private void comprobarEstadoUsuario(){
        //Optenemos el usuario actual.
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){

        }else{
            //startActivity(new Intent(PublicacionFragment.class, MainActivity.class));
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.nav_publicacion).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //Resultado del permiso.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case CAMERA_REQUEST_CODE:{
                if(grantResults.length > 0){
                    boolean camaraAceptada = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean galeriaAceptada = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(camaraAceptada && galeriaAceptada){
                        cogerDeLaCamara();
                    }else{
                        Toast.makeText(getActivity(), "Los permisos de la camara y galeria son necesarios", Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }
            break;

            case GALLERY_REQUEST_CODE:{
                if(grantResults.length > 0){
                    boolean galeriaAceptada = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(galeriaAceptada){
                        cogerDeLaGaleria();
                    }else{
                        Toast.makeText(getActivity(), "Es necesario el permiso de la galeria", Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == Activity.RESULT_OK){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}