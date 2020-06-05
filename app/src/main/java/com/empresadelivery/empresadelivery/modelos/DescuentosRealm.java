package com.empresadelivery.empresadelivery.modelos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DescuentosRealm extends RealmObject {
    @PrimaryKey
            int iddesc;
    int idvaledescuento;

    public int getIddesc() {
        return iddesc;
    }


    public void setIddesc(int iddesc) {
        this.iddesc = iddesc;
    }

    public int getIdvaledescuento() {
        return idvaledescuento;
    }

    public void setIdvaledescuento(int idvaledescuento) {
        this.idvaledescuento = idvaledescuento;
    }

    public String getNombredescuento() {
        return nombredescuento;
    }

    public void setNombredescuento(String nombredescuento) {
        this.nombredescuento = nombredescuento;
    }

    public String getMontodescuento() {
        return montodescuento;
    }

    public void setMontodescuento(String montodescuento) {
        this.montodescuento = montodescuento;
    }

    public String getEstadodescuento() {
        return estadodescuento;
    }

    public void setEstadodescuento(String estadodescuento) {
        this.estadodescuento = estadodescuento;
    }

    String nombredescuento;
    String montodescuento;
    String estadodescuento;

    @Override
    public String toString(){return String.valueOf(idvaledescuento)+" - "+ String.valueOf(nombredescuento)+ "   S/."+String.valueOf(montodescuento);
    }
}
