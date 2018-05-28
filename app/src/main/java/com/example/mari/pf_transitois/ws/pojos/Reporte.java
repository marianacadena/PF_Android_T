package com.example.mari.pf_transitois.ws.pojos;

/**
 * Created by Mari on 04/05/2018.
 */

public class Reporte {
    private int idReporte;
    private float longitud;
    private float latitud;
    private String nombreConductor;
    private String ciudad;
    private int folio;
    private int idConductor;
    private int idVechiculoCond;
    private int idVehiculoChoque;
    private String fechaHora;

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public int getIdVechiculoCond() {
        return idVechiculoCond;
    }

    public void setIdVechiculoCond(int idVechiculoCond) {
        this.idVechiculoCond = idVechiculoCond;
    }

    public int getIdVehiculoChoque() {
        return idVehiculoChoque;
    }

    public void setIdVehiculoChoque(int idVehiculoChoque) {
        this.idVehiculoChoque = idVehiculoChoque;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

}
