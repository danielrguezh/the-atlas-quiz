package es.ies.puerto.model.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.model.abstractas.Conexion;
import es.ies.puerto.model.entities.Rank;
import es.ies.puerto.model.entities.UserEntity; 

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public class UserService extends Conexion{
/**
     * Constructor por defecto
     */
    public UserService() {

    }

    /**
     * Contructor con la ruta del archivo de la bbdd
     * @param unaRutaArchivoBD path de la bbdd
     * @throws SQLException
     */
    public UserService(String unaRutaArchivoBD) throws SQLException {
        super(unaRutaArchivoBD);
    }

    /**
     * Metodo que obtiene un usuario por su email o nombre de usuario
     * @param input email o nombre de usuario
     * @return usuario encontrado o null si no existe
     * @throws SQLException
     */
    public List<UserEntity> obtenerUsuarioPorEmailOUser(String input) throws SQLException {
        String sql = "SELECT * FROM users " + "WHERE email=? OR user=?";
        return obtenerUsuario(sql, input, input);
    }

    /**
     * Metodo que obtiene un usuario dado su email, user, name o id
     * @param input email o nombre de usuario
     * @return usuario encontrado o null si no existe
     * @throws SQLException
     */
    public List<UserEntity> obtenerUsuarioPorInput(String input) throws SQLException {
        String sql = "SELECT * FROM users " + "WHERE email=? OR user=? OR name=? OR id=?";
        return obtenerUsuario(sql, input, input, input, input);
    }
    
    /**
     * Metodo que obtiene todos los usuarios de la base de datos
     * @return lista de usuarios
     * @throws SQLException
     */
    public List<UserEntity> obtenerUsuarios() throws SQLException{
        String sql = "SELECT * FROM users";
        return obtenerUsuario(sql);
    }

    /**
     * Metodo que ejecuta una consulta SQL para obtener usuarios
     * @param sql consulta SQL a ejecutar
     * @return lista de usuarios obtenidos
     * @throws SQLException
     */
    private List<UserEntity> obtenerUsuario(String sql, String... parametros) throws SQLException{
        List<UserEntity> usuarios = new ArrayList<UserEntity>();
        try {
            PreparedStatement sentencia = getConnection().prepareStatement(sql);
            for (int i = 0; i < parametros.length; i++) {
                sentencia.setString(i + 1, parametros[i]);
            }
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                int usuarioId = resultado.getInt("id");
                String userStr = resultado.getString("user");
                String emailStr = resultado.getString("email");
                String contraseniaStr = resultado.getString("password");
                String countryStr = resultado.getString("country");
                int level = resultado.getInt("level");
                String rankStr = resultado.getString("rank");
                Rank rank;
                switch (rankStr) {
                    case "TOURIST":
                        rank=Rank.TOURIST;
                        break;
                    case "NOMAD":
                        rank=Rank.NOMAD;
                        break;
                    case "CARTOGRAPHER":
                        rank=Rank.CARTOGRAPHER;
                        break;
                    case "CARTOGRAPH_MASTER":
                        rank=Rank.CARTOGRAPH_MASTER;
                        break;
                    default:
                        rank=Rank.BEGINNER;
                        break;
                }
                UserEntity usuarioEntityModel = new UserEntity(usuarioId, userStr, 
                emailStr, contraseniaStr, countryStr, level, rank);
                usuarios.add(usuarioEntityModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return usuarios;
    }

    /**
     * Metodo para insertar un usuario
     * @throws SQLException
     */
    public boolean insertarUsuario(UserEntity usuario) throws SQLException {
        String sql = "INSERT INTO users (user, email, password, country, level, rank) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setString(1, usuario.getUser());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setString(3, usuario.getPassword());
            sentencia.setString(4, usuario.getCountry());
            sentencia.setInt(5, usuario.getLevel());
            sentencia.setString(6, usuario.getRank().toString());
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return false;
    }

    public boolean actualizarUsuario(UserEntity usuario) throws SQLException {
        String sql = "UPDATE users SET user = ?, email = ?,  password = ?, country = ?, level = ?, rank = ? WHERE id = ?";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setString(1, usuario.getUser());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setString(3, usuario.getPassword());
            sentencia.setString(4, usuario.getCountry());
            sentencia.setInt(5, usuario.getLevel());
            sentencia.setString(6, usuario.getRank().toString());
            sentencia.setInt(5, usuario.getId());
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return false;
    }

    public boolean updateLevel(int level, String email) throws SQLException {
        String sql = "UPDATE users SET level = ?, WHERE email = ?";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setInt(1, level);
            sentencia.setString(2, email);
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return false;
    }

    public boolean updateRank(String nivel, String email) throws SQLException {
        String sql = "UPDATE users SET id_nivel = ? WHERE email = ?";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setString(1, nivel);
            sentencia.setString(2, email);
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return false;
    }
}
