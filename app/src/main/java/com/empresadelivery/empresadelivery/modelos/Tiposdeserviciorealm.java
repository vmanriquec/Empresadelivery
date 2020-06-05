package com.empresadelivery.empresadelivery.modelos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tiposdeserviciorealm extends RealmObject {
    @PrimaryKey
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    int idtipodeatencion;
    String nombretipodeatencion;
    String estadotipodeatencion;
    @Override
    public String toString(){return String.valueOf(idtipodeatencion)+" - "+ String.valueOf(nombretipodeatencion);
    }
}
