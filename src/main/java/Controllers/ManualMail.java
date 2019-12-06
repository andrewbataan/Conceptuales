/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.Notificacion;
import Model.Usuario;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.NewsAddress;
import static org.primefaces.behavior.confirm.ConfirmBehavior.PropertyKeys.message;

/**
 *
 * @author User
 */
public class ManualMail {
    
  static  Notificacion noti = new Notificacion();
  static Usuario usr = new Usuario();

    public static void main(String recipient) {
        try {
            Properties properties = new Properties();
            
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable ", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.setup.port", "578");
            
            Session mailSession = Session.getDefaultInstance(properties,null);
            mailSession.setDebug(true);
            
            Message mailMessage = new MimeMessage(mailSession);
            
            mailMessage.setFrom(new NewsAddress(noti.getUser()));
            mailMessage.setRecipient(Message.RecipientType.TO, new NewsAddress(noti.getDestinatario()));
            mailMessage.setContent(message,"text/html" );
            mailMessage.setSubject(noti.getAsunto());
            
            Transport transport = mailSession.getTransport("stmp");
            transport.connect("smtp.gmail.com", usr.getEmail(), usr.getContrasena() );
        } catch (Exception ex) {
            Logger.getLogger(ManualMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}