package com.main.controller.menu;

import com.main.database.JpaConnector;
import com.main.model.ExcelExport;
import com.main.model.entity.ConsumptionEntity;
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

public class ConsumptionController implements Initializable {
    @FXML
    public Button buttonConsumptionRefreshTable;
    @FXML
    public Button buttonChange;
    @FXML
    public Button buttonDelete;
    @FXML
    public Button buttonExportToExcel;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonConsumptionRefreshTable.setOnAction(this::OnPress_Button_RefreshConsumptionTable);
        buttonExportToExcel.setOnAction(this::OnPress_Button_ExportToExcel);

        // TODO: uncomment when will be added the login system
//        buttonChange.setOnAction(this::OnPress_Button_Change);
//        buttonDelete.setOnAction(this::OnPress_Button_Delete);
    }

    @FXML
    public void OnPress_Button_RefreshConsumptionTable(ActionEvent event) {
        displayInformationToConsumptionTableView();
    }

    @FXML
    public void OnPress_Button_ExportToExcel(ActionEvent event) {
        Stage stage = (Stage) buttonExportToExcel.getScene().getWindow();
        ExcelExport<ConsumptionEntity> excelExport = new ExcelExport<>();
        excelExport.export("Consumption", tableConsumptionView, stage);
    }

    @FXML
    public void OnPress_Button_Change(ActionEvent event) {
        // TODO:: make when will be added the login system
    }

    @FXML
    public void OnPress_Button_Delete(ActionEvent event) {
        // TODO:: make when will be added the login system
    }

    private void displayInformationToConsumptionTableView() {
        clearTableView();
        ObservableList<ConsumptionEntity> data = FXCollections.observableArrayList(JpaConnector.getConsumption().getAll());
        if (data.size() < 1) {
            MainController.showAlert(Alert.AlertType.INFORMATION, "Table View", "Table is empty.");
            return;
        }
        assignInformationToTableView(data);
    }

    private void clearTableView() {
        if (tableConsumptionView.getItems().size() > 0)
            tableConsumptionView.getItems().clear();
    }

    private void assignInformationToTableView(ObservableList<ConsumptionEntity> data) {
        tableConsumptionColumnId.setCellValueFactory(new PropertyValueFactory<>("idConsumption"));
        tableConsumptionColumnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        tableConsumptionColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableConsumptionColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableConsumptionColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tableConsumptionColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableConsumptionView.setItems(data);
    }
}
