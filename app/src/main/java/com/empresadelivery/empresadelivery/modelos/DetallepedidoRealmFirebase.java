package com.empresadelivery.empresadelivery.modelos;

public class DetallepedidoRealmFirebase {

    public int iddetallepedido;
    public int idproducto;
    private int cantidad;
    private Double precventa;
    private Double subtotal;
    private int idpedido;
    private int  idalmacen;
    private String adicionales;
    private String cremas;
    private String comentario;
    private int ojo;

    private String  imagenreal;
    private String comentariococina;
    private String nombreproductorealm;

    public int getIddetallepedido() {
        return iddetallepedido;
    }

    public void setIddetallepedido(int iddetallepedido) {
        this.iddetallepedido = iddetallepedido;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecventa() {
        return precventa;
    }

    public void setPrecventa(Double precventa) {
        this.precventa = precventa;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdalmacen() {
        return idalmacen;
    }

    public void setIdalmacen(int idalmacen) {
        this.idalmacen = idalmacen;
    }

    public String getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(String adicionales) {
        this.adicionales = adicionales;
    }

    public String getCremas() {
        return cremas;
    }

    public void setCremas(String cremas) {
        this.cremas = cremas;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getOjo() {
        return ojo;
    }

    public void setOjo(int ojo) {
        this.ojo = ojo;
    }

    public String getImagenreal() {
        return imagenreal;
    }

    public void setImagenreal(String imagenreal) {
        this.imagenreal = imagenreal;
    }

    public String getComentariococina() {
        return comentariococina;
    }

    public void setComentariococina(String comentariococina) {
        this.comentariococina = comentariococina;
    }

    public String getNombreproductorealm() {
        return nombreproductorealm;
    }

    public void setNombreproductorealm(String nombreproductorealm) {
        this.nombreproductorealm = nombreproductorealm;
    }

    public String getIdalmacenrealm() {
        return idalmacenrealm;
    }

    public void setIdalmacenrealm(String idalmacenrealm) {
        this.idalmacenrealm = idalmacenrealm;
    }

    public DetallepedidoRealmFirebase(int iddetallepedido, int idproducto, int cantidad, Double precventa,
                                      Double subtotal, int idpedido, int idalmacen, String adicionales,
                                      String cremas, String comentario, int ojo, String imagenreal, String comentariococina,
                                      String nombreproductorealm, String idalmacenrealm) {
        this.iddetallepedido = iddetallepedido;
        this.idproducto = idproducto;
        this.cantidad = cantidad;
        this.precventa = precventa;
        this.subtotal = subtotal;
        this.idpedido = idpedido;
        this.idalmacen = idalmacen;
        this.adicionales = adicionales;
        this.cremas = cremas;
        this.comentario = comentario;
        this.ojo = ojo;
        this.imagenreal = imagenreal;
        this.comentariococina = comentariococina;
        this.nombreproductorealm = nombreproductorealm;
        this.idalmacenrealm = idalmacenrealm;
    }

    private String idalmacenrealm;







}
