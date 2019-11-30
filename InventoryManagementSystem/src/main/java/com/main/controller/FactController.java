package com.main.controller;

import com.main.database.jpa.JpaConnector;
import com.main.model.entity.ConsumptionEntity;
import com.main.model.entity.FactEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class FactController implements Initializable {
    @FXML
    public TextField textFieldConsumptionAmount;
    @FXML
    public DatePicker datePickerConsumption;

    @FXML
    public Button buttonNewConsumption;
    @FXML
    public Button buttonFactRefreshTable;
    @FXML
    public Button buttonNewOrder;
    @FXML
    public Button buttonFactClearTable;

    @FXML
    public TableView<FactEntity> tableFactView;
    @FXML
    public TableColumn<FactEntity, Integer> tableFactColumnId;
    @FXML
    public TableColumn<FactEntity, Integer> tableFactColumnIdProduct;
    @FXML
    public TableColumn<FactEntity, Integer> tableFactColumnAmount;
    @FXML
    public TableColumn<FactEntity, Double> tableFactColumnPrice;
    @FXML
    public TableColumn tableFactColumnTotalPrice;
    @FXML
    public TableColumn<FactEntity, Date> tableFactColumnDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNewConsumption.setOnAction(this::OnPress_Button_NewConsumption);
        buttonFactRefreshTable.setOnAction(this::OnPress_Button_RefreshFactTable);
        buttonNewOrder.setOnAction(this::OnPress_Button_NewOrder);
        buttonFactClearTable.setOnAction(this::OnPress_Button_ClearFactTable);
    }

    public void OnPress_Button_ClearFactTable(ActionEvent event) {
        tableFactView.getItems().clear();
    }

    @FXML
    public void OnPress_Button_NewOrder(ActionEvent event) {
        FactEntity factEntity = getSelectedItem();
        if (factEntity != null) {
            Parent root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/new_order.fxml"));
                fxmlLoader.setController(new NewOrderController(factEntity));
                root = fxmlLoader.load();
                MainController.showModalWindow("New Order", root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MainController.showAlert(Alert.AlertType.ERROR, "New Order", "Please select a product from table below.");
        }
    }

    @FXML
    public void OnPress_Button_RefreshFactTable(ActionEvent event) {
        displayInformationToFactTableView();
    }

    @FXML
    public void OnPress_Button_NewConsumption(ActionEvent event) {
        FactEntity selectedItem = getSelectedItem();

        if (selectedItem == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Consumption", "Please select a product from table below.");
            return;
        }

        ConsumptionEntity consumptionEntity = getConsumptionEntity(selectedItem);
        if (consumptionEntity != null) try {
            JpaConnector.getConsumption().save(consumptionEntity);

            FactEntity factEntity = JpaConnector.getFact().get(selectedItem.getIdFact()).get();
            factEntity.setAmount(factEntity.getAmount() - Integer.parseInt(textFieldConsumptionAmount.getText()));
            JpaConnector.getFact().update(factEntity);

            MainController.showAlert(Alert.AlertType.INFORMATION, "New Consumption", "Consumption was successfully added.");
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Consumption", ex.getMessage());
        }
    }

    private FactEntity getSelectedItem() {
        return tableFactView.getSelectionModel().getSelectedItem();
    }

    private ConsumptionEntity getConsumptionEntity(FactEntity selectedItem) {
        if (isInformationValid(selectedItem)) {
            String productName = JpaConnector.getProduct().get(selectedItem.getIdProduct()).get().getName();
            return new ConsumptionEntity(
                    productName,
                    selectedItem.getPrice(),
                    Integer.parseInt(textFieldConsumptionAmount.getText()),
                    Date.valueOf(datePickerConsumption.getValue()));
        }
        return null;
    }

    private boolean isInformationValid(FactEntity selectedFactEntityItem) {
        boolean hasOnlyNumbers = MainController.hasOnlyNumbers(textFieldConsumptionAmount.getText());
        if (!hasOnlyNumbers) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Amount must has only numbers.");
            return false;
        }
        if (Integer.parseInt(textFieldConsumptionAmount.getText()) > selectedFactEntityItem.getAmount()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Inventory contain only: " + selectedFactEntityItem.getAmount() + " units");
            return false;
        }
        if (datePickerConsumption.getValue() == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Choose data of the order.");
            return false;
        }

        return true;
    }

    private void displayInformationToFactTableView() {
        ObservableList<FactEntity> list = FXCollections.observableArrayList(JpaConnector.getFact().getAll());
        if (tableFactView.getItems().size() > 0)
            tableFactView.getItems().clear();

        if (list != null && list.size() > 0) {
            tableFactColumnId.setCellValueFactory(new PropertyValueFactory<>("idFact"));
            tableFactColumnIdProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
            tableFactColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableFactColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            tableFactColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            tableFactColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableFactView.setItems(list);
        }
    }
}
