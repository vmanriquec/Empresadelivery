package com.empresadelivery.empresadelivery.modelos;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AdicionalRealm extends RealmObject {
    @PrimaryKey
    int idadicional;
int idadicionaldetalle;

    public int getIdadicionaldetalle() {
        return idadicionaldetalle;
    }

    public void setIdadicionaldetalle(int idadicionaldetalle) {
        this.idadicionaldetalle = idadicionaldetalle;
    }

    String nombreadicional;
    Double precioadicional;
    String    estadoadicional;

    int idproducto;
    int id;
    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }




    @Override
    public String toString() {
        return "AdicionalRealm{" +
                "nombreadicional='" + nombreadicional + '\'' +
                '}';
    }


    public int getIdadicional() {
        return idadicional;
    }

    public void setIdadicional(int idadicional) {
        this.idadicional = idadicional;
    }

    public String getNombreadicional() {
        return nombreadicional;
    }

    public void setNombreadicional(String nombreadicional) {
        this.nombreadicional = nombreadicional;
    }

    public Double getPrecioadicional() {
        return precioadicional;
    }

    public void setPrecioadicional(Double precioadicional) {
        this.precioadicional = precioadicional;
    }

    public String getEstadoadicional() {
        return estadoadicional;
    }

    public void setEstadoadicional(String estadoadicional) {
        this.estadoadicional = estadoadicional;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AdicionalRealm() {

    }
}
