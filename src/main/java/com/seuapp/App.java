package com.seuapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));

        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED); // Garante que a janela não tenha
        // decoração
        stage.setScene(scene);

        // Define a largura e altura da janela
        double width = 1024;
        double height = 768;
        stage.setWidth(width);
        stage.setHeight(height);

        // Obtém as dimensões da tela principal
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        Platform.runLater(() -> {
            // Calcula as posições X e Y para a janela ser centralizada
            double x = (screenBounds.getWidth() - width) / 2;
            double y = (screenBounds.getHeight() - height) / 2;

            // Define a posição da janela
            stage.setX(x);
            stage.setY(y);
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
