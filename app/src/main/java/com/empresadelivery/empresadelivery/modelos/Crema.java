package com.empresadelivery.empresadelivery.modelos;

public class Crema {
    int idcrema;
    String nombrecrema;
    int idempresa;

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

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public Crema(int idcrema, String nombrecrema, String estadocrema, int idempresa) {
        this.idcrema = idcrema;
        this.nombrecrema = nombrecrema;
        this.estadocrema = estadocrema;
        this.idempresa = idempresa;
    }

    String estadocrema;
}
