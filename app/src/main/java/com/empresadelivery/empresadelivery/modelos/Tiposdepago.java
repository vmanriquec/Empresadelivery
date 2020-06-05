package com.empresadelivery.empresadelivery.modelos;

public class Tiposdepago {
   int idtiposdepago;

    public Tiposdepago(int idtiposdepago, String nombretiposdepago, String estadotiposdepago) {
        this.idtiposdepago = idtiposdepago;
        this.nombretiposdepago = nombretiposdepago;
        this.estadotiposdepago = estadotiposdepago;
    }

    String nombretiposdepago;
   String estadotiposdepago;

    public int getIdtiposdepago() {
        return idtiposdepago;
    }

    public void setIdtiposdepago(int idtiposdepago) {
        this.idtiposdepago = idtiposdepago;
    }

    public String getNombretiposdepago() {
        return nombretiposdepago;
    }

    public void setNombretiposdepago(String nombretiposdepago) {
        this.nombretiposdepago = nombretiposdepago;
    }

    public String getEstadotiposdepago() {
        return estadotiposdepago;
    }

    public void setEstadotiposdepago(String estadotiposdepago) {
        this.estadotiposdepago = estadotiposdepago;
    }
    @Override
    public String toString(){return String.valueOf(idtiposdepago)+" - "+ String.valueOf(nombretiposdepago);
    }
}
