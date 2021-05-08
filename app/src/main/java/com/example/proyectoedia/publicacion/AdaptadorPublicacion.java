package com.example.proyectoedia.publicacion;

import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoedia.R;
import com.example.proyectoedia.ThereProfileActivity;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdaptadorPublicacion extends RecyclerView.Adapter<AdaptadorPublicacion.MyHolder> {

    Context context;
    List<ModeloPublicacion> publicacionLista;

    public AdaptadorPublicacion(Context context, List<ModeloPublicacion> publicacionLista) {
        this.context = context;
        this.publicacionLista = publicacionLista;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //-->>Inflador del layout lista publicaciones.

        View view = LayoutInflater.from(context).inflate(R.layout.filas_posts, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        //Traemos los datos.
        final String uid = publicacionLista.get(i).getUid();
        String uEmail = publicacionLista.get(i).getuEmail();
        String uName = publicacionLista.get(i).getuName();
        String uDp = publicacionLista.get(i).getuDp();
        String pId = publicacionLista.get(i).getpId();
        String pTitulo = publicacionLista.get(i).getpTitulo();
        String pDescripcion = publicacionLista.get(i).getpDescripcion();
        String pImagen = publicacionLista.get(i).getpImagen();
        String pTimeStamp = publicacionLista.get(i).getpTime();

        //Convertimos el tiempo a la fecha actual.
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        String pTiempo = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();


        myHolder.uNameTv.setText(uName);
        myHolder.pTimeTv.setText(pTiempo);
        myHolder.pTituloTv.setText(pTitulo);
        myHolder.pDescripcionTv.setText(pDescripcion);



        //Establecer usuario dp.
        try{
            Picasso.get().load(uDp).placeholder(R.drawable.imagen_por_defecto).into(myHolder.uImagenIv);
        }catch (Exception e){

        }

        //Establecer imagen del post
        //Si no hay imagen, entonces ocualtar ImageView.
        if(pImagen.equals("noImagen")){

            //Ocultar ImageView
            myHolder.pImagenIv.setVisibility(View.GONE);
        }else{
            try{
                Picasso.get().load(pImagen).into(myHolder.pImagenIv);
            }catch (Exception e){

            }
        }



        myHolder.opcionesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Mas", Toast.LENGTH_SHORT).show();
            }
        });

        myHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();
            }
        });

        myHolder.comentarioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Comentario", Toast.LENGTH_SHORT).show();
            }
        });

        myHolder.compartirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Compartir", Toast.LENGTH_SHORT).show();
            }
        });

        myHolder.perfilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ThereProfileActivity.class);
                intent.putExtra("uid", uid);
                context.startActivity(intent);
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
            compartirBtn = itemView.findViewById(R.id.compartirBtn);
            perfilLayout = itemView.findViewById(R.id.perfilLayout);


        }
    }
}
