package com.empresadelivery.empresadelivery.modelos;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Detallepedidorealm extends RealmObject {
       @PrimaryKey
    public int id;
 public int ojo;
 private String subtotal;
    private int idpedido;
    private int cantidadrealm;
    private Double precventarealm;
    private String nombreproductorealm;
    private String imagenrealm;
    private int idalmacenrealm;
    private int idproductorealm;
private String comentarioacocina;
private int iddetallepedido;

    public int getIddetallepedido() {
        return iddetallepedido;
    }

    public void setIddetallepedido(int iddetallepedido) {
        this.iddetallepedido = iddetallepedido;
    }

    public String getComentarioacocina() {
        return comentarioacocina;
    }

    public void setComentarioacocina(String comentarioacocina) {
        this.comentarioacocina = comentarioacocina;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }




    private RealmList<ProductoRealm> productoRealms;
    public int getOjo() {
        return ojo;
    }

    public void setOjo(int ojo) {
        this.ojo = ojo;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public RealmList<ProductoRealm> getProductoRealms() {
        return productoRealms;
    }

    public void setProductoRealms(RealmList<ProductoRealm> productoRealms) {
        this.productoRealms = productoRealms;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public int getIdproductorealm() {
        return idproductorealm;
    }

    public void setIdproductorealm(int idproductorealm) {
        this.idproductorealm = idproductorealm;
    }

    public int getCantidadrealm() {
        return cantidadrealm;
    }

    public void setCantidadrealm(int cantidadrealm) {
        this.cantidadrealm = cantidadrealm;
    }

    public Double getPrecventarealm() {
        return precventarealm;
    }

    public void setPrecventarealm(Double precventarealm) {
        this.precventarealm = precventarealm;
    }

    public String getNombreproductorealm() {
        return nombreproductorealm;
    }

    public void setNombreproductorealm(String nombreproductorealm) {
        this.nombreproductorealm = nombreproductorealm;
    }

    public String getImagenrealm() {
        return imagenrealm;
    }

    public void setImagenrealm(String imagenrealm) {
        this.imagenrealm = imagenrealm;
    }

    public int getIdalmacenrealm() {
        return idalmacenrealm;
    }

    public void setIdalmacenrealm(int idalmacenrealm) {
        this.idalmacenrealm = idalmacenrealm;
    }





}
