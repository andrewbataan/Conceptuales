package Controllers;

import Model.Usuario;
import Services.UsuarioDao;
import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "VerificationController")
@SessionScoped


public class VerificationController implements Serializable{
    
    private static final long serialVersionUID = 1L;
     private String nombre;
    private Usuario usuario;
    private String apellido1;
    private String email;
 private   String contrasena;
 private int id;
 private String password;
 private String user;
private boolean logueado = false;
    public VerificationController() {
    }
 
    
    public String signUp(){
         UsuarioDao dao = new UsuarioDao();
         Usuario usr = new Usuario(this.id,this.nombre,this.apellido1,this.email,this.contrasena);
         dao.insert(usr);
          if (usr!= null) {

            System.out.println(usr.getNombre());
           
            this.redirect("/faces/usermails?faces-redirect=true&idUsuario= " + this.usuario.getId());

        } else {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid User"));
        }
        return "succesfull signUp";

    }
    public String login ()throws SQLException, ClassNotFoundException {
     UsuarioDao usr = new UsuarioDao();
     if(Verification (usr)) {
      return "/faces/usermails?faces-redirect=true&idUsuario= " + this.usuario.getId();
        } else {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid User"));
        }
        return null;
    }
    
    
    
    
    
    
 private boolean Verification(UsuarioDao usr) throws SQLException, ClassNotFoundException {
        FacesMessage msg;
        Usuario loginU = usr.sign(this.user, this.password);
        if (loginU != null) {
            usuario = loginU;
            id = usuario.getId();
            logueado = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", this.nombre);
            return true;
            
        } 
         return false;
         
 }
 
 
 
  public void redirect(String page) {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext
                    .getCurrentInstance().getExternalContext().getRequest();
            FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .redirect(
                            request.getContextPath()
                            + "/faces/" + page + ".xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
}
