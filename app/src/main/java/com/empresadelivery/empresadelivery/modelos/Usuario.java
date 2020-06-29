package com.empresadelivery.empresadelivery.modelos;

public class Usuario {


    int idusuario;
    String nombreusuario;
    String claveusuario;
    String estadousuario;
    int idalmacen;
        String idfacebook;
        String nombrefacebook;

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

    public String getEstadousuario() {
        return estadousuario;
    }

    public void setEstadousuario(String estadousuario) {
        this.estadousuario = estadousuario;
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

    public String getNombrefacebook() {
        return nombrefacebook;
    }

    public void setNombrefacebook(String nombrefacebook) {
        this.nombrefacebook = nombrefacebook;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getIdfirebase() {
        return idfirebase;
    }

    public void setIdfirebase(String idfirebase) {
        this.idfirebase = idfirebase;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Usuario(int idusuario, String nombreusuario, String claveusuario, String estadousuario,
                   int idalmacen, String idfacebook, String nombrefacebook, String imagen, String apellidos,
                   String idfirebase, String telefono, String contrasena, String correo, String direccion,
                   String longitud, String latitud, String referencia) {
        this.idusuario = idusuario;
        this.nombreusuario = nombreusuario;
        this.claveusuario = claveusuario;
        this.estadousuario = estadousuario;
        this.idalmacen = idalmacen;
        this.idfacebook = idfacebook;
        this.nombrefacebook = nombrefacebook;
        this.imagen = imagen;
        Apellidos = apellidos;
        this.idfirebase = idfirebase;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.correo = correo;
        this.direccion = direccion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.referencia = referencia;
    }

    String imagen;
        String Apellidos;
        String idfirebase;
        String telefono;
        String contrasena;
        String  correo;
        String direccion;
        String longitud;
        String latitud;
        String referencia;
}
