package com.empresadelivery.empresadelivery.modelos;

public class CremaRealmFirebase {
    int idcremadetalle;
    String nombrecrema;
    String estadocrema;
    int iddetalle;

    public int getIdcremadetalle() {
        return idcremadetalle;
    }

    public void setIdcremadetalle(int idcremadetalle) {
        this.idcremadetalle = idcremadetalle;
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

    public int getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(int iddetalle) {
        this.iddetalle = iddetalle;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    int idproducto;

    public CremaRealmFirebase(int idcremadetalle, String nombrecrema, String estadocrema, int iddetalle, int idproducto) {
        this.idcremadetalle = idcremadetalle;
        this.nombrecrema = nombrecrema;
        this.estadocrema = estadocrema;
        this.iddetalle = iddetalle;
        this.idproducto = idproducto;
    }

    public CremaRealmFirebase(){}



}
