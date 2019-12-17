package Services;

import Model.Notificacion;
import Services.Idao.IDao;
import java.sql.Connection;
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
public class NotifDao implements IDao<Notificacion> {

    private final Conector conectorJDBC = Conector.getConector();

    @Override
    public void insert(Notificacion data) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection connectionDB = conectorJDBC.conectar();

        try {

            stmt = connectionDB.prepareStatement("insert into notificacion(idUsuario,asunto,cuerpo,destinatario) values (?,?,?,?);");
            stmt.setInt(1, data.getIdUsuario());
            stmt.setString(2, data.getAsunto());
            stmt.setString(3, data.getCuerpo());
            stmt.setString(4, data.getDestinatario());
            stmt.executeUpdate();
            stmt.close();
       connectionDB.close();
            System.out.println("Cuenta creada con exito!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error a la hora de crear tu correo, hubo un problema con la Base de Datos");
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

    public List<Notificacion> userEmails(int idUsuario) throws SQLException {
        Connection conn= conectorJDBC.conectar();

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Notificacion> notificaciones = new ArrayList<>();
        Notificacion notificacion = null;
        //This method is for get the users.noti info //
        try {
            
            ps = conn.prepareStatement("SELECT usuario.email AS usuario, notificacion.asunto, notificacion.cuerpo, notificacion.destinatario "
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
         } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error, the query is invalid");
        } finally {
            conectorJDBC.cerrarConexion(conn, ps, rs);
        
        }
        return notificaciones;
    }

    @Override
    public List<Notificacion> getAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Notificacion> notificaciones = new ArrayList<>();
        Notificacion notificacion = null;
Connection conn= conectorJDBC.conectar();
        try {
            
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
        }catch (SQLException e) {
            e.printStackTrace();
            try {
                throw new SQLException("Error, the query is invalid");
            } catch (SQLException ex) {
                Logger.getLogger(NotifDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            conectorJDBC.cerrarConexion(conn, ps, rs);
        
        }
        
        
        return notificaciones;
        
    }
}

