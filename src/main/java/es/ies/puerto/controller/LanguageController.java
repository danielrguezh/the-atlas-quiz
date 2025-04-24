package es.ies.puerto.controller;

import es.ies.puerto.controller.abstractas.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

public class LanguageController extends AbstractController{
    @FXML Button buttonEnglish;
    @FXML Button buttonSpanish;
    
    /**
     * Methot that redirects to the login screen
     */
    @FXML
    protected void onEnglishButton(){
        mostrarPantalla(buttonEnglish, "login.fxml");
    }

    /**
     * Metodo que redirige a la pantalla de login
     */
    @FXML
    protected void onSpanishButton(){
        mostrarPantalla(buttonSpanish, "login.fxml");
    }
}
