package com.empresadelivery.empresadelivery.modelos;

public class Empresa {

    int idempresa;
    String razonsocialempresa;
        String direccionempresa;
    String telefonoempresa;
    String montominimodeventa;
    String tiempodedemoraempresa;

    String emailempresa;
    String paginawebempresa;
    String estadoempresa;
    String sloganempresa;
    String nombreadministrador;
    String telefonoadministrador;
    String logotipoempresa;
    String idrubroempresa;


    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getRazonsocialempresa() {
        return razonsocialempresa;
    }

    public void setRazonsocialempresa(String razonsocialempresa) {
        this.razonsocialempresa = razonsocialempresa;
    }

    public String getDireccionempresa() {
        return direccionempresa;
    }

    public void setDireccionempresa(String direccionempresa) {
        this.direccionempresa = direccionempresa;
    }

    public String getTelefonoempresa() {
        return telefonoempresa;
    }

    public void setTelefonoempresa(String telefonoempresa) {
        this.telefonoempresa = telefonoempresa;
    }



    public String getEmailempresa() {
        return emailempresa;
    }

    public void setEmailempresa(String emailempresa) {
        this.emailempresa = emailempresa;
    }



    public String getPaginawebempresa() {
        return paginawebempresa;
    }

    public void setPaginawebempresa(String paginawebempresa) {
        this.paginawebempresa = paginawebempresa;
    }

    public String getEstadoempresa() {
        return estadoempresa;
    }

    public void setEstadoempresa(String estadoempresa) {
        this.estadoempresa = estadoempresa;
    }

    public String getSloganempresa() {
        return sloganempresa;
    }

    public void setSloganempresa(String sloganempresa) {
        this.sloganempresa = sloganempresa;
    }

    public String getNombreadministrador() {
        return nombreadministrador;
    }

    public void setNombreadministrador(String nombreadministrador) {
        this.nombreadministrador = nombreadministrador;
    }

    public String getTelefonoadministrador() {
        return telefonoadministrador;
    }

    public void setTelefonoadministrador(String telefonoadministrador) {
        this.telefonoadministrador = telefonoadministrador;
    }

    public String getLogotipoempresa() {
        return logotipoempresa;
    }

    public void setLogotipoempresa(String logotipoempresa) {
        this.logotipoempresa = logotipoempresa;
    }

    public String getIdrubroempresa() {
        return idrubroempresa;
    }

    public void setIdrubroempresa(String idrubroempresa) {
        this.idrubroempresa = idrubroempresa;
    }

    public String getMontominimodeventa() {
        return montominimodeventa;
    }

    public void setMontominimodeventa(String montominimodeventa) {
        this.montominimodeventa = montominimodeventa;
    }

    public String getTiempodedemoraempresa() {
        return tiempodedemoraempresa;
    }

    public void setTiempodedemoraempresa(String tiempodedemoraempresa) {
        this.tiempodedemoraempresa = tiempodedemoraempresa;
    }



    @Override
    public String toString() {
        return "Empresa{" +
                "idempresa=" + idempresa +
                ", razonsocialempresa='" + razonsocialempresa + '\'' +
                '}';
    }


    public Empresa(int idempresa, String razonsocialempresa, String direccionempresa,
                   String telefonoempresa,  String emailempresa, String paginawebempresa,
                   String estadoempresa, String sloganempresa, String nombreadministrador,
                   String telefonoadministrador, String logotipoempresa, String idrubroempresa,
                   String montominimodeventa, String tiempodedemoraempresa) {
        this.idempresa = idempresa;
        this.razonsocialempresa = razonsocialempresa;
        this.direccionempresa = direccionempresa;
        this.telefonoempresa = telefonoempresa;

        this.emailempresa = emailempresa;
        this.paginawebempresa = paginawebempresa;
        this.estadoempresa = estadoempresa;
        this.sloganempresa = sloganempresa;
        this.nombreadministrador = nombreadministrador;
        this.telefonoadministrador = telefonoadministrador;
        this.logotipoempresa = logotipoempresa;
        this.idrubroempresa = idrubroempresa;
        this.montominimodeventa = montominimodeventa;
        this.tiempodedemoraempresa = tiempodedemoraempresa;

    }


}
