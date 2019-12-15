/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Model.Notificacion;
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
        
       Notificacion noti = new Notificacion();
     
        
        noti.setAsunto("prueba");
        noti.setCuerpo("bodyy");
        noti.setIdUsuario(1);
        noti.setDestinatario("tset@gmail");
        
        try{
            NotifDao dao = new NotifDao();
            dao.insert(noti);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
