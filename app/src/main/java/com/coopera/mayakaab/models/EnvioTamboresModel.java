package com.coopera.mayakaab.models;

import java.util.ArrayList;

public class EnvioTamboresModel {

    String idEnvio;
    String numCosecha;
    String codigo;
    String numeroFolio;
    String numeroTamborSubcentro;
    String kgsBruto;
    String tara;
    String kgsNeto;
    String numTamborTix;
    String idUsuario;
    String fechaRegisro;

    public EnvioTamboresModel(String idEnvio, String numCosecha, String codigo, String numeroFolio, String numeroTamborSubcentro, String kgsBruto, String tara, String kgsNeto, String numTamborTix, String idUsuario, String fechaRegisro) {
        this.idEnvio = idEnvio;
        this.numCosecha = numCosecha;
        this.codigo = codigo;
        this.numeroFolio = numeroFolio;
        this.numeroTamborSubcentro = numeroTamborSubcentro;
        this.kgsBruto = kgsBruto;
        this.tara = tara;
        this.kgsNeto = kgsNeto;
        this.numTamborTix = numTamborTix;
        this.idUsuario = idUsuario;
        this.fechaRegisro = fechaRegisro;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
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

    public String getNumTamborTix() {
        return numTamborTix;
    }

    public void setNumTamborTix(String numTamborTix) {
        this.numTamborTix = numTamborTix;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaRegisro() {
        return fechaRegisro;
    }

    public void setFechaRegisro(String fechaRegisro) {
        this.fechaRegisro = fechaRegisro;
    }
}
