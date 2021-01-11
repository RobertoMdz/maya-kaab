package com.coopera.mayakaab.models;

public class MielConvencionalModel {
    String fecha;
    String productor;
    String comunidad;
    String precioBruto;
    String pt;
    String pn;
    String pc;
    String ti;
    String observaciones;
    String saldoFi;
    String saldoPorPagar;
    String autori;

    public MielConvencionalModel(String fecha, String productor, String comunidad, String precioBruto, String pt, String pn, String pc, String ti, String observaciones, String saldoFi, String saldoPorPagar, String autori) {
        this.fecha = fecha;
        this.productor = productor;
        this.comunidad = comunidad;
        this.precioBruto = precioBruto;
        this.pt = pt;
        this.pn = pn;
        this.pc = pc;
        this.ti = ti;
        this.observaciones = observaciones;
        this.saldoFi = saldoFi;
        this.saldoPorPagar = saldoPorPagar;
        this.autori = autori;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getPrecioBruto() {
        return precioBruto;
    }

    public void setPrecioBruto(String precioBruto) {
        this.precioBruto = precioBruto;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getTi() {
        return ti;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getSaldoFi() {
        return saldoFi;
    }

    public void setSaldoFi(String saldoFi) {
        this.saldoFi = saldoFi;
    }

    public String getSaldoPorPagar() {
        return saldoPorPagar;
    }

    public void setSaldoPorPagar(String saldoPorPagar) {
        this.saldoPorPagar = saldoPorPagar;
    }

    public String getAutori() {
        return autori;
    }

    public void setAutori(String autori) {
        this.autori = autori;
    }
}
