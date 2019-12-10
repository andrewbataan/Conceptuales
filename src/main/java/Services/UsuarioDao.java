/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Model.Usuario;
import Services.Idao.IDao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class UsuarioDao implements IDao<Usuario>, Serializable {

    private static final long serialVersionUID = 1L;
    private final Conector conectorJDBC = Conector.getConector();

    @Override
    public void insert(Usuario data) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection connectionDB = conectorJDBC.conectar();
        try {

            String sql = ("INSERT INTO usuario(idUsuario,nombre,apellido,email,contrasena) VALUES (?,?,?,?,?);");
            stmt = connectionDB.prepareStatement(sql);
            stmt.setInt(1, data.getId());
            stmt.setString(2, data.getNombre());
            stmt.setString(3, data.getApellido());
            stmt.setString(4, data.getEmail());
            stmt.setString(5, data.getContrasena());
            stmt.executeUpdate();
            stmt.close();
            connectionDB.close();
            System.out.println("Cuenta creada con exito!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error a la hora de crear tu cuenta, hubo un problema con la Base de Datos");
        }
    }

    @Override
    public void delete(Usuario data) {
        Connection connectionDB = conectorJDBC.conectar();
        PreparedStatement stmt = null;
        try {
            stmt = connectionDB.prepareStatement("delete from usuario where idUsuario = ?;");
            stmt.setInt(1, data.getId());
            stmt.executeUpdate();
            stmt.close();
            connectionDB.close();
            System.out.println("Usuario Eliminado con Exito!");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw new SQLException("Error a la hora de eliminar el Usuario");
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Usuario data) {
        ResultSet rs = null;
        PreparedStatement ps= null;
      Connection conn= conectorJDBC.conectar();
        try {

            String sql = ("Update usuario set nombre = ?,apellido = ?,email = ?,contrasena= ? where idUsuario =? ;");
          ps = conn.prepareStatement(sql);
            ps.setString(1, data.getNombre());
            ps.setString(2, data.getApellido());
            ps.setString(3, data.getEmail());
            ps.setString(4, data.getContrasena());
            ps.setInt(5, data.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error, no se pudo modificar el usuario seleccionado!");
        } finally {
            conectorJDBC.cerrarConexion(conn, ps, rs);
        }
    }
    

    public Usuario sign(String user, String password) throws SQLException,ClassNotFoundException {
         Connection conn = conectorJDBC.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {
          
            //STEP 3: Execute a query
           
            ps = conn.prepareStatement("SELECT * FROM conceptual.usuario WHERE email = ? and contrasena = ? ");
            ps.setString(1, user);
            ps.setString(2, password);
            rs = ps.executeQuery();

            //STEP 3.1: Extract data from result set
            while (rs.next()) {
                usuario = new Usuario ();
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setId(rs.getInt("idUsuario"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error, no se pudo completar la conexion a la Base de Datos");
        } finally {
           conectorJDBC.cerrarConexion(conn, ps, rs);
        }

        return usuario;

    }

    public List<Usuario> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
