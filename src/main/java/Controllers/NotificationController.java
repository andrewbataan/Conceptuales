/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Model.Notificacion;
import NotiService.EmailService;
import Services.NotifDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import job.QuartzJoB;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

@ManagedBean(name = "NotificationController")
@SessionScoped

public class NotificationController {


    private String shedule;
   private List<String> times = new ArrayList<String>();
   private List <Notificacion>notifs = new ArrayList<>();
   private NotifDao ndao = new NotifDao();
 
   private static String destinatario, asunto, cuerpo;
     @ManagedProperty("#{loginController}")
    private static VerificationController veriController = new VerificationController();
   
   public void viewNotifs( int id) throws SQLException{
       
    this.notifs = ndao.userEmails(id);
       
   }
    @PostConstruct
    public void init() {
        times.add("40 seconds");
        times.add("2 minutes");
        times.add("10 minutes");
    }
    
     public String crearCorreo() {
        return "newmail?faces-redirect=true&idUsuario= " + veriController.getUsuario().getId();
    }

    public String verCorreos() {
        return "correos?faces-redirect=true&idUsuario=" + veriController.getUsuario().getId();
    }
     public static void  sendmail(){
         EmailService es = new EmailService();
      es.simpleEmail(veriController.getUsuario(),   NotificationController.asunto, NotificationController.cuerpo, NotificationController.destinatario);
     }
     
     
     public void QuartzMail(){
         
     
             String cronTrigger="";
             try{
                 if (this.shedule.equalsIgnoreCase("40seconds")){
                     cronTrigger = "0/40 0 0 ? * * *";
                 }else if (this.shedule.equalsIgnoreCase("2 minutes")){
                 cronTrigger = "0 0/2 0 ? * * *";
             }else if (this.shedule.equalsIgnoreCase("10 minutes"))
                 cronTrigger = "00/10 0?***";
                 
                  System.out.println(cronTrigger);
                  
                 JobDetail job1 = JobBuilder.newJob(QuartzJoB.class).withIdentity("job1", "group1").build();
                 Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("cronTrigger1", "group1").withSchedule(CronScheduleBuilder.cronSchedule(cronTrigger))
                    .build();
                 Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
            scheduler1.start();
            scheduler1.scheduleJob(job1, trigger1);

            Thread.sleep(100000);

            scheduler1.shutdown();

            System.out.println("email has been send");

        } catch (Exception e) {
            e.printStackTrace();
        }

             }

    public String getShedule() {
        return shedule;
    }

    public void setShedule(String shedule) {
        this.shedule = shedule;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public List<Notificacion> getNotifs() {
        return notifs;
    }

    public void setNotifs(List<Notificacion> notifs) {
        this.notifs = notifs;
    }

    public NotifDao getNdao() {
        return ndao;
    }

    public void setNdao(NotifDao ndao) {
        this.ndao = ndao;
    }

    public static String getDestinatario() {
        return destinatario;
    }

    public static void setDestinatario(String destinatario) {
        NotificationController.destinatario = destinatario;
    }

    public static String getAsunto() {
        return asunto;
    }

    public static void setAsunto(String asunto) {
        NotificationController.asunto = asunto;
    }

    public static String getCuerpo() {
        return cuerpo;
    }

    public static void setCuerpo(String cuerpo) {
        NotificationController.cuerpo = cuerpo;
    }

    public static VerificationController getVeriController() {
        return veriController;
    }

    public static void setVeriController(VerificationController veriController) {
        NotificationController.veriController = veriController;
    }
    
}
