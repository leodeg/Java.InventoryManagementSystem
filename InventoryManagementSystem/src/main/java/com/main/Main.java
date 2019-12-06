package com.main;

import com.main.controller.menu.MainController;
import com.main.database.EntityManagerConnector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        connectToDatabase();
        openMainWindow(primaryStage);
    }

    private void connectToDatabase() {
        try {
            EntityManagerConnector.initialize();
        } catch (NullPointerException ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Database connection", ex.getMessage());
        }
    }

    private void openMainWindow(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/menu/main.fxml"));
            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(Main.class.getResource("/ui/themes/bootstrap.css").toExternalForm());

            primaryStage.setOnCloseRequest(this::handleOnCloseApplicationRequest);
            primaryStage.setTitle("Inventory management system");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Main window", "Database is not running.");
            EntityManagerConnector.close();
        }
    }

    private void handleOnCloseApplicationRequest(WindowEvent event) {
        EntityManagerConnector.close();
        Platform.exit();
        System.exit(0);
    }
}
