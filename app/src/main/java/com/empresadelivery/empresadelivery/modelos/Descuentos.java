package com.empresadelivery.empresadelivery.modelos;

public class Descuentos {
   int idvaledescuento;
   String nombredescuento;
   String montodescuento;
   String estadodescuento;

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

    public Descuentos(int idvaledescuento, String nombredescuento, String montodescuento, String estadodescuento) {
        this.idvaledescuento = idvaledescuento;
        this.nombredescuento = nombredescuento;
        this.montodescuento = montodescuento;
        this.estadodescuento = estadodescuento;
    }
    @Override
    public String toString(){return String.valueOf(idvaledescuento)+" - "+ String.valueOf(nombredescuento)+" - "+String.valueOf(montodescuento);
    }

}
