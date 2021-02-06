package com.coopera.mayakaab.models;

public class ComprasMielModel {

    private String idMiel;
    private String nombreProductor;
    private String localidad;
    private String codigo;
    private String numeroFolio;
    private String humedad;
    private String pesoBruto;
    private String pesoTara;
    private String precioCompra;
    private String totalKgs;
    private String totalPagar;
    private String numeroTambor;
    private String mielEntrante;
    private String mielFaltante;
    private String idProductor;
    private String idRegistro;
    private String idUsuario;
    private String fechaRegistro;
    private String tipoMiel;


    public ComprasMielModel(
            String idMiel,
            String nombreProductor,
            String localidad,
            String codigo,
            String numeroFolio,
            String humedad,
            String pesoBruto,
            String pesoTara,
            String precioCompra,
            String totalKgs,
            String totalPagar,
            String numeroTambor,
            String mielEntrante,
            String mielFaltante,
            String idProductor,
            String idRegistro,
            String idUsuario,
            String fechaRegistro,
            String tipoMiel) {

        this.idMiel = idMiel;
        this.nombreProductor = nombreProductor;
        this.localidad = localidad;
        this.codigo = codigo;
        this.numeroFolio = numeroFolio;
        this.humedad = humedad;
        this.pesoBruto = pesoBruto;
        this.pesoTara = pesoTara;
        this.precioCompra = precioCompra;
        this.totalKgs = totalKgs;
        this.totalPagar = totalPagar;
        this.numeroTambor = numeroTambor;
        this.mielEntrante = mielEntrante;
        this.mielFaltante = mielFaltante;
        this.idProductor = idProductor;
        this.idRegistro = idRegistro;
        this.idUsuario = idUsuario;
        this.fechaRegistro = fechaRegistro;
        this.tipoMiel = tipoMiel;
    }

    public String getIdMiel() {
        return idMiel;
    }

    public void setIdMiel(String idMiel) {
        this.idMiel = idMiel;
    }

    public String getNombreProductor() {
        return nombreProductor;
    }

    public void setNombreProductor(String nombreProductor) {
        this.nombreProductor = nombreProductor;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
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

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getPesoBruto() {
        return pesoBruto;
    }

    public void setPesoBruto(String pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public String getPesoTara() {
        return pesoTara;
    }

    public void setPesoTara(String pesoTara) {
        this.pesoTara = pesoTara;
    }

    public String getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(String precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getTotalKgs() {
        return totalKgs;
    }

    public void setTotalKgs(String totalKgs) {
        this.totalKgs = totalKgs;
    }

    public String getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(String totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getNumeroTambor() {
        return numeroTambor;
    }

    public void setNumeroTambor(String numeroTambor) {
        this.numeroTambor = numeroTambor;
    }

    public String getMielEntrante() {
        return mielEntrante;
    }

    public void setMielEntrante(String mielEntrante) {
        this.mielEntrante = mielEntrante;
    }

    public String getMielFaltante() {
        return mielFaltante;
    }

    public void setMielFaltante(String mielFaltante) {
        this.mielFaltante = mielFaltante;
    }

    public String getIdProductor() {
        return idProductor;
    }

    public void setIdProductor(String idProductor) {
        this.idProductor = idProductor;
    }

    public String getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getTipoMiel() {
        return tipoMiel;
    }

    public void setTipoMiel(String tipoMiel) {
        this.tipoMiel = tipoMiel;
    }
}
