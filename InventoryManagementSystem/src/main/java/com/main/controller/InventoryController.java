package com.main.controller;

import com.main.database.jpa.JpaConnector;
import com.main.model.entity.ArrivalEntity;
import com.main.model.entity.ConsumptionEntity;
import com.main.model.entity.FactEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Date;

public class InventoryController {
    @FXML
    public Button buttonFactRefreshTable;
    @FXML
    public Button buttonNewOrder;
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

    @FXML
    public Button buttonArrivalRefreshTable;
    @FXML
    public TableView<ArrivalEntity> tableArrivalView;
    @FXML
    public TableColumn<ArrivalEntity, Integer> tableArrivalColumnId;
    @FXML
    public TableColumn<ArrivalEntity, Integer> tableArrivalColumnIdProduct;
    @FXML
    public TableColumn<ArrivalEntity, Integer> tableArrivalColumnAmount;
    @FXML
    public TableColumn<ArrivalEntity, Double> tableArrivalColumnPrice;
    @FXML
    public TableColumn tableArrivalColumnTotalPrice;
    @FXML
    public TableColumn<ArrivalEntity, Date> tableArrivalColumnDate;

    @FXML
    public Button buttonConsumptionRefreshTable;
    @FXML
    public TableView<ConsumptionEntity> tableConsumptionView;
    @FXML
    public TableColumn<ConsumptionEntity, Integer> tableConsumptionColumnId;
    @FXML
    public TableColumn<ConsumptionEntity, String> tableConsumptionColumnProductName;
    @FXML
    public TableColumn<ConsumptionEntity, Double> tableConsumptionColumnPrice;
    @FXML
    public TableColumn<ConsumptionEntity, Integer> tableConsumptionColumnAmount;
    @FXML
    public TableColumn<ConsumptionEntity, Double> tableConsumptionColumnTotalPrice;
    @FXML
    public TableColumn<ConsumptionEntity, Date> tableConsumptionColumnDate;

    @FXML
    public TextField textFieldConsumptionAmount;
    @FXML
    public DatePicker datePickerConsumption;
    @FXML
    public Button buttonNewConsumption;


    public void OnPress_Button_NewOrder(ActionEvent event) {
        FactEntity factEntity = getSelectedItem();
        if (factEntity != null) {
            Parent root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/new_order.fxml"));
                fxmlLoader.setController(new NewOrderController(factEntity));
                root = fxmlLoader.load();
                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("New Order");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MainController.showAlert(Alert.AlertType.ERROR, "New Order", "Please select a product from table below.");
        }
    }

    private FactEntity getSelectedItem() {
        return tableFactView.getSelectionModel().getSelectedItem();
    }

    public void OnPress_Button_NewConsumption() {
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


    public void OnPress_Button_RefreshFactTable(ActionEvent event) {
        displayInformationToFactTableView();
    }

    public void OnPress_Button_RefreshArrivalTable(ActionEvent event) {
        displayInformationToArrivalTableView();
    }

    public void OnPress_Button_RefreshConsumptionTable(ActionEvent event) {
        displayInformationToConsumptionTableView();
    }

    private void displayInformationToFactTableView() {
        displayInventory(FXCollections.observableArrayList(JpaConnector.getFact().getAll()),
                tableFactView,
                "idFact",
                tableFactColumnId,
                tableFactColumnIdProduct,
                tableFactColumnPrice,
                tableFactColumnAmount,
                tableFactColumnTotalPrice,
                tableFactColumnDate);
    }

    private void displayInformationToArrivalTableView() {
        displayInventory(FXCollections.observableArrayList(JpaConnector.getArrival().getAll()),
                tableArrivalView,
                "idArrival",
                tableArrivalColumnId,
                tableArrivalColumnIdProduct,
                tableArrivalColumnPrice,
                tableArrivalColumnAmount,
                tableArrivalColumnTotalPrice,
                tableArrivalColumnDate);
    }

    private void displayInventory(ObservableList list, TableView tableView, String columnIdName, TableColumn id, TableColumn idProductm, TableColumn price, TableColumn amount, TableColumn totalPrice, TableColumn date) {
        if (tableView.getItems().size() > 0)
            tableView.getItems().clear();

        if (list != null && list.size() > 0) {
            id.setCellValueFactory(new PropertyValueFactory<>(columnIdName));
            idProductm.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableView.setItems(list);
        }
    }

    private void displayInformationToConsumptionTableView() {
        ObservableList<ConsumptionEntity> data = FXCollections.observableArrayList(JpaConnector.getConsumption().getAll());
        if (data != null && data.size() > 0) {
            tableConsumptionColumnId.setCellValueFactory(new PropertyValueFactory<>("idConsumption"));
            tableConsumptionColumnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            tableConsumptionColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableConsumptionColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            tableConsumptionColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            tableConsumptionColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableConsumptionView.setItems(data);
        }
    }

    public void OnPress_Button_ClearFactTable() {
        tableFactView.getItems().clear();
    }

}
