package com.empresadelivery.empresadelivery.modelos;

public class Tipodeservicioempresa {
    int idempresa;
    int idtipodeatencion;

    String nombretipodeatencion;
    String estadotipodeatencion;
    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
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

    public Tipodeservicioempresa(int idempresa, int idtipodeatencion, String nombretipodeatencion, String estadotipodeatencion) {
        this.idempresa = idempresa;
        this.idtipodeatencion = idtipodeatencion;
        this.nombretipodeatencion = nombretipodeatencion;
        this.estadotipodeatencion = estadotipodeatencion;
    }

}
