package com.empresadelivery.empresadelivery.modelos;

public class Adicional {
    int idadicional;
    String nombreadicional;
    Double precioadicional;
    String    estadoadicional;
    int idempresa;

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public Adicional(int idadicional, String nombreadicional, Double precioadicional, String estadoadicional, int idempresa) {
        this.idadicional = idadicional;
        this.nombreadicional = nombreadicional;
        this.precioadicional = precioadicional;
        this.estadoadicional = estadoadicional;
        this.idempresa = idempresa;
    }

    @Override
    public String toString() {
        return "Adicional{" +
                "nombreadicional='" + nombreadicional + '\'' +
                '}';
    }

    public int getIdadicional() {
        return idadicional;
    }

    public void setIdadicional(int idadicional) {
        this.idadicional = idadicional;
    }

    public String getNombreadicional() {
        return nombreadicional;
    }

    public void setNombreadicional(String nombreadicional) {
        this.nombreadicional = nombreadicional;
    }

    public Double getPrecioadicional() {
        return precioadicional;
    }

    public void setPrecioadicional(Double precioadicional) {
        this.precioadicional = precioadicional;
    }

    public String getEstadoadicional() {
        return estadoadicional;
    }

    public void setEstadoadicional(String estadoadicional) {
        this.estadoadicional = estadoadicional;
    }
}
