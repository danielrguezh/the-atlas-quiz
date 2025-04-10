package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.regex.Pattern;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.UsuarioEntitySqlite;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

public class RegistroController extends AbstractController{

    private UsuarioEntitySqlite usuarioEditado;

    @FXML
    public Text textRegistroTitulo;

    @FXML 
    private TextField textFieldUsuario;

    @FXML 
    private PasswordField textFieldPassword;

    @FXML 
    private PasswordField textFieldPasswordRepit;

    @FXML 
    private TextField textFieldNombre;

    @FXML 
    private TextField textFieldEmail;

    @FXML 
    private TextField textFieldEmailRepit;

    @FXML 
    private Text textMensaje;

    @FXML 
    private Button openRegistrarButton;

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
     * Carga los datos del usuario en los campos de la interfaz grafica
     * @param usuario con los datos que se mostraran en pantalla
     */
    public void cargarDatosUsuario(UsuarioEntitySqlite usuario) {
        if (usuario != null) {
            this.usuarioEditado = usuario;
            textFieldUsuario.setText(usuario.getUser());
            textFieldNombre.setText(usuario.getName());
            textFieldEmail.setText(usuario.getEmail());
        }
    }

    /**
     * Maneja el evento de clic en el boton de registro
     * Valida los datos ingresados por el usuario y crea un nuevo usuario si son correctos
     * Muestra mensajes de error si los datos no cumplen con los requisitos
     */
    @FXML
    protected void onRegistrarButtonClick() {
        if (textFieldUsuario == null || textFieldUsuario.getText().isEmpty()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorUsuarioVacio"));
            return;
        }
        if (textFieldPassword == null ||  textFieldPassword.getText().isEmpty() 
            || textFieldPasswordRepit == null || textFieldPasswordRepit.getText().isEmpty()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorPasswordVacio"));
            return;
        }
        if (textFieldNombre == null || textFieldNombre.getText().isEmpty()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorNombreVacio"));
            return;
        }
        if (textFieldEmail == null ||  textFieldEmail.getText().isEmpty() 
            || textFieldEmailRepit == null || textFieldEmailRepit.getText().isEmpty()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorEmailVacio"));
            return;
        }
        if (textFieldPassword.getText().length() < 8) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorPasswordLongitud"));
            return;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(textFieldEmail.getText()).matches()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorEmailFormato"));
            return;
        }
        boolean equalPassword = textFieldPassword.getText().equals(textFieldPasswordRepit.getText());
        boolean equalEmail = textFieldEmail.getText().equals(textFieldEmailRepit.getText());
        if (equalPassword && equalEmail) {
            try {
                if (usuarioEditado == null) { 
                    UsuarioEntitySqlite nuevoUsuario = new UsuarioEntitySqlite(
                        textFieldUsuario.getText(),
                        textFieldEmail.getText(),
                        textFieldNombre.getText(),
                        textFieldPassword.getText()
                    );
                    boolean insertado = getUsuarioServiceSqlite().insertarUsuario(nuevoUsuario);
                    if (!insertado) {
                        textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorUsuarioExiste"));
                        return;
                    }
                    mostrarPantalla(openRegistrarButton, "profile.fxml", nuevoUsuario);
                } else {
                    usuarioEditado.setUser(textFieldUsuario.getText());
                    usuarioEditado.setEmail(textFieldEmail.getText());
                    usuarioEditado.setName(textFieldNombre.getText());
                    if (!textFieldPassword.getText().equals("")) {
                        usuarioEditado.setPassword(textFieldPassword.getText());
                    }
                    boolean actualizado = getUsuarioServiceSqlite().actualizarUsuario(usuarioEditado);
                    if (!actualizado) {
                        textMensaje.setText("Error al actualizar el usuario");
                        return;
                    }
                    mostrarPantalla(openRegistrarButton, "profile.fxml", usuarioEditado);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorEmailOPasswordNoCoincide"));
    }

    /**
     * Metodo que redirige a la pantalla de inicio de sesion (login)
     */
    @FXML
    protected void onVolverAtrasClick() {
        mostrarPantalla(buttonVolverAtras, "login.fxml");
    }
}