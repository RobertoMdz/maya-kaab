package com.coopera.mayakaab.models;

import android.graphics.drawable.Drawable;

public class GaleriaImagenModel {
    String id;
    String imagen;

    public GaleriaImagenModel(String id, String imagen) {
        this.id = id;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
