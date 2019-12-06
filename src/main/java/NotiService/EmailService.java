package NotiService;

import Model.Notificacion;
import Model.Usuario;
import Services.NotifDao;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author jairf
 */
public class EmailService {
    public String simpleEmail(Usuario usr,String asunto,String cuerpo,String destinatario){
NotifDao dao = new NotifDao();
        Notificacion notificacion = null;
        
        
        try{
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            System.out.println(usr.getEmail());
            System.out.println(usr.getContrasena());
            email.setAuthenticator(new DefaultAuthenticator(usr.getEmail(), usr.getContrasena()));
            email.setSSLOnConnect(true);
            email.setFrom(usr.getEmail());
            email.setSubject(asunto);
            email.setMsg(cuerpo);
            email.addTo(destinatario);
            email.send();

            notificacion = new Notificacion();
            notificacion.setIdUsuario(usr.getId());
            notificacion.setAsunto(asunto);
            notificacion.setCuerpo(cuerpo);
            notificacion.setDestinatario(destinatario);
          dao.insert(notificacion);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "el email se ha enviado Correctamente";
    }
}
