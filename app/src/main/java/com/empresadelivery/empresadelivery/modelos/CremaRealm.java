package com.empresadelivery.empresadelivery.modelos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CremaRealm extends RealmObject {
    @PrimaryKey
    int id;
        String nombrecrema;
    String estadocrema;
int idcrema;
    int idproducto;

    int idcremadetalle;
    int iddetalle;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getIdcremadetalle() {
        return idcremadetalle;
    }

    public void setIdcremadetalle(int idcremadetalle) {
        this.idcremadetalle = idcremadetalle;
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

    @Override
    public String toString() {
        return "CremaRealm{" +
                "idcrema=" + idcrema +
                ", nombrecrema='" + nombrecrema + '\'' +
                '}';
    }


    public int getIdcrema() {
        return idcrema;
    }

    public void setIdcrema(int idcrema) {
        this.idcrema = idcrema;
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

}
