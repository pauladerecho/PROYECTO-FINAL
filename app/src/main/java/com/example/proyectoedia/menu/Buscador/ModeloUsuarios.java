package com.example.proyectoedia.menu.Buscador;

public class ModeloUsuarios {

    //--> Se tiene que poner igual que está en firebase
    String name, email, search, imagen, portada, uid;

    public ModeloUsuarios() {
    }

    public ModeloUsuarios(String name, String email, String search, String imagen, String portada, String uid) {
        this.name = name;
        this.email = email;
        this.search = search;
        this.imagen = imagen;
        this.portada = portada;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

