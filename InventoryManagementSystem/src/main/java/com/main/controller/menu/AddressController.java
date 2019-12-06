package com.main.controller.menu;

import com.main.database.JpaConnector;
import com.main.model.ExcelExport;
import com.main.model.entity.AddressEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ResourceBundle;

public class AddressController implements Initializable {
    @FXML
    public TextField textFieldAddress;
    @FXML
    public TextField textFieldAddress2;
    @FXML
    public TextField textFieldCity;
    @FXML
    public TextField textFieldRegion;

    @FXML
    public Button buttonNew;
    @FXML
    public Button buttonRefresh;
    @FXML
    public Button buttonChange;
    @FXML
    public Button buttonExportToExcel;

    @FXML
    public TableView<AddressEntity> tableView;
    @FXML
    public TableColumn<AddressEntity, Integer> tableColumnId;
    @FXML
    public TableColumn<AddressEntity, String> tableColumnAddress;
    @FXML
    public TableColumn<AddressEntity, String> tableColumnAddress2;
    @FXML
    public TableColumn<AddressEntity, String> tableColumnCity;
    @FXML
    public TableColumn<AddressEntity, String> tableColumnRegion;
    @FXML
    public AnchorPane anchorPane;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNew.setOnAction(this::OnPress_Button_New);
        buttonRefresh.setOnAction(this::OnPress_Button_Refresh);
        buttonChange.setOnAction(this::OnPress_Button_Change);
        buttonExportToExcel.setOnAction(this::OnPress_Button_ExportToExcel);

        displayInformationToTableView();

        tableView.getSelectionModel().selectedItemProperty().addListener(newSelection -> {
            if (newSelection != null) {
                displaySelectedInfo(tableView.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void displaySelectedInfo(AddressEntity entity) {
        textFieldAddress.setText(entity.getAddress());
        textFieldAddress2.setText(entity.getAddress2());
        textFieldCity.setText(entity.getCity());
        textFieldRegion.setText(entity.getRegion());
    }

    @FXML
    public void OnPress_Button_ExportToExcel(ActionEvent event) {
        Stage stage = (Stage) buttonExportToExcel.getScene().getWindow();
        ExcelExport<AddressEntity> excelExport = new ExcelExport<>();
        excelExport.export("Address", tableView, stage);
    }

    @FXML
    private void OnPress_Button_New(ActionEvent event) {
        if (informationIsValid()) {
            AddressEntity addressEntity = getAddressEntity();
            if (addressEntity != null) try {
                JpaConnector.getAddress().save(addressEntity);
                MainController.showAlert(Alert.AlertType.INFORMATION, "New Address", "Address successfully added.");
                displayInformationToTableView();
            } catch (Exception ex) {
                MainController.showAlert(Alert.AlertType.ERROR, "New Address", ex.getMessage());
            }
        }
    }

    @FXML
    private void OnPress_Button_Change(ActionEvent event) {
        if (selectedItemsIsEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Address", "Please select address from the table below.");
            return;
        }

        if (informationIsValid()) {
            AddressEntity addressEntity = getAddressEntity();
            AddressEntity entityToChange = getSelectedItem();

            try {
                assignNewInformation(addressEntity, entityToChange);
                saveNewInformationToDatabase(entityToChange);
            } catch (Exception ex) {
                MainController.showAlert(Alert.AlertType.ERROR, "Change Address", ex.getMessage());
            }
        }
    }

    private void saveNewInformationToDatabase(AddressEntity entityToChange) {
        JpaConnector.getAddress().update(entityToChange);
        MainController.showAlert(Alert.AlertType.INFORMATION, "Change Address", "Address successfully changed.");
        displayInformationToTableView();
    }

    private void assignNewInformation(AddressEntity newAddressInfo, AddressEntity entityToChange) {
        entityToChange.setAddress(newAddressInfo.getAddress());
        entityToChange.setAddress2(newAddressInfo.getAddress2());
        entityToChange.setCity(newAddressInfo.getCity());
        entityToChange.setRegion(newAddressInfo.getRegion());
    }

    private AddressEntity getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private boolean selectedItemsIsEmpty() {
        return tableView.getSelectionModel().isEmpty();
    }

    @Nullable
    private AddressEntity getAddressEntity() {
        return new AddressEntity(
                textFieldAddress.getText(),
                textFieldAddress2.getText() != null ? textFieldAddress2.getText() : "Empty",
                textFieldCity.getText(),
                textFieldRegion.getText() != null ? textFieldRegion.getText() : "Empty");
    }

    private boolean informationIsValid() {
        if (textFieldAddress.getText().length() < 1) {
            MainController.showAlert(Alert.AlertType.ERROR, "Address", "Please enter an address.");
            return false;
        }
        if (textFieldCity.getText().length() < 1) {
            MainController.showAlert(Alert.AlertType.ERROR, "Address", "Please enter a city.");
            return false;
        }
        return true;
    }

    @FXML
    private void OnPress_Button_Refresh(ActionEvent event) {
        displayInformationToTableView();
    }

    private void displayInformationToTableView() {
        clearTableView();
        ObservableList<AddressEntity> data = FXCollections.observableArrayList(JpaConnector.getAddress().getAll());
        if (data.size() < 1) {
            MainController.showAlert(Alert.AlertType.INFORMATION, "Table View", "Table is empty.");
            return;
        }
        assignDataToTableView(data);
    }

    private void clearTableView() {
        if (tableView.getItems().size() > 0)
            tableView.getItems().clear();
    }

    private void assignDataToTableView(ObservableList<AddressEntity> data) {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
        tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableColumnAddress2.setCellValueFactory(new PropertyValueFactory<>("address2"));
        tableColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tableColumnRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        tableView.setItems(data);
    }
}
