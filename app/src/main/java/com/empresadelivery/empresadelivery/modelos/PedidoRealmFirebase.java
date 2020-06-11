package com.empresadelivery.empresadelivery.modelos;

public class PedidoRealmFirebase {
    private int idpedido;
    private int idcliente;
    private int idmesa;
    private Double totalpedido;

    private String estadopedido ;
    private String fechapedido;
    private int idusuario;
    private int idalmacen;
    private String idfacebook;
    private String descripcionpedido;
    private String llevar;
    private String direccionallevar;
    private String idfirebase;
    private String nombredescuento;
    private String cuantopagaecliente;
    private String vuelto,referencias,nombreusuario;
    private String detalleproducto;
    private String idempresa;

    public String getDetalleproducto() {
        return detalleproducto;
    }

    public void setDetalleproducto(String detalleproducto) {
        this.detalleproducto = detalleproducto;
    }

    @Override
    public String toString() {
        return "PedidoRealmFirebase{" +
                "descripcionpedido='" + descripcionpedido + '\'' +
                ", direccionallevar='" + direccionallevar + '\'' +
                ", nombredescuento='" + nombredescuento + '\'' +
                ", cuantopagaecliente='" + cuantopagaecliente + '\'' +
                ", referencias='" + referencias + '\'' +
                ", nombreusuario='" + nombreusuario + '\'' +
                ", montodescuento='" + montodescuento + '\'' +
                ", nombrecosto='" + nombrecosto + '\'' +
                ", montocosto='" + montocosto + '\'' +
                '}';
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    private String telefono;


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


    public String getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(String idempresa) {
        this.idempresa = idempresa;
    }

    public PedidoRealmFirebase(int idpedido, int idcliente, int idmesa,
                               Double totalpedido, String estadopedido, String fechapedido,
                               int idusuario, int idalmacen, String idfacebook, String descripcionpedido,
                               String llevar, String direccionallevar, String idfirebase, String nombredescuento,
                               String montodescuento, String nombrecosto, String montocosto, String longitud,
                               String latitud, String cuantopagaecliente, String vuelto, String telefono, String referencias, String nombreusuario, String detalleproducto, String idempresa) {
        this.idpedido = idpedido;
        this.idcliente = idcliente;
        this.idmesa = idmesa;
        this.totalpedido = totalpedido;
        this.estadopedido = estadopedido;
        this.fechapedido = fechapedido;
        this.idusuario = idusuario;
        this.idalmacen = idalmacen;
        this.idfacebook = idfacebook;
        this.descripcionpedido = descripcionpedido;
        this.llevar = llevar;
        this.direccionallevar = direccionallevar;
        this.idfirebase = idfirebase;
        this.nombredescuento = nombredescuento;
        this.montodescuento = montodescuento;
        this.nombrecosto = nombrecosto;
        this.montocosto = montocosto;
        this.longitud = longitud;
        this.latitud = latitud;
        this.cuantopagaecliente=cuantopagaecliente;
        this.vuelto=vuelto;
        this.telefono=telefono;
        this.referencias=referencias;
        this.nombreusuario=nombreusuario;
        this.detalleproducto=detalleproducto;
        this.idempresa=idempresa;
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

    private  String montodescuento;
    private  String nombrecosto;
    private String montocosto;
    private String longitud;
    private String latitud;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public PedidoRealmFirebase(){



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

    public String getDescripcionpedido() {
        return descripcionpedido;
    }

    public void setDescripcionpedido(String descripcionpedido) {
        this.descripcionpedido = descripcionpedido;
    }

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



}
