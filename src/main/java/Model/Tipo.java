/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author User
 */
public class Tipo {
    int idTipo;
    String notiAutomaticas;
    String notiManuales;

    public Tipo() {
    }

    public Tipo(int idTipo, String notiAutomaticas, String notiManuales) {
        this.idTipo = idTipo;
        this.notiAutomaticas = notiAutomaticas;
        this.notiManuales = notiManuales;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNotiAutomaticas() {
        return notiAutomaticas;
    }

    public void setNotiAutomaticas(String notiAutomaticas) {
        this.notiAutomaticas = notiAutomaticas;
    }

    public String getNotiManuales() {
        return notiManuales;
    }

    public void setNotiManuales(String notiManuales) {
        this.notiManuales = notiManuales;
    }
    
}
