package com.main.controller.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public AnchorPane uiMainWindow;
    @FXML
    public AnchorPane categories;
    @FXML
    public AnchorPane products;
    @FXML
    public AnchorPane fact;
    @FXML
    public AnchorPane arrival;
    @FXML
    public AnchorPane consumption;
    @FXML
    public AnchorPane customers;
    @FXML
    public AnchorPane orders;
    @FXML
    public AnchorPane sales;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    public static boolean hasOnlyNumbers(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static void showModalWindow(String title, Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}

