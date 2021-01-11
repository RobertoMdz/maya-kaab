package com.coopera.mayakaab.models;

import android.graphics.drawable.Drawable;

public class GaleriaImagenModel {
    String id;
    int imagen;

    public GaleriaImagenModel(String id, int imagen) {
        this.id = id;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
