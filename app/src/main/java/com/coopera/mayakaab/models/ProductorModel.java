package com.coopera.mayakaab.models;

public class ProductorModel {

    String nombre;
    String comunidad;
    String mielEntera;
    String mielFalt;
    String telefono;

    public ProductorModel(String nombre, String comunidad, String mielEntera, String mielFalt, String telefono) {
        this.nombre = nombre;
        this.comunidad = comunidad;
        this.mielEntera = mielEntera;
        this.mielFalt = mielFalt;
        this.telefono = telefono;
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

    public String getMielEntera() {
        return mielEntera;
    }

    public void setMielEntera(String mielEntera) {
        this.mielEntera = mielEntera;
    }

    public String getMielFalt() {
        return mielFalt;
    }

    public void setMielFalt(String mielFalt) {
        this.mielFalt = mielFalt;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
