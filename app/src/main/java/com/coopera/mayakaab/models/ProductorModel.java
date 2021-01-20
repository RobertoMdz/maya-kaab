package com.coopera.mayakaab.models;

public class ProductorModel {

    String id;
    String nombre;
    String comunidad;
    String telefono;
    String referencia;

    public ProductorModel(String id, String nombre, String comunidad, String telefono, String referencia) {
        this.id = id;
        this.nombre = nombre;
        this.comunidad = comunidad;
        this.telefono = telefono;
        this.referencia = referencia;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
