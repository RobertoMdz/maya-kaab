package com.coopera.mayakaab.models;

import java.util.ArrayList;

public class ComprasModel {

    private String mes;
    private String total_kilos_miel_comprada;
    private String total_compra_mes;
    private String cantidad_compra_mes;

    public ComprasModel(String mes, String total_kilos_miel_comprada, String total_compra_mes, String cantidad_compra_mes) {
        this.mes = mes;
        this.total_kilos_miel_comprada = total_kilos_miel_comprada;
        this.total_compra_mes = total_compra_mes;
        this.cantidad_compra_mes = cantidad_compra_mes;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getTotal_kilos_miel_comprada() {
        return total_kilos_miel_comprada;
    }

    public void setTotal_kilos_miel_comprada(String total_kilos_miel_comprada) {
        this.total_kilos_miel_comprada = total_kilos_miel_comprada;
    }

    public String getTotal_compra_mes() {
        return total_compra_mes;
    }

    public void setTotal_compra_mes(String total_compra_mes) {
        this.total_compra_mes = total_compra_mes;
    }

    public String getCantidad_compra_mes() {
        return cantidad_compra_mes;
    }

    public void setCantidad_compra_mes(String cantidad_compra_mes) {
        this.cantidad_compra_mes = cantidad_compra_mes;
    }
}
