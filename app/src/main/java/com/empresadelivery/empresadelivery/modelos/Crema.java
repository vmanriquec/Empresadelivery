package com.empresadelivery.empresadelivery.modelos;

public class Crema {
    int idcrema;
    String nombrecrema;

    @Override
    public String toString() {
        return "Crema{" +
                "nombrecrema='" + nombrecrema + '\'' +
                '}';
    }

    public int getIdcrema() {
        return idcrema;
    }

    public void setIdcrema(int idcrema) {
        this.idcrema = idcrema;
    }

    public String getNombrecrema() {
        return nombrecrema;
    }

    public void setNombrecrema(String nombrecrema) {
        this.nombrecrema = nombrecrema;
    }

    public String getEstadocrema() {
        return estadocrema;
    }

    public void setEstadocrema(String estadocrema) {
        this.estadocrema = estadocrema;
    }

    public Crema(int idcrema, String nombrecrema, String estadocrema) {
        this.idcrema = idcrema;
        this.nombrecrema = nombrecrema;
        this.estadocrema = estadocrema;
    }

    String estadocrema;
}
