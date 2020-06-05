package com.empresadelivery.empresadelivery.modelos;

public class Tipodepagoempresa {
int idtiposdepagoempresa;

    public int getIdtiposdepagoempresa() {
        return idtiposdepagoempresa;
    }

    public void setIdtiposdepagoempresa(int idtiposdepagoempresa) {
        this.idtiposdepagoempresa = idtiposdepagoempresa;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getIdtiposdepago() {
        return idtiposdepago;
    }

    public void setIdtiposdepago(int idtiposdepago) {
        this.idtiposdepago = idtiposdepago;
    }

    public String getEstadotiposdepago() {
        return estadotiposdepago;
    }

    public void setEstadotiposdepago(String estadotiposdepago) {
        this.estadotiposdepago = estadotiposdepago;
    }

    public Tipodepagoempresa(int idtiposdepagoempresa, int idempresa, int idtiposdepago, String estadotiposdepago) {
        this.idtiposdepagoempresa = idtiposdepagoempresa;
        this.idempresa = idempresa;
        this.idtiposdepago = idtiposdepago;
        this.estadotiposdepago = estadotiposdepago;
    }

    int idempresa;
int idtiposdepago;
String estadotiposdepago;
    @Override
    public String toString(){return String.valueOf(idtiposdepagoempresa)+" - "+ String.valueOf(idtiposdepago);
    }
}
