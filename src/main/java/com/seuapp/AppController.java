package com.seuapp;

import javax.bluetooth.BluetoothStateException;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AppController {

    private boolean isMaximized = false;
    private Rectangle2D previousWindowBounds = null;

    @FXML
    private Button btnClose, btnMinimize, btnMaximize;

    @FXML
    private Label lblContent;

    @FXML
    private HBox windowControls; // Sua "title bar" customizada

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void initialize() {
        makeStageDraggable();
    }

    private void makeStageDraggable() {
        windowControls.setOnMousePressed(event -> {
            Stage stage = (Stage) windowControls.getScene().getWindow();
            // Quando o mouse é pressionado, armazena a diferença entre a posição da janela
            // e a posição do cursor
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });

        windowControls.setOnMouseDragged(event -> {
            Stage stage = (Stage) windowControls.getScene().getWindow();
            // Durante o arrasto, atualiza a posição da janela com base na posição original
            // + o deslocamento do mouse
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
    }

    @FXML
    private void handleMinimize() {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleMaximize() {
        Stage stage = (Stage) btnMaximize.getScene().getWindow();
        if (!isMaximized) {
            // Salva os limites da janela antes de maximizar
            previousWindowBounds = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());

            // Obtém os limites da área de trabalho visual, excluindo a barra de tarefas
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

            // Aplica os limites da área de trabalho visual à janela
            stage.setX(visualBounds.getMinX());
            stage.setY(visualBounds.getMinY());
            stage.setWidth(visualBounds.getWidth());
            stage.setHeight(visualBounds.getHeight());

            btnMaximize.setText("❐"); // Atualiza o ícone/texto para "restaurar"
            isMaximized = true;
        } else {
            // Restaura a janela ao seu tamanho e posição anteriores
            if (previousWindowBounds != null) {
                stage.setX(previousWindowBounds.getMinX());
                stage.setY(previousWindowBounds.getMinY());
                stage.setWidth(previousWindowBounds.getWidth());
                stage.setHeight(previousWindowBounds.getHeight());
            }

            btnMaximize.setText("□"); // Atualiza o ícone/texto para "maximizar"
            isMaximized = false;
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleDiscover() {
        lblContent.setText("Listando dispositivos...");
        BluetoothManager bluetoothManager = new BluetoothManager();
        try {
            bluetoothManager.startDiscovery();
        } catch (BluetoothStateException e) {
            e.printStackTrace();
            lblContent.setText("Erro ao iniciar a descoberta de dispositivos Bluetooth.");
        }
    }

    @FXML
    private void handleManage() {
        lblContent.setText("Gerenciar dispositivos...");
    }

    @FXML
    private void handleLog() {
        lblContent.setText("Log de rede bluetooth dos dispositivos conectados...");
    }
}
