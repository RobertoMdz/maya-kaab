package com.coopera.mayakaab.models;

public class Caja {

    private String idcaja;
    private String cajaInicial;
    private String cajaFinal;
    private String fechaRegistro;

    public Caja(String idcaja, String cajaInicial, String cajaFinal, String fechaRegistro) {
        this.idcaja = idcaja;
        this.cajaInicial = cajaInicial;
        this.cajaFinal = cajaFinal;
        this.fechaRegistro = fechaRegistro;
    }

    public String getIdcaja() {
        return idcaja;
    }

    public void setIdcaja(String idcaja) {
        this.idcaja = idcaja;
    }

    public String getCajaInicial() {
        return cajaInicial;
    }

    public void setCajaInicial(String cajaInicial) {
        this.cajaInicial = cajaInicial;
    }

    public String getCajaFinal() {
        return cajaFinal;
    }

    public void setCajaFinal(String cajaFinal) {
        this.cajaFinal = cajaFinal;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
