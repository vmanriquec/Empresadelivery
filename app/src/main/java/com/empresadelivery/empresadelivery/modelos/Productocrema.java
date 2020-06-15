package com.empresadelivery.empresadelivery.modelos;

public class Productocrema {
    int idproductocrema;
    int idproducto;

    int idcrema;
    String estadoproductocrema;
int idempresa;

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getIdproductocrema() {
        return idproductocrema;
    }

    public void setIdproductocrema(int idproductocrema) {
        this.idproductocrema = idproductocrema;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdcrema() {
        return idcrema;
    }

    public void setIdcrema(int idcrema) {
        this.idcrema = idcrema;
    }

    public String getEstadoproductocrema() {
        return estadoproductocrema;
    }

    public void setEstadoproductocrema(String estadoproductocrema) {
        this.estadoproductocrema = estadoproductocrema;
    }

    public Productocrema(int idproductocrema, int idproducto, int idcrema, String estadoproductocrema,int idempresa) {
        this.idproductocrema = idproductocrema;
        this.idproducto = idproducto;
        this.idcrema = idcrema;
        this.estadoproductocrema = estadoproductocrema;
        this.idempresa=idempresa;
    }

}
