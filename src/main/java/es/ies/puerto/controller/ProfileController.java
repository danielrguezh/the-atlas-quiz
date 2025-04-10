package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.List;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.UsuarioEntitySqlite;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

public class ProfileController extends AbstractController {

    @FXML
    private TextField textFieldUsuario;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldNivel;

    @FXML
    private Button openEditarButton;

    @FXML
    private Button openJugarButton;

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
            textFieldUsuario.setText(usuario.getUser());
            textFieldNombre.setText(usuario.getName());
            textFieldEmail.setText(usuario.getEmail());
            textFieldNivel.setText(String.valueOf(usuario.getIdNivel()));
        }
    }

    /**
     * Metodo que redirige a la pantalla de la lista de registro
     */
    @FXML
    protected void openEditarClick() {
        try {
            List<UsuarioEntitySqlite> usuarios = getUsuarioServiceSqlite().obtenerUsuarioPorEmailOUser(textFieldUsuario.getText());
            if (!usuarios.isEmpty()) {
                UsuarioEntitySqlite usuario = usuarios.get(0);
                mostrarPantalla(openEditarButton, "registro.fxml", usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que redirige a la pantalla de juego
     */
    @FXML
    protected void openJugarClick() {
        try {
            List<UsuarioEntitySqlite> usuarios = getUsuarioServiceSqlite().obtenerUsuarioPorEmailOUser(textFieldUsuario.getText());
            if (!usuarios.isEmpty()) {
                UsuarioEntitySqlite usuario = usuarios.get(0);
                mostrarPantalla(buttonVolverAtras, "juego.fxml", usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que redirige a la pantalla de la lista de usuarios
     */
    @FXML
    protected void onVolverAtrasClick() {
        mostrarPantalla(buttonVolverAtras, "usuarios.fxml");
    }
}