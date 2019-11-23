package com.main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    @FXML
    private Button button;

    public void pressButton(ActionEvent event) {
        System.out.print("Hello");
    }
}
