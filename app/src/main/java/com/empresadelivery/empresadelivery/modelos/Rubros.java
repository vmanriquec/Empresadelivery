package com.empresadelivery.empresadelivery.modelos;

public class Rubros {
    public Rubros(int idrubroempresa, String nombrerubro, String estadorubro) {
        this.idrubroempresa = idrubroempresa;
        this.nombrerubro = nombrerubro;
        this.estadorubro = estadorubro;
    }

    int idrubroempresa;
    String nombrerubro;
    String estadorubro;

    public int getIdrubroempresa() {
        return idrubroempresa;
    }

    public void setIdrubroempresa(int idrubroempresa) {
        this.idrubroempresa = idrubroempresa;
    }

    public String getNombrerubro() {
        return nombrerubro;
    }

    public void setNombrerubro(String nombrerubro) {
        this.nombrerubro = nombrerubro;
    }

    public String getEstadorubro() {
        return estadorubro;
    }

    public void setEstadorubro(String estadorubro) {
        this.estadorubro = estadorubro;
    }

    public String toString(){return String.valueOf(idrubroempresa)+" - "+ String.valueOf(nombrerubro);
    }

}
