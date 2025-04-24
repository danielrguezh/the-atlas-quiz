package es.ies.puerto.model.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.model.abstractas.Conexion;
import es.ies.puerto.model.entities.QuestionEntity;
import es.ies.puerto.model.entities.Rank;

/**
 * @author danielrguezh
 * @version 1.0.0
 */ 
public class QuestionService extends Conexion{

    /**
     * Constructor por defecto
     */
    public QuestionService() {

    }

    /**
     * Contructor con la ruta del archivo de la bbdd
     * @param unaRutaArchivoBD path de la bbdd
     * @throws SQLException
     */
    public QuestionService(String unaRutaArchivoBD) throws SQLException {
        super(unaRutaArchivoBD);
    }

    public List<QuestionEntity> obtenerPreguntaAleatoriaPorCategoria(String categoria, Rank rank) throws SQLException {
        String sql = "SELECT * FROM preguntas WHERE category = ? AND rank = ? ORDER BY RANDOM() LIMIT 1";
        return obtenerPregunta(sql, categoria, rank.toString());
    }

    private List<QuestionEntity> obtenerPregunta(String sql, String... parametros) throws SQLException{
        List<QuestionEntity> preguntas = new ArrayList<QuestionEntity>();
        try {
            PreparedStatement sentencia = getConnection().prepareStatement(sql);
            for (int i = 0; i < parametros.length; i++) {
                sentencia.setString(i + 1, parametros[i]);
            }
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                int id = resultado.getInt("id");
                String categoryStr = resultado.getString("category");
                String descriptionStr = resultado.getString("description");
                String answerStr = resultado.getString("answer");
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
                String rutaImagenStr = resultado.getString("rutaImagen");
                QuestionEntity questionEntity = new QuestionEntity(id, categoryStr, descriptionStr, answerStr, rank, rutaImagenStr);
                preguntas.add(questionEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return preguntas;
    }
}
 