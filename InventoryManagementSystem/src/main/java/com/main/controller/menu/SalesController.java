package com.main.controller.menu;

import com.main.database.JpaConnector;
import com.main.model.ExcelExport;
import com.main.model.entity.SaleEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class SalesController implements Initializable {
    @FXML
    public Button buttonRefreshTable;
    @FXML
    public Button buttonExportToExcel;

    @FXML
    public TableView<SaleEntity> tableView;
    @FXML
    public TableColumn<SaleEntity, Integer> tableColumnId;
    @FXML
    public TableColumn<SaleEntity, String> tableColumnProductName;
    @FXML
    public TableColumn<SaleEntity, String> tableColumnCustomerName;
    @FXML
    public TableColumn<SaleEntity, Double> tableColumnPrice;
    @FXML
    public TableColumn<SaleEntity, Integer> tableColumnAmount;
    @FXML
    public TableColumn<SaleEntity, Double> tableColumnTotalPrice;
    @FXML
    public TableColumn<SaleEntity, Date> tableColumnDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonRefreshTable.setOnAction(this::OnPress_Button_RefreshTable);
        buttonExportToExcel.setOnAction(this::OnPress_Button_ExportToExcel);
    }

    @FXML
    public void OnPress_Button_ExportToExcel(ActionEvent event) {
        Stage stage = (Stage) buttonExportToExcel.getScene().getWindow();
        ExcelExport<SaleEntity> excelExport = new ExcelExport<>();
        excelExport.export("Sales", tableView, stage);
    }

    @FXML
    public void OnPress_Button_RefreshTable(ActionEvent event) {
        displayInformationToTableView();
    }

    private void displayInformationToTableView() {
        clearTableView();
        ObservableList<SaleEntity> data = FXCollections.observableArrayList(JpaConnector.getSale().getAll());
        if (data.size() < 1) {
            MainController.showAlert(Alert.AlertType.INFORMATION, "Table View", "Table is empty.");
            return;
        }
        assignInformationToTableView(data);
    }

    private void clearTableView() {
        if (tableView.getItems().size() > 0)
            tableView.getItems().clear();
    }

    private void assignInformationToTableView(ObservableList<SaleEntity> data) {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idSale"));
        tableColumnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        tableColumnCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.setItems(data);
    }
}
