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
public class Notificaciones {
    int idNotificacion;
    String asunto;
    String remitente;
    String titulo;

    public Notificaciones() {
    }

    public Notificaciones(int idNotificacion, String asunto, String remitente, String titulo) {
        this.idNotificacion = idNotificacion;
        this.asunto = asunto;
        this.remitente = remitente;
        this.titulo = titulo;
    }

    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
}
