package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.List;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.PalabraEntitySqlite;
import es.ies.puerto.model.entities.UsuarioEntitySqlite;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
    private Text textNivel;

    @FXML
    private Text textPuntos;

    @FXML
    private Text textVictorias;

    @FXML
    private Canvas ahorcadoCanvas;

    @FXML
    private Text textIntentos;

    @FXML
    private Text textLetrasUsadas;

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

    
}