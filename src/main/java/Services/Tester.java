/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Model.Usuario;

/**
 *
 * @author User
 */
public class Tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Usuario usr = new Usuario();
        usr.setNombre("Sin nombre");
        usr.setEmail("alanna@gmail.com");
        usr.setContrasena("variable");
        usr.setId(100);
        try{
            UsuarioDao dao = new UsuarioDao();
            dao.insertar(usr);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
