package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.List;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.QuestionEntity;
import es.ies.puerto.model.entities.Rank;
import es.ies.puerto.model.entities.UserEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public class JuegoController extends AbstractController{

    @FXML
    private Text textUsuarioMostrar;

    @FXML
    private Text textLevel;

    @FXML
    private Text textRank;

    @FXML
    private Label labelTimer;

    @FXML
    private Text textIntentos;

    @FXML
    private Text questionDescriptionText;

    @FXML
    private Text textPalabra;

    @FXML
    private TextField textFieldLetra;

    @FXML
    private Text textMensaje;

    @FXML
    private Button insertarButton;

    @FXML
    private Button reiniciarButton;

    @FXML
    private Button buttonVolverAtras;

    private String usernameSinPrefijo;
    private int nivelUsuario;
    private int puntosUsuario;
    private String emailUsuario;
    private String palabraSecreta;   
    private char[] estadoPalabra;    
    private int intentosRestantes; 
    private StringBuilder letrasUtilizadas;
    private int victorias = 0;
    private final int umbralVictorias = 3;

    @FXML
    public void initialize() {
        cambiarIdioma();
    }

    /**
     * Metodo que carga los datos de usuario
     * @param usuario
     */
    public void cargarDatosUsuario(UserEntity usuario) {
        if (usuario != null) {
            textUsuarioMostrar.setText( usuario.getUser());
            textLevel.setText("Nivel: " + usuario.getLevel());
            textRank.setText("Rango: " +usuario.getRank());
            iniciarJuego();
        }
    }

    /**
     * Metodo que inicializa el juego
     */
    private void iniciarJuego() {
        cargarPalabraAleatoria(obtenerNivelUsuario());  
        intentosRestantes = 9; 
        estadoPalabra = new char[palabraSecreta.length()];
        for (int i = 0; i < estadoPalabra.length; i++) {
            estadoPalabra[i] = '_';
        }
        textPalabra.setText(formatearEstadoPalabra());
        textIntentos.setText("Intentos: " + intentosRestantes);
        textMensaje.setText("");
        textMensaje.setStyle("-fx-fill: red;");
        letrasUtilizadas = new StringBuilder();
        textLetrasUsadas.setText("Letras usadas: ");
        limpiarCanvas();
        dibujarAhorcado(); 
    }
    

    private void cargarPalabraAleatoria(String categoria) {
        try {
            List<QuestionEntity> palabras = getQuestionService().obtenerPreguntaAleatoriaPorCategoria(categoria, Rank.valueOf(textRank.toString()));
            if (!palabras.isEmpty()) {
                this.palabraSecreta = palabras.get(0).getDescription();
            } else {
                this.palabraSecreta = "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}