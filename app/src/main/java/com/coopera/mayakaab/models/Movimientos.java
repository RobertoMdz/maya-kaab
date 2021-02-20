package com.coopera.mayakaab.models;

public class Movimientos {
    private String fecha;
    private String total;
    private String mielKgs;

    public Movimientos(String fecha, String total, String mielKgs) {
        this.fecha = fecha;
        this.total = total;
        this.mielKgs = mielKgs;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMielKgs() {
        return mielKgs;
    }

    public void setMielKgs(String mielKgs) {
        this.mielKgs = mielKgs;
    }

}
