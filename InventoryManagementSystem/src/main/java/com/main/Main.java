package com.main;

import com.main.controller.MainController;
import com.main.model.jpa.EntityManagerConnector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
            primaryStage.setTitle("Inventory management system");

            Scene scene = new Scene(root, 1080, 800);
            scene.getStylesheets().add(Main.class.getResource("/ui/bootstrap.css").toExternalForm());

            primaryStage.setOnCloseRequest(windowEvent -> {
                EntityManagerConnector.close();
                Platform.exit();
                System.exit(0);
            });

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Main window", "Database is not running.");
            EntityManagerConnector.close();
        }
    }
}
