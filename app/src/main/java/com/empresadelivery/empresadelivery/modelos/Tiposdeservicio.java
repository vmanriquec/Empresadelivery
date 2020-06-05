package com.empresadelivery.empresadelivery.modelos;

public class Tiposdeservicio {
    int idtipodeatencion;
    String nombretipodeatencion;
    String estadotipodeatencion;

    public Tiposdeservicio(int idtipodeatencion, String nombretipodeatencion, String estadotipodeatencion, String imagentipoatencion) {
        this.idtipodeatencion = idtipodeatencion;
        this.nombretipodeatencion = nombretipodeatencion;
        this.estadotipodeatencion = estadotipodeatencion;
        this.imagentipoatencion = imagentipoatencion;
    }

    public int getIdtipodeatencion() {
        return idtipodeatencion;
    }

    public void setIdtipodeatencion(int idtipodeatencion) {
        this.idtipodeatencion = idtipodeatencion;
    }

    public String getNombretipodeatencion() {
        return nombretipodeatencion;
    }

    public void setNombretipodeatencion(String nombretipodeatencion) {
        this.nombretipodeatencion = nombretipodeatencion;
    }

    public String getEstadotipodeatencion() {
        return estadotipodeatencion;
    }

    public void setEstadotipodeatencion(String estadotipodeatencion) {
        this.estadotipodeatencion = estadotipodeatencion;
    }

    public String getImagentipoatencion() {
        return imagentipoatencion;
    }

    public void setImagentipoatencion(String imagentipoatencion) {
        this.imagentipoatencion = imagentipoatencion;
    }

    String imagentipoatencion;

    @Override
    public String toString(){return String.valueOf(idtipodeatencion)+" - "+ String.valueOf(nombretipodeatencion);
    }
}
