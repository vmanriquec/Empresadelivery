package com.empresadelivery.empresadelivery.modelos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductoRealm extends RealmObject {
    @PrimaryKey
      private int idproducto;

    private String nombreproducto,estadoproducto,ingredientes,descripcion;

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public String getEstadoproducto() {
        return estadoproducto;
    }

    public void setEstadoproducto(String estadoproducto) {
        this.estadoproducto = estadoproducto;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecventa() {
        return precventa;
    }

    public void setPrecventa(Double precventa) {
        this.precventa = precventa;
    }

    private Double precventa;


}
