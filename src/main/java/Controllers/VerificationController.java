
package Controllers;

import Model.Usuario;
import Services.UsuarioDao;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "VerificationController")
@SessionScoped


public class VerificationController implements Serializable{
     private String nombre;
     Usuario usuario = new Usuario();
    private String apellido1;
    private String email;
 private   String contrasena;
 private int id;
private boolean logueado = false;
    public VerificationController() {
    }
 
    
    public String signUp(){
         UsuarioDao dao = new UsuarioDao();
         Usuario usr = new Usuario(this.id ,this.nombre,this.apellido1,this.email,this.contrasena);
         dao.insert(usr);
         return "succesfull sign up";
    }
 private boolean login(UsuarioDao usuarios) {
        FacesMessage msg;
        Usuario loginU = usuarios.verificarUsuario(this.email, this.contrasena);
        if (loginU != null) {
            usuario = loginU;
            id = usuario.getId();
            logueado = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.nombre);
            return true;
            
        } else {
            logueado = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Watch out for PrimeFaces."));
 
 
 
 
 
}
         return false;
 }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLogueado() {
        return logueado;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
