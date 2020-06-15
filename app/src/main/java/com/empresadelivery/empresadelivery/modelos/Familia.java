
package com.empresadelivery.empresadelivery.modelos;

/**
 * Created by Perseo on 05/08/2014.
 */
public class Familia {
    private int idfamilia;

    public Familia(int idfamilia, int idempresa, String nombrefamilia, String estadofamilia) {
        this.idfamilia = idfamilia;
        this.idempresa = idempresa;
        this.nombrefamilia = nombrefamilia;
        this.estadofamilia = estadofamilia;
    }

    private int idempresa;
    private String nombrefamilia;
private String estadofamilia;


    public int getIdfamilia() {
        return idfamilia;
    }

    public void setIdfamilia(int idfamilia) {
        this.idfamilia = idfamilia;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getNombrefamilia() {
        return nombrefamilia;
    }

    public void setNombrefamilia(String nombrefamilia) {
        this.nombrefamilia = nombrefamilia;
    }

    public String getEstadofamilia() {
        return estadofamilia;
    }

    public void setEstadofamilia(String estadofamilia) {
        this.estadofamilia = estadofamilia;
    }

    @Override
    public String toString(){return String.valueOf(idfamilia)+" - "+ String.valueOf(nombrefamilia);
    }

}
