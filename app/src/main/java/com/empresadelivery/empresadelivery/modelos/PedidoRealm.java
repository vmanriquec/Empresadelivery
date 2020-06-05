package com.empresadelivery.empresadelivery.modelos;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PedidoRealm extends RealmObject {
    @PrimaryKey
    private int id;
    private int idpedido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int idcliente;
    private int idmesa;
    private Double totalpedido;
    private String estadopedido ;
    private String descripcionpedido;
    private String fechapedido;
    private int idusuario;
    private int idalmacen;
    private String idfacebook ;
   private String llevar;
    private String direccionallevar;
    private String idfirebase;
    private String nombredescuento;
    private String cuantopagaecliente;
    private String vuelto,referencias,nombreusuario;
    private String detalleproducto;

    private  String montodescuento;
    private  String nombrecosto;
    private String montocosto;
    private String longitud;
    private String latitud;

    public String getLlevar() {
        return llevar;
    }

    public void setLlevar(String llevar) {
        this.llevar = llevar;
    }

    public String getDireccionallevar() {
        return direccionallevar;
    }

    public void setDireccionallevar(String direccionallevar) {
        this.direccionallevar = direccionallevar;
    }

    public String getIdfirebase() {
        return idfirebase;
    }

    public void setIdfirebase(String idfirebase) {
        this.idfirebase = idfirebase;
    }

    public String getNombredescuento() {
        return nombredescuento;
    }

    public void setNombredescuento(String nombredescuento) {
        this.nombredescuento = nombredescuento;
    }

    public String getCuantopagaecliente() {
        return cuantopagaecliente;
    }

    public void setCuantopagaecliente(String cuantopagaecliente) {
        this.cuantopagaecliente = cuantopagaecliente;
    }

    public String getVuelto() {
        return vuelto;
    }

    public void setVuelto(String vuelto) {
        this.vuelto = vuelto;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getDetalleproducto() {
        return detalleproducto;
    }

    public void setDetalleproducto(String detalleproducto) {
        this.detalleproducto = detalleproducto;
    }

    public String getMontodescuento() {
        return montodescuento;
    }

    public void setMontodescuento(String montodescuento) {
        this.montodescuento = montodescuento;
    }

    public String getNombrecosto() {
        return nombrecosto;
    }

    public void setNombrecosto(String nombrecosto) {
        this.nombrecosto = nombrecosto;
    }

    public String getMontocosto() {
        return montocosto;
    }

    public void setMontocosto(String montocosto) {
        this.montocosto = montocosto;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }





    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    private Double total;
    private RealmList<Detallepedidorealm> detallepedidorealms;



    public String getDescripcionpedido() {
        return descripcionpedido;
    }

    public void setDescripcionpedido(String descripcionpedido) {
        this.descripcionpedido = descripcionpedido;
    }


    public RealmList<Detallepedidorealm> getDetallepedidorealms() {
        return detallepedidorealms;
    }

    public void setDetallepedidorealms(RealmList<Detallepedidorealm> detallepedidorealms) {
        this.detallepedidorealms = detallepedidorealms;
    }


    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getIdmesa() {
        return idmesa;
    }

    public void setIdmesa(int idmesa) {
        this.idmesa = idmesa;
    }

    public Double getTotalpedido() {
        return totalpedido;
    }

    public void setTotalpedido(Double totalpedido) {
        this.totalpedido = totalpedido;
    }

    public String getEstadopedido() {
        return estadopedido;
    }

    public void setEstadopedido(String estadopedido) {
        this.estadopedido = estadopedido;
    }

    public String getFechapedido() {
        return fechapedido;
    }

    public void setFechapedido(String fechapedido) {
        this.fechapedido = fechapedido;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdalmacen() {
        return idalmacen;
    }

    public void setIdalmacen(int idalmacen) {
        this.idalmacen = idalmacen;
    }

    public String getIdfacebook() {
        return idfacebook;
    }

    public void setIdfacebook(String idfacebook) {
        this.idfacebook = idfacebook;
    }


}
