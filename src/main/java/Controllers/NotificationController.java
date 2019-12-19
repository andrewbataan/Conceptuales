/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.Notificacion;
import NotiService.EmailService;
import Services.NotifDao;
import java.io.Serializable;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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

public class NotificationController implements Serializable {
//asjkdfdsf
    private static final long serialVersionUID = 1L;
    private String shedule;
    private int idUsuario;
    private List<String> times = new ArrayList<String>();
    private List<Notificacion> notif = new ArrayList<>();
    private NotifDao ndao = new NotifDao();
    
    
// these variables are static becuase we are using them for quartz job scheduler
    private static String destinatario;
    private static String asunto;
    private static String cuerpo;
    
    //These controller is needed for login user data
    @ManagedProperty("#{VerificationController}")
    private static VerificationController veriController = new VerificationController();

    public String viewNotifs(int id) throws SQLException {

        this.notif = ndao.userEmails(id);
        return "USER notifs List";

    }

    @PostConstruct
    public void init() {
        try {
            times.add("40s");// look for quartz converter
            times.add("2min");// look for cron time converter
            times.add("10min");// look for cron gtime converter
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            idUsuario = parseInt(request.getParameter("idUsuario"));
            this.viewNotifs(idUsuario);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String newEmailPage() {
        return "newmail?faces-redirect=true&idUsuario= " + veriController.getUsuario().getId();
    }

    public String redirectViewMailPage() {
        return "usermails?faces-redirect=true&idUsuario=" + veriController.getUsuario().getId();
    }

    public static void sendmail() {
        EmailService es = new EmailService();
        es.simpleEmail(veriController.getUsuario(), NotificationController.asunto, NotificationController.cuerpo, NotificationController.destinatario);
    }
// Automatic emails funcionality works with postConstruct timesList for more information ask alexis,,, please dont alter this method
    public String quartzMail() {

        String cron = "";
        try {
            if (this.shedule.equalsIgnoreCase("40s")) {

                cron = "0/40 * * * * ?";
            } else if (this.shedule.equalsIgnoreCase("2min")) {
                cron = "0 0/2 0 ? * * *";
            } else if (this.shedule.equalsIgnoreCase("10min")) {
                cron = "00/10 0?***";
            }

            System.out.println(cron);

            JobDetail quartzJob = JobBuilder.newJob(QuartzJoB.class).withIdentity("jobs", "groups").build();
            Trigger emailTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger1", "groups").withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
            scheduler1.start();
            scheduler1.scheduleJob(quartzJob,emailTrigger);

            Thread.sleep(100000);

            scheduler1.shutdown();

            System.out.println("email has been send");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "automatic mail has benn succesfullly send";

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
        return notif;
    }

    public void setNotifs(List<Notificacion> notifs) {
        this.notif = notifs;
    }

    public NotifDao getNdao() {
        return ndao;
    }

    public void setNdao(NotifDao ndao) {
        this.ndao = ndao;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        NotificationController.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        NotificationController.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        NotificationController.cuerpo = cuerpo;
    }

    public VerificationController getVeriController() {
        return veriController;
    }

    public void setVeriController(VerificationController veriController) {
        NotificationController.veriController = veriController;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}
