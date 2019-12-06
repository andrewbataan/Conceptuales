/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Model.Usuario;
import Services.Idao.IDao;
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
public class UsuarioDao extends Conexion implements IDao<Usuario> {

    public int insertar(Usuario usr) {

        return 0;

    }

    @Override
    public void insert(Usuario data) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        this.conectar();
        try {

            this.conectar();
            String sql = ("INSERT INTO usuario(idUsuario,nombre,apellido,email,contrasena) VALUES (?,?,?,?,?)");
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, data.getId());
            stmt.setString(2, data.getNombre());
            stmt.setString(3, data.getApellido());
            stmt.setString(4, data.getEmail());
            stmt.setString(5, data.getContrasena());
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
    public void delete(Usuario data) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("delete from usuario where idUsuario = ?;");
            stmt.setInt(1, data.getId());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            System.out.println("User has been deleted");
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
        PreparedStatement stmt = null;

        try {

            this.conectar();
            String sql = ("Update usuario set nombre = ?,apellido = ?,email = ?,contrasena= ? where idUsuario =? ;");
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, data.getNombre());
            stmt.setString(2, data.getApellido());
            stmt.setString(3, data.getEmail());
            stmt.setString(4, data.getContrasena());
            stmt.setInt(5, data.getId());
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
     public Usuario verificarUsuario(String email, String contrasena) {
        ResultSet rs = null;
       PreparedStatement stmt = null;
        Usuario usuario = null;
        try {
            this.conectar();
            //STEP 3: Execute a query
                String sql = "SELECT * FROM usuario where email =  ? AND contrasena =   ? ";
            stmt = conn.prepareStatement(sql);
        stmt.setString(1,email);
           stmt.setString(2,contrasena);
            rs = stmt.executeQuery(sql);

            //STEP 3.1: Extract data from result set
            while (rs.next()) {
               usuario= new Usuario(rs.getInt("idUsuario"),rs.getString("nombre"),rs.getString("apellido1"),rs.getString("email"),rs.getString("contrasena"));
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
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
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return usuario;
        
     }

    public List<Usuario> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}