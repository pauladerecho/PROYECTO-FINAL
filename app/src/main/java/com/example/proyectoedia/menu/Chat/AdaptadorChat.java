package com.example.proyectoedia.menu.Chat;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoedia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.MyHolder> {

    private static  final  int MSG_TYPE_LEFT = 0;
    private static  final  int MSG_TYPE_RIGHT = 1;
    Context context;
    List<ModeloChat> chatList;
    String imagenUrl;

    FirebaseUser fUser;

    public AdaptadorChat(Context context, List<ModeloChat> chatList, String imagenUrl) {
        this.context = context;
        this.chatList = chatList;
        this.imagenUrl = imagenUrl;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //Condicional para controlar el fondo dependiendo si envias o recibes mensaje

        if(i==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.columna_derecha_chat, viewGroup, false);
            return  new MyHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.columna_izquierda_chat, viewGroup, false);
            return  new MyHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        //-->>Recuperar los datos

        String mensaje = chatList.get(i).getMensaje();
        String fechaHora = chatList.get(i).getHoraDia();

        //Convertir la fecha y hora en el formato Date

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

        try {
            calendar.setTimeInMillis(Long.parseLong(fechaHora));
        }catch (Exception e){
            e.printStackTrace();;
        }

        String dateTime = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();

        //settear los datos

        holder.mensajeTv.setText(mensaje);
        holder.fechaHoraTv.setText(dateTime);
        try{
            Picasso.get().load(imagenUrl).into(holder.perfilChatIv);
        }catch (Exception e){
        }

        //settear el estado del mensaje

        if(i==chatList.size()-1){
          if(chatList.get(i).isVisto()){
              holder.vistoTv.setText("Visto");
          }else{
              holder.vistoTv.setText("Entregado");
          }
        }else{

            holder.vistoTv.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {

        //--->>comprobar si el usuario existe por su ID

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getEnviado().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{

        //Vistas
        ImageView perfilChatIv;
        TextView mensajeTv, fechaHoraTv, vistoTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //Inicializacion de vistas

            perfilChatIv = itemView.findViewById(R.id.perfilChatIv);
            mensajeTv = itemView.findViewById(R.id.mensajeTv);
            fechaHoraTv = itemView.findViewById(R.id.fechaHoraTv);
            vistoTv = itemView.findViewById(R.id.vistoTv);

        }
    }
}
