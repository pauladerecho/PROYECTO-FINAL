package com.example.proyectoedia.menu.Chat;

import com.google.firebase.database.PropertyName;

public class ModeloChat {

    String mensaje, recibido, enviado, horaDia;
    boolean isVisto;

    public ModeloChat() {
    }

    public ModeloChat(String mensaje, String recibido, String enviado, String horaDia, boolean isVisto) {
        this.mensaje = mensaje;
        this.recibido = recibido;
        this.enviado = enviado;
        this.horaDia = horaDia;
        this.isVisto = isVisto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRecibido() {
        return recibido;
    }

    public void setRecibido(String recibido) {
        this.recibido = recibido;
    }

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }

    public String getHoraDia() {
        return horaDia;
    }

    public void setHoraDia(String horaDia) {
        this.horaDia = horaDia;
    }

    @PropertyName("isVisto")
    public boolean isVisto() {
        return isVisto;
    }
    @PropertyName("isVisto")
    public void setVisto(boolean visto) {
        this.isVisto = visto;
    }
}
