package es.ies.puerto.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.QuestionEntity;
import es.ies.puerto.model.entities.Rank;
import es.ies.puerto.model.entities.UserEntity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public class JuegoController extends ProfileController{
    private QuestionEntity preguntaEntity;


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
    private ImageView questionImageView; 

    @FXML
    private Text questionDescriptionText;

    @FXML
    private Text textPalabra;

    @FXML
    private TextField answerTextField;

    @FXML
    private Text textMensaje;

    @FXML
    private Button insertarButton;

    @FXML
    private Button reiniciarButton;

    @FXML
    private Button buttonVolverAtras;

    Timeline timeline;
    int segundosRespuesta;

    @FXML
    public void initialize() {
        cambiarIdioma();
        iniciarJuego();
    }

    /**
     * Metodo que carga los datos de usuario
     * @param usuario
     * public void cargarDatosUsuario(UserEntity usuario) {
        if (usuario != null) {
            textUsuarioMostrar.setText( usuario.getUser());
            textLevel.setText("Nivel: " + usuario.getLevel());
            textRank.setText("Rango: " +usuario.getRank());
            iniciarJuego();
        }
    }
     */
    

    /**
     * Metodo que inicializa el juego
     */
    private void iniciarJuego() {
        cargarDatosUsuario(player);
        cargarPreguntaAleatoria(categoryString);
        
    }
    

    /**
     * Metodo que carga una pregunta aleatoria segun categoria y rango
     * @param categoria
     */
    private void cargarPreguntaAleatoria(String categoria) {
        try {
            List<QuestionEntity> preguntas = getQuestionService().obtenerPreguntaAleatoriaPorCategoria(categoria, Rank.valueOf(textRank.toString()));
            if (!preguntas.isEmpty()) {
               preguntaEntity = preguntas.get(0);
               URL imageUrl = getClass().getResource(preguntaEntity.getRutaImagen());

            if (imageUrl != null) {
                Image image = new Image(imageUrl.toExternalForm());
                questionImageView.setImage(image);
            }
               questionDescriptionText.setText(preguntaEntity.getDescription());
            } else {
                questionDescriptionText.setText("No se han encontrado preguntas validas para su rango, vuelva a inentarlo o juegue otro modo para subir de rango");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        empezarTiempo();
    }

    /**
    * Metodo para iniciar el temporizador
    * Se encarga de iniciar el temporizador y actualizar el tiempo transcurrido cada segundo
    */
    private void empezarTiempo() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(100), e -> {
            segundosRespuesta--;
            actualizarTemporizador();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
    * Metodo para actualizar el temporizador
    */
    private void actualizarTemporizador() {
        int segundos = segundosRespuesta % 60;
        labelTimer.setText(segundos+ " s");
    }

    /**
    * Metodo para detener el temporizador
    */
    private void pararTemporizador() {
        if (timeline != null || segundosRespuesta==0) {
            timeline.stop();
        }
    }

    @FXML
    protected void onInsertarClick(){
        if (answerTextField.getText().contains(preguntaEntity.getAnswer())) {
            pararTemporizador();
            puntuar();
            textMensaje.setText("Respuesta correcta.");
        }
        textMensaje.setText("Respuesta incorrecta.");
    }

    private void puntuar(){
        int puntuacion= segundosRespuesta;
        switch (preguntaEntity.getCategory()) {
            case "TOURIST":
                puntuacion= (int) (puntuacion *1.5);
                break;
            case "NOMAD":
                puntuacion= puntuacion *2;
                break;
            case "CARTOGRAPHER":
                puntuacion= puntuacion *3;
                break;
            case "CARTOGRAPH_MASTER":
                puntuacion= puntuacion *5;
                break;
            default:
                break;    
                 
        }
        int newLevel= puntuacion+player.getLevel();
        player.setLevel(newLevel);

        if (player.getLevel() > 3000 && player.getRank().equals(Rank.BEGINNER)) {
            player.setRank(player.getRank().rankUp());
        }
        if (player.getLevel() > 10000 && player.getRank().equals(Rank.TOURIST)) {
            player.setRank(player.getRank().rankUp());
        }
        if (player.getLevel() > 25000 && player.getRank().equals(Rank.NOMAD)) {
            player.setRank(player.getRank().rankUp());
        }
        if (player.getLevel() > 40000 && player.getRank().equals(Rank.CARTOGRAPHER)) {
            player.setRank(player.getRank().rankUp());
        }

        try {
            getUsuarioServiceSqlite().actualizarUsuario(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que reinicia la pantalla
     */
    @FXML
    protected void onReiniciarClick() {
        iniciarJuego();
    }

    /**
     * Metodo que redirige a la pantalla de profile
     */
    @FXML
    protected void onVolverAtrasClick() {
        try {
            List<UserEntity> usuarios = getUsuarioServiceSqlite().obtenerUsuarioPorEmailOUser(textUsuarioMostrar.getText());
            if (!usuarios.isEmpty()) {
                UserEntity usuario = usuarios.get(0);
                mostrarPantalla(buttonVolverAtras, "profile.fxml", usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}