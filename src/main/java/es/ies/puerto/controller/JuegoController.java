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
public class JuegoController extends AbstractController{
    private QuestionEntity preguntaEntity;
    UserEntity player;
    String categoria;

    @FXML
    ImageView userImageView;

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
    int segundosRespuesta = 100;

    @FXML
    public void initialize() {
        cambiarIdioma();
    }

    /**
     * Metodo que carga los datos de usuario
     * @param usuario
     */
    public void cargarDatosUsuario(UserEntity usuario, String categoryString) {
        if (usuario != null) {
            player=usuario;
            categoria=categoryString;
            textUsuarioMostrar.setText( usuario.getUser());
            textLevel.setText("Nivel: " + usuario.getLevel());
            textRank.setText("Rango: " +usuario.getRank());
            String imagePath = "/es/ies/puerto/img/flags/" + usuario.getCountry() + ".png";
            URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl != null) {
                Image image = new Image(imageUrl.toExternalForm());
                userImageView.setImage(image);
            }
            cargarPreguntaAleatoria(categoria);
        }
    }
    

    /**
     * Metodo que inicializa el juego
     */
    private void iniciarJuego() {
        cargarDatosUsuario(player, categoria);
        answerTextField.setText("");
        textMensaje.setText("");
    }
    

    /**
     * Metodo que carga una pregunta aleatoria segun categoria y rango
     * @param categoria
     */
    private void cargarPreguntaAleatoria(String categoria) {
        try {
            Rank rangoJugador = player.getRank();
    
            List<QuestionEntity> preguntas =
                getQuestionService()
                    .obtenerPreguntaAleatoriaPorCategoria(categoria, rangoJugador);
            System.out.println(categoria + " " + rangoJugador);
            if (!preguntas.isEmpty()) {
                preguntaEntity = preguntas.get(0);
                if (categoria == "banderas") {
                    String imagePath = "/" +preguntaEntity.getRutaImagen();
                    System.out.println(imagePath);
                    URL imageUrl = getClass().getResource(imagePath);
                    if (imageUrl != null) {
                        Image image = new Image(imageUrl.toExternalForm());
                        questionImageView.setImage(image);
                    }
                } else {
                    questionImageView.setImage(null);
                    
                }
                questionDescriptionText.setText(preguntaEntity.getDescription());
            } else {
                questionDescriptionText.setText(
                    "No se han encontrado preguntas válidas para su rango…"
                );
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
        pararTemporizador();
        segundosRespuesta = 100;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
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
        labelTimer.setText(segundosRespuesta+ " s");
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
        String respuesta = answerTextField.getText().trim();
        if (respuesta.equalsIgnoreCase(preguntaEntity.getAnswer())) {
            pararTemporizador();
            puntuar();
            textMensaje.setText("Respuesta correcta.");
        } else {
            textMensaje.setText("Respuesta incorrecta.");
        }
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
        textLevel.setText("Nivel: " + newLevel);

        if (player.getLevel() > 3000 && player.getRank().equals(Rank.BEGINNER)) {
            player.setRank(Rank.TOURIST);
        }
        if (player.getLevel() > 10000 && player.getRank().equals(Rank.TOURIST)) {
            player.setRank(Rank.NOMAD);
        }
        if (player.getLevel() > 25000 && player.getRank().equals(Rank.NOMAD)) {
            player.setRank(Rank.CARTOGRAPHER);
        }
        if (player.getLevel() > 40000 && player.getRank().equals(Rank.CARTOGRAPHER)) {
            player.setRank(Rank.CARTOGRAPH_MASTER);
        }

        try {
            getUsuarioServiceSqlite().updateLevel(newLevel, player.getEmail());
            System.out.println(newLevel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que reinicia la pantalla
     */
    @FXML
    protected void onReiniciarClick() {
        pararTemporizador();
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