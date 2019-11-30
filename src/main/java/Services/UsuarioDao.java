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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class UsuarioDao extends Conexion implements IDao<Usuario> {

    
    public int insertar(Usuario usr) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        this.conectar();
        try {

            this.conectar();
            String sql = ("INSERT INTO usuario(id,nombre,apellido,email,contrasena) VALUES (?,?,?,?,?)");           
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usr.getId());
            stmt.setString(2, usr.getNombre());
            stmt.setString(3, usr.getApellido());
            stmt.setString(4, usr.getEmail());
            stmt.setString(5, usr.getContrasena());
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
        return 0;

    }

    @Override
    public void insert(Usuario data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Usuario data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Usuario data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
