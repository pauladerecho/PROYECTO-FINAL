package com.example.proyectoedia.publicacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoedia.MainActivity;
import com.example.proyectoedia.R;
import com.example.proyectoedia.menu.perfil.PerfilListaPublicacionActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdaptadorPublicacion extends RecyclerView.Adapter<AdaptadorPublicacion.MyHolder> {

    Context context;
    List<ModeloPublicacion> publicacionLista;

    String miUid;

    private DatabaseReference likesRef;
    private DatabaseReference postsRef;

    boolean mProcesoLikes = false;

    public AdaptadorPublicacion(Context context, List<ModeloPublicacion> publicacionLista) {
        this.context = context;
        this.publicacionLista = publicacionLista;
        miUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        likesRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        postsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //-->>Inflador del layout lista publicaciones.

        View view = LayoutInflater.from(context).inflate(R.layout.filas_posts, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {

        //Traemos los datos.
        final String uid = publicacionLista.get(i).getUid();
        String uEmail = publicacionLista.get(i).getuEmail();
        String uName = publicacionLista.get(i).getuName();
        String uDp = publicacionLista.get(i).getuDp();
        final String pId = publicacionLista.get(i).getpId();
        String pTitulo = publicacionLista.get(i).getpTitulo();
        String pDescripcion = publicacionLista.get(i).getpDescripcion();
        final String pImagen = publicacionLista.get(i).getpImagen();
        String pTimeStamp = publicacionLista.get(i).getpTime();
        String pLikes = publicacionLista.get(i).getpLikes();//Total de likes de un post.

        //Convertimos el tiempo a la fecha actual.
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        String pTiempo = DateFormat.format("dd/MM/yyyy", calendar).toString();


        myHolder.uNameTv.setText(uName);
        myHolder.pTimeTv.setText(pTiempo);
        myHolder.pTituloTv.setText(pTitulo);
        myHolder.pDescripcionTv.setText(pDescripcion);
        myHolder.pLikesTv.setText(pLikes + "Me gusta");
        setLikes(myHolder, pId);


        //Establecer usuario dp.
        try{
            Picasso.get().load(uDp).placeholder(R.drawable.icon_person).into(myHolder.uImagenIv);
        }catch (Exception e){

        }

        //Establecer imagen del post
        //Si no hay imagen, entonces ocualtar ImageView.
        if(pImagen.equals("noImagen")){
            //Ocultar ImageView
            myHolder.pImagenIv.setVisibility(View.GONE);
        }else{

            //Mostrat ImageView
            myHolder.pImagenIv.setVisibility(View.VISIBLE);

            try{
                Picasso.get().load(pImagen).into(myHolder.pImagenIv);
            }catch (Exception e){

            }
        }



        myHolder.opcionesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarMasOpciones(myHolder.opcionesBtn, uid, miUid, pId, pImagen);
               // Toast.makeText(context, "Mas", Toast.LENGTH_SHORT).show();
            }
        });

        myHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();

                final int pLikes = Integer.parseInt(publicacionLista.get(i).getpLikes());
                mProcesoLikes = true;

                final String postId = publicacionLista.get(i).getpId();
                likesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(mProcesoLikes){
                            //Quitamos el like, que ya estaba.
                            if(dataSnapshot.child(postId).hasChild(miUid)){
                                postsRef.child(postId).child("pLikes").setValue(""+(pLikes-1));
                                likesRef.child(postId).child(miUid).removeValue();
                            }else {
                                //Añadimos un like.
                                postsRef.child(postId).child("pLikes").setValue(""+(pLikes+1));
                                likesRef.child(postId).child(miUid).setValue("Liked");
                            }
                            mProcesoLikes = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        myHolder.comentarioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Comentario", Toast.LENGTH_SHORT).show();
            }
        });

/*        myHolder.compartirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Compartir", Toast.LENGTH_SHORT).show();
            }
        });*/

        myHolder.perfilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PerfilListaPublicacionActivity.class);
                intent.putExtra("uid", uid);
                context.startActivity(intent);

            }
        });
    }

    private void setLikes(final MyHolder holder, final String postKey) {

            likesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.child(postKey).hasChild(miUid)){
                        holder.likeBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.like_color, 0, 0, 0);
                        holder.likeBtn.setText("Me gusta");

                    }else {
                        holder.likeBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                        holder.likeBtn.setText("Me gusta");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

    private void MostrarMasOpciones(ImageButton opcionesBtn, String uid, String miUid, final String pId, final String pImagen) {

        final PopupMenu popupMenu = new PopupMenu(context, opcionesBtn, Gravity.END);

        if(uid.equals(miUid)){
            popupMenu.getMenu().add(Menu.NONE, 0, 0, "Borrar");
            popupMenu.getMenu().add(Menu.NONE, 1, 0, "Editar");
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int id = menuItem.getItemId();

                if(id == 0){
                    //Si pulsa el boton se elimina.
                    comenzarBorrar(pId, pImagen);

                }else  if(id == 1){
                    //Si pulsa el boton se edita.
                    Intent intent = new Intent(context, PublicacionFragment.class);
                    intent.putExtra("key", "EditarPost");
                    intent.putExtra("EditarPostId", pId);
                    context.startActivity(intent);


                }
                return false;
            }
        });

        popupMenu.show();
    }

    private void comenzarBorrar(String pId, String pImagen) {

        //El post puede ser con o sin imagen.
        if(pImagen.equals("noImage")){
            borrarSinImagen(pId);
        }else{
            borrarConImagen(pId, pImagen);
        }
    }

    private void borrarConImagen(final String pId, String pImagen) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Borrando");

        StorageReference imagenRef = FirebaseStorage.getInstance().getReferenceFromUrl(pImagen);
        imagenRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Se borra la imagen, y luego de la base de datos.
                        Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("pId").equalTo(pId);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for(DataSnapshot ds: dataSnapshot.getChildren()){
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(context, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(context, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void borrarSinImagen(String pId) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Borrando");

        Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("pId").equalTo(pId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ds.getRef().removeValue();
                }
                Toast.makeText(context, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return publicacionLista.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView uImagenIv, pImagenIv;
        TextView uNameTv, pTimeTv, pTituloTv, pDescripcionTv, pLikesTv;
        ImageButton opcionesBtn;
        Button likeBtn, comentarioBtn, compartirBtn;
        LinearLayout perfilLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            uImagenIv = itemView.findViewById(R.id.uImagenIv);
            pImagenIv = itemView.findViewById(R.id.pImagenIv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.pTiempoTv);
            pTituloTv = itemView.findViewById(R.id.pTituloTv);
            pDescripcionTv = itemView.findViewById(R.id.pDescripcionTv);
            pLikesTv = itemView.findViewById(R.id.pLikesTv);
            opcionesBtn = itemView.findViewById(R.id.opcionesBtn);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            comentarioBtn = itemView.findViewById(R.id.comentarioBtn);
            //compartirBtn = itemView.findViewById(R.id.compartirBtn);
            perfilLayout = itemView.findViewById(R.id.perfilLayout);


        }
    }
}
