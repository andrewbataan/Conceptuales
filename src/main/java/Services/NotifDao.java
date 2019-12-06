package Services;

import Model.Notificacion;
import Services.Idao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairf
 */
public class NotifDao extends Conexion implements IDao<Notificacion> {

    @Override
    public void insert(Notificacion data) {
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {

            this.conectar();
            stmt = conn.prepareStatement("insert into notificacion(idUsuario,asunto,cuerpo,destinatario) values (?,?,?,?);");
            stmt.setInt(1, data.getIdUsuario());
            stmt.setString(2, data.getAsunto());
            stmt.setString(3, data.getCuerpo());
            stmt.setString(4, data.getDestinatario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: No se puedo agregar");
            e.printStackTrace();

        } finally {
            try {
                if (stmt.isClosed()) {
                } else {
                    stmt.close();
                }

                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Notificacion data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Notificacion data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Notificacion> userEmails(int idUsuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Notificacion> notificaciones = new ArrayList<>();
        Notificacion notificacion = null;
        //This method is for get the users.noti info //
        try {
            this.conectar();
            ps = conn.prepareStatement("SELECT usuario.nombre AS usuario, notificacion.asunto, notificacion.cuerpo, notificacion.destinatario "
                    + "FROM notificacion INNER JOIN  usuario ON notificacion.idUsuario = usuario.idUsuario "
                    + "WHERE notificacion.idUsuario = ?;");
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                notificacion = new Notificacion();
                notificacion.setUser(rs.getString("usuario"));
                notificacion.setAsunto(rs.getString("asunto"));
                notificacion.setCuerpo(rs.getString("cuerpo"));
                notificacion.setDestinatario(rs.getString("destinatario"));
                notificaciones.add(notificacion);
            }
        } catch (Exception ex) {
            System.out.println("No se puedo realizar la consulta de las notificaciones...");
        } finally {
            try {
                if (!stmt.isClosed()) {
                    stmt.close();
                }
                if (!rs.isClosed()) {
                    rs.close();
                }
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(Notificacion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return notificaciones;
    }

    @Override
    public List<Notificacion> getAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Notificacion> notificaciones = new ArrayList<>();
        Notificacion notificacion = null;

        try {
            this.conectar();
            ps = conn.prepareStatement("SELECT * FROM notificacion WHERE idUsuario;");

            rs = ps.executeQuery();
            while (rs.next()) {
                notificacion = new Notificacion();
                notificacion.setUser(rs.getString("usuario"));
                notificacion.setAsunto(rs.getString("asunto"));
                notificacion.setCuerpo(rs.getString("cuerpo"));
                notificacion.setDestinatario(rs.getString("destinatario"));
                notificacion.setIdUsuario(rs.getInt("idUsuario"));
                notificacion.setIdNotificacion(rs.getInt("idNotificacion"));
                notificaciones.add(notificacion);
            }
        } catch (Exception ex) {
            System.out.println("No se puedo realizar la consulta de las notificaciones...");
        } finally {
            try {
                if (!stmt.isClosed()) {
                    stmt.close();
                }
                if (!rs.isClosed()) {
                    rs.close();
                }
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(Notificacion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return notificaciones;
    }
}
