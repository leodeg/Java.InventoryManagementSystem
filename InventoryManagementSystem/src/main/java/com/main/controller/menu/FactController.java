package com.main.controller.menu;

import com.main.controller.modalWindow.NewOrderFromInventoryController;
import com.main.database.JpaConnector;
import com.main.model.export.ExcelExport;
import com.main.model.entity.ConsumptionEntity;
import com.main.model.entity.FactEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    public Button buttonExportToExcel;

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
        buttonExportToExcel.setOnAction(this::OnPress_Button_ExportToExcel);
    }

    @FXML
    public void OnPress_Button_ExportToExcel(ActionEvent event) {
        Stage stage = (Stage) buttonExportToExcel.getScene().getWindow();
        ExcelExport<FactEntity> excelExport = new ExcelExport<>();
        excelExport.export("Fact", tableFactView, stage);
    }

    public void OnPress_Button_ClearFactTable(ActionEvent event) {
        tableFactView.getItems().clear();
    }

    @FXML
    public void OnPress_Button_NewOrder(ActionEvent event) {
        FactEntity factEntity = getSelectedItem();
        if (factEntity != null) {
            openNewOrderWindow(factEntity);
        } else {
            MainController.showAlert(Alert.AlertType.ERROR, "New Order", "Please select a product from table below.");
        }
    }

    private void openNewOrderWindow(FactEntity factEntity) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/modalWindow/new_order_from_inventory.fxml"));
            fxmlLoader.setController(new NewOrderFromInventoryController(factEntity));
            root = fxmlLoader.load();
            MainController.showModalWindow("New Order", root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void OnPress_Button_RefreshFactTable(ActionEvent event) {
        displayInformationToFactTableView();
    }

    @FXML
    public void OnPress_Button_NewConsumption(ActionEvent event) {
        if (checkIsSelectionEmpty()) return;

        FactEntity selectedItem = getSelectedItem();
        ConsumptionEntity consumptionEntity = getConsumptionEntity(selectedItem);
        if (consumptionEntity != null) try {
            saveConsumptionToDatabase(selectedItem, consumptionEntity);
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Consumption", ex.getMessage());
        }
    }

    private boolean checkIsSelectionEmpty() {
        if (selectionIsEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Consumption", "Please select a product from table below.");
            return true;
        }
        return false;
    }

    private void saveConsumptionToDatabase(FactEntity selectedItem, ConsumptionEntity consumptionEntity) {
        JpaConnector.getConsumption().save(consumptionEntity);
        FactEntity factEntity = JpaConnector.getFact().get(selectedItem.getIdFact()).get();
        factEntity.setAmount(factEntity.getAmount() - Integer.parseInt(textFieldConsumptionAmount.getText()));
        JpaConnector.getFact().update(factEntity);
        MainController.showAlert(Alert.AlertType.INFORMATION, "New Consumption", "Consumption was successfully added.");
    }

    private boolean selectionIsEmpty() {
        return tableFactView.getSelectionModel().isEmpty();
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
        clearTableView();
        ObservableList<FactEntity> list = FXCollections.observableArrayList(JpaConnector.getFact().getAll());
        if (list.size() < 1) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table View", "Table is empty.");
            return;
        }
        assignInformationToTableView(list);
    }

    private void clearTableView() {
        if (tableFactView.getItems().size() > 0)
            tableFactView.getItems().clear();
    }

    private void assignInformationToTableView(ObservableList<FactEntity> list) {
        tableFactColumnId.setCellValueFactory(new PropertyValueFactory<>("idFact"));
        tableFactColumnIdProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        tableFactColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableFactColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableFactColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tableFactColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableFactView.setItems(list);
    }
}
