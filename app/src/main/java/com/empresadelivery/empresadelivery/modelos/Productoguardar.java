package com.empresadelivery.empresadelivery.modelos;

public class Productoguardar {
    int idproducto;

    public int getIdalmacen() {
        return idalmacen;
    }

    public void setIdalmacen(int idalmacen) {
        this.idalmacen = idalmacen;
    }

    int idalmacen;
    int idempresa;

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    String nombreproducto;
    String estadoproducto;
    Double precventa;
    String imagen;

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public String getEstadoproducto() {
        return estadoproducto;
    }

    public void setEstadoproducto(String estadoproducto) {
        this.estadoproducto = estadoproducto;
    }

    public Double getPrecventa() {
        return precventa;
    }

    public void setPrecventa(Double precventa) {
        this.precventa = precventa;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getIdfamilia() {
        return idfamilia;
    }

    public void setIdfamilia(int idfamilia) {
        this.idfamilia = idfamilia;
    }

    public Double getPreccosto() {
        return preccosto;
    }

    public void setPreccosto(Double preccosto) {
        this.preccosto = preccosto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigobarras() {
        return codigobarras;
    }

    public void setCodigobarras(String codigobarras) {
        this.codigobarras = codigobarras;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    int idfamilia;

    public Productoguardar(int idproducto, String nombreproducto, String estadoproducto, Double precventa, String imagen, int idfamilia, Double preccosto, String descripcion, String codigobarras, String foto, String qr, int idproveedor, String ingredientes,int idalmacen,int idempresa) {
        this.idproducto = idproducto;
        this.nombreproducto = nombreproducto;
        this.estadoproducto = estadoproducto;
        this.precventa = precventa;
        this.imagen = imagen;
        this.idfamilia = idfamilia;
        this.preccosto = preccosto;
        this.descripcion = descripcion;
        this.codigobarras = codigobarras;
        this.foto = foto;
        this.qr = qr;
        this.idproveedor = idproveedor;
        this.ingredientes = ingredientes;
        this.idalmacen=idalmacen;
        this.idempresa=idempresa;
    }

    Double preccosto;
    String descripcion;
    String codigobarras;
    String foto;
    String qr;
    int idproveedor;
    String ingredientes;
}
