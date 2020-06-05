package com.empresadelivery.empresadelivery.modelos;

public class Productoadicional {
    public int getIdproductoadicional() {
        return idproductoadicional;
    }

    public void setIdproductoadicional(int idproductoadicional) {
        this.idproductoadicional = idproductoadicional;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdadicional() {
        return idadicional;
    }

    public void setIdadicional(int idadicional) {
        this.idadicional = idadicional;
    }

    public String getEstadocrema() {
        return estadocrema;
    }

    public void setEstadocrema(String estadocrema) {
        this.estadocrema = estadocrema;
    }

    public Productoadicional(int idproductoadicional, int idproducto, int idadicional, String estadocrema) {
        this.idproductoadicional = idproductoadicional;
        this.idproducto = idproducto;
        this.idadicional = idadicional;
        this.estadocrema = estadocrema;
    }

    int idproductoadicional;
    int idproducto;
      int idadicional;
      String estadocrema;
}
