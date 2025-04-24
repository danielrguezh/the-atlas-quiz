package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.List;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.UserEntity;
import es.ies.puerto.model.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

public class UsuariosController extends AbstractController{

    @FXML
    private Text textListaUsuario;

    @FXML
    private TextField textFieldBuscarUsuario;

    @FXML
    private Button buscarUsuariosButton;

    @FXML
    private Text textMensaje;

    @FXML
    private ListView<UserEntity> listViewUsuarios; 

    @FXML
    private Button buttonVolverAtras;

    /**
     * Metodo de inicializacion de la interfaz
     */
    @FXML
    public void initialize() {
        cambiarIdioma();
        configurarListView();
        cargarUsuarios();
    }

    /**
     * Configura la visualizacion de la lista de usuarios ListView
     */
    private void configurarListView() {
        listViewUsuarios.setCellFactory(param -> new ListCell<UserEntity>() {
        @Override
        protected void updateItem(UserEntity usuario, boolean empty) {
            super.updateItem(usuario, empty);
            if (empty || usuario == null) {
                setText(null);
                return;
            } 
            String usuarioRow = ConfigManager.ConfigProperties.getProperty("usuarioRow");
            String countryRow = ConfigManager.ConfigProperties.getProperty("countryRow");
            String levelRow = ConfigManager.ConfigProperties.getProperty("levelRow");
            String rankRow = ConfigManager.ConfigProperties.getProperty("rankRow");

            String formato = ""+usuarioRow+": %s\n"+countryRow+": %s\n"+levelRow+": %s\n"+rankRow+": %s";
            String texto = String.format(
                formato, 
                usuario.getUser(), 
                usuario.getCountry(), 
                usuario.getLevel(),
                usuario.getRank()
                );
            setText(texto);
            }
        });
    }

    /**
     * Metodo que carga los usuarios desde la base de datos y los muestra en la ListView
     */
    private void cargarUsuarios() {
        try {
            UserService service = getUsuarioServiceSqlite();
            List<UserEntity> listaUsuarios = service.obtenerUsuarios();
            ObservableList<UserEntity> usuarios = FXCollections.observableArrayList(listaUsuarios);
            listViewUsuarios.setItems(usuarios);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para filtrar los usuarios de la lista
     */
    @FXML
    protected void onBuscarUsuarios() {
        if (textFieldBuscarUsuario == null || textFieldBuscarUsuario.getText().trim().isEmpty()) {
            cargarUsuarios();
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorCredencialesVacios"));
            return;
        }
        List<UserEntity> usuariosFiltrados;
        try {
            usuariosFiltrados = getUsuarioServiceSqlite().obtenerUsuarioPorInput(textFieldBuscarUsuario.getText());
            if (usuariosFiltrados == null || usuariosFiltrados.isEmpty()) {
                textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorUsuarioNoEncontrado"));
                cargarUsuarios();
                return;
            }
            listViewUsuarios.setItems(FXCollections.observableArrayList(usuariosFiltrados));
            textMensaje.setText(null);
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