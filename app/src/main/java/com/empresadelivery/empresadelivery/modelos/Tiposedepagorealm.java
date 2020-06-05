package com.empresadelivery.empresadelivery.modelos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tiposedepagorealm extends RealmObject {
    @PrimaryKey
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int idtiposdepago;
    String nombretiposdepago;
    String estadotiposdepago;

    public int getIdtiposdepago() {
        return idtiposdepago;
    }

    public void setIdtiposdepago(int idtiposdepago) {
        this.idtiposdepago = idtiposdepago;
    }

    public String getNombretiposdepago() {
        return nombretiposdepago;
    }

    public void setNombretiposdepago(String nombretiposdepago) {
        this.nombretiposdepago = nombretiposdepago;
    }

    public String getEstadotiposdepago() {
        return estadotiposdepago;
    }

    public void setEstadotiposdepago(String estadotiposdepago) {
        this.estadotiposdepago = estadotiposdepago;
    }
    @Override
    public String toString(){return String.valueOf(idtiposdepago)+" - "+ String.valueOf(nombretiposdepago);
    }
}
