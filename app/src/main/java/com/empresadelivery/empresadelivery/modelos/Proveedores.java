package com.empresadelivery.empresadelivery.modelos;

public class Proveedores {

    private int idproveedor;
    private String nombre;
    private String razonsocial;
    private String direccion;
    private String telefono;
    private String etado;
    private int idempresa;

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEtado() {
        return etado;
    }

    public void setEtado(String etado) {
        this.etado = etado;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public Proveedores(int idproveedor, String nombre, String razonsocial, String direccion, String telefono, String etado, int idempresa) {
        this.idproveedor = idproveedor;
        this.nombre = nombre;
        this.razonsocial = razonsocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.etado = etado;
        this.idempresa = idempresa;
    }
    @Override
    public String toString(){

        return this.idproveedor+"   -  "+this.razonsocial;
    }

}
