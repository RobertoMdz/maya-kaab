package com.coopera.mayakaab.models;

public class MielOrganicaModel {

    String emision;
    String codigo;
    String numeroFolio;
    String fecha;
    String nombre;
    String humedad;
    String precioBruto;
    String tara;
    String pn;
    String pu;
    String total;
    String numTambores;

    public MielOrganicaModel(String emision, String codigo, String numeroFolio, String fecha, String nombre, String humedad, String precioBruto, String tara, String pn, String pu, String total, String numTambores) {
        this.emision = emision;
        this.codigo = codigo;
        this.numeroFolio = numeroFolio;
        this.fecha = fecha;
        this.nombre = nombre;
        this.humedad = humedad;
        this.precioBruto = precioBruto;
        this.tara = tara;
        this.pn = pn;
        this.pu = pu;
        this.total = total;
        this.numTambores = numTambores;
    }

    public String getEmision() {
        return emision;
    }

    public void setEmision(String emision) {
        this.emision = emision;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNumeroFolio() {
        return numeroFolio;
    }

    public void setNumeroFolio(String numeroFolio) {
        this.numeroFolio = numeroFolio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getPrecioBruto() {
        return precioBruto;
    }

    public void setPrecioBruto(String precioBruto) {
        this.precioBruto = precioBruto;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getPu() {
        return pu;
    }

    public void setPu(String pu) {
        this.pu = pu;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNumTambores() {
        return numTambores;
    }

    public void setNumTambores(String numTambores) {
        this.numTambores = numTambores;
    }
}
