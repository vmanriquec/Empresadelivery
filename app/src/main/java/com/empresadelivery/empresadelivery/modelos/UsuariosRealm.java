package com.empresadelivery.empresadelivery.modelos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UsuariosRealm extends RealmObject {
    @PrimaryKey
    private int idusuario;

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getClaveusuario() {
        return claveusuario;
    }

    public void setClaveusuario(String claveusuario) {
        this.claveusuario = claveusuario;
    }

    public String getAlmacenusuario() {
        return almacenusuario;
    }

    public void setAlmacenusuario(String almacenusuario) {
        this.almacenusuario = almacenusuario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstadousuario() {
        return estadousuario;
    }

    public void setEstadousuario(String estadousuario) {
        this.estadousuario = estadousuario;
    }

    private String nombreusuario;
        private String claveusuario;
        private String almacenusuario;
        private String imagen;
        private String estadousuario;

    public String getIdfacebook() {
        return idfacebook;
    }

    public void setIdfacebook(String idfacebook) {
        this.idfacebook = idfacebook;
    }

    private String idfacebook;


}
