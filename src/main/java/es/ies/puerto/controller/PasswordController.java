package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.List;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.UserEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

public class PasswordController extends AbstractController{

    @FXML 
    private Text textEmail;

    @FXML 
    private TextField textFieldEmail;

    @FXML 
    private Text textMensaje;

    @FXML 
    private Button openAceptarButton;

    @FXML
    private Button buttonVolverAtras;

    /**
     * Metodo de inicializacion de la interfaz
     */
    @FXML
    public void initialize() {
        cambiarIdioma();
    }

    /**
     * Metodo que verifica si el email ingresado es valido y si existe en el sistema
     */
    @FXML
    protected void onPasswordButtonClick() {
        if (textFieldEmail == null ||  textFieldEmail.getText().isEmpty()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorEmailVacioNulo"));
            return;
        }
        List<UserEntity> email;
        try {
            email = getUsuarioServiceSqlite().obtenerUsuarioPorEmailOUser(textFieldEmail.getText());
            if (email == null) {
                textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorEnviarEmail"));
                return;
            }
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("emailEnvioCorrecto"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Metodo que redirige a la pantalla de inicio de sesion (login)
     */
    @FXML
    protected void onVolverAtrasClick() {
        mostrarPantalla(buttonVolverAtras, "login.fxml");
    }
}