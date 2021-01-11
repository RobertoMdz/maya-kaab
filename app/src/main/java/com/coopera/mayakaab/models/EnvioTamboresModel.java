package com.coopera.mayakaab.models;

public class EnvioTamboresModel {

    String numCosecha;
    String codigo;
    String numeroFolio;
    String numeroTamborSubcentro;
    String kgsBruto;
    String tara;
    String kgsNeto;
    String numTambor;

    public EnvioTamboresModel(String numCosecha, String codigo, String numeroFolio, String numeroTamborSubcentro, String kgsBruto, String tara, String kgsNeto, String numTambor) {
        this.numCosecha = numCosecha;
        this.codigo = codigo;
        this.numeroFolio = numeroFolio;
        this.numeroTamborSubcentro = numeroTamborSubcentro;
        this.kgsBruto = kgsBruto;
        this.tara = tara;
        this.kgsNeto = kgsNeto;
        this.numTambor = numTambor;
    }

    public String getNumCosecha() {
        return numCosecha;
    }

    public void setNumCosecha(String numCosecha) {
        this.numCosecha = numCosecha;
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

    public String getNumeroTamborSubcentro() {
        return numeroTamborSubcentro;
    }

    public void setNumeroTamborSubcentro(String numeroTamborSubcentro) {
        this.numeroTamborSubcentro = numeroTamborSubcentro;
    }

    public String getKgsBruto() {
        return kgsBruto;
    }

    public void setKgsBruto(String kgsBruto) {
        this.kgsBruto = kgsBruto;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getKgsNeto() {
        return kgsNeto;
    }

    public void setKgsNeto(String kgsNeto) {
        this.kgsNeto = kgsNeto;
    }

    public String getNumTambor() {
        return numTambor;
    }

    public void setNumTambor(String numTambor) {
        this.numTambor = numTambor;
    }
}
