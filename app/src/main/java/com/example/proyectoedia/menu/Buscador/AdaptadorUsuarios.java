package com.example.proyectoedia.menu.Buscador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoedia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.MyHolder>{

    Context context;
    List<ModeloUsuarios> usuariosList;

    //--->>constructor
    public AdaptadorUsuarios(Context context, List<ModeloUsuarios> usuariosList) {
        this.context = context;
        this.usuariosList = usuariosList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //-->>Inflador del layout lista usuarios

        View view = LayoutInflater.from(context).inflate(R.layout.lista_usuarios, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        //--> Traer los datos

        String imagenUsuario = usuariosList.get(i).getImagen();

        String nombreUsuario  = usuariosList.get(i).getName();

        final String  emailUsuario = usuariosList.get(i).getEmail();

        //--> Settear los datos

        myHolder.mNombreTv.setText(nombreUsuario);
        myHolder.mEmailTv.setText(emailUsuario);


        try{
            Picasso.get().load(imagenUsuario)
                    .placeholder(R.drawable.search_icon)
                    .into(myHolder.mAvatarIv);
        }catch (Exception e){

        }

        //-->>On Click

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+emailUsuario,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarIv;
        TextView mNombreTv, mEmailTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNombreTv = itemView.findViewById(R.id.nombreTv);
            mEmailTv = itemView.findViewById(R.id.emailTv);

        }
    }

}
