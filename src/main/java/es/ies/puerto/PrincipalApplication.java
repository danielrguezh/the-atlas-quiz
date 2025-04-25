package es.ies.puerto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

public class PrincipalApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = this.getClass().getResource("css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        Image icon = new Image(getClass().getResource("/es/ies/puerto/img/icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("The Atlas Quiz");

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        // Calcular el tamaño de la ventana con relación de aspecto 16:9
        double windowWidth = screenWidth * 0.8; // 80% del ancho de la pantalla
        double windowHeight = windowWidth * 9 / 16; // Mantener la relación de aspecto 16:9

        // Establecer el tamaño de la ventana
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);

        // Centrar la ventana en la pantalla
        stage.setX((screenWidth - windowWidth) / 2);
        stage.setY((screenHeight - windowHeight) / 2);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}