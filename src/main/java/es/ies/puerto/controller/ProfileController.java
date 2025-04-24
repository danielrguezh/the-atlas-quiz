package es.ies.puerto.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.UserEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

public class ProfileController extends AbstractController {
    String categoryString;
    UserEntity player;

    @FXML
    ImageView userImageView;

    @FXML
    private Text textUsuarioMostrar;

    @FXML
    private Text textLevel;

    @FXML
    private Text textRank;

    
    @FXML
    private Button capitalesButton;

    @FXML
    private Button banderasButton;

    @FXML
    private Button poblacionButton;

    @FXML
    private Button pibButton;

    @FXML
    private Button pibPCButton;


    @FXML
    private Button openEditarButton;

    @FXML
    private Button buttonVolverAtras;

    /**
     * Metodo de inicializacion de la interfaz
     */
    @FXML
    public void initialize() {
        cambiarIdioma();
        cargarDatosUsuario(player);
        
    }

    /**
     * Carga los datos del usuario en los campos de la interfaz grafica
     * @param usuario con los datos que se mostraran en pantalla
     */
    public void cargarDatosUsuario(UserEntity usuario) {
        if (usuario != null) {
            player=usuario;
            textUsuarioMostrar.setText(usuario.getUser());
            textLevel.setText(String.valueOf(usuario.getLevel()));
            textRank.setText(usuario.getRank().toString());
            String imagePath = "/es/ies/puerto/img/flags/" + usuario.getCountry() + ".png";
            URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl != null) {
                Image image = new Image(imageUrl.toExternalForm());
                userImageView.setImage(image);
            }
            
        }
    }

    @FXML
    protected void onCapitales() {
        categoryString="capitales";
        selectGame(capitalesButton);
    }

    @FXML
    protected void onBanderas() {
        categoryString="banderas";
        selectGame(banderasButton);
    }

    @FXML
    protected void onPoblacion() {
        categoryString="capitales";
        selectGame(poblacionButton);
    }

    @FXML
    protected void onPib() {
        categoryString="capitales";
        selectGame(pibButton);
    }

    @FXML
    protected void onPibPC() {
        categoryString="capitales";
        selectGame(pibPCButton);
    }

    /** 
     * Metodo que redirige a la pantalla de juego
     */
    protected void selectGame(Button button) {
        try {
            List<UserEntity> usuarios = getUsuarioServiceSqlite().obtenerUsuarioPorEmailOUser(textUsuarioMostrar.getText());
            if (!usuarios.isEmpty()) {
                UserEntity usuario = usuarios.get(0);
                mostrarPantalla(button, "juego.fxml", usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que redirige a la pantalla de la lista de registro
     */
    @FXML
    protected void openEditarClick() {
        try {
            List<UserEntity> usuarios = getUsuarioServiceSqlite().obtenerUsuarioPorEmailOUser(textUsuarioMostrar.getText());
            if (!usuarios.isEmpty()) {
                player = usuarios.get(0);
                mostrarPantalla(openEditarButton, "registro.fxml", player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que redirige a la pantalla de login
     */
    @FXML
    protected void onVolverAtrasClick() {
        mostrarPantalla(buttonVolverAtras, "login.fxml");
    }
}