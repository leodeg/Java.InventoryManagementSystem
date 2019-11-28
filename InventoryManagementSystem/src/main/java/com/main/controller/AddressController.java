package com.main.controller;

import com.main.model.entity.AddressEntity;
import com.main.model.jpa.JpaAddressDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private JpaAddressDao addressDao;

    public AddressController() {
        addressDao = new JpaAddressDao();
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNew.setOnAction(this::OnPress_Button_New);
        buttonRefresh.setOnAction(this::OnPress_Button_Refresh);
        displayInformationToTableView();
    }

    @FXML
    private void OnPress_Button_New(ActionEvent event) {
        AddressEntity addressEntity = getAddressEntity();
        if (addressEntity != null) try {
            addressDao.save(addressEntity);
            MainController.showAlert(Alert.AlertType.INFORMATION, "New Address", "Address successfully added.");
            displayInformationToTableView();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Address", ex.getMessage());
        }
    }

    @Nullable
    private AddressEntity getAddressEntity() {
        if (isInformationValid()) {
            return new AddressEntity(
                    textFieldAddress.getText(),
                    textFieldAddress2.getText() != null ? textFieldAddress2.getText() : "Empty",
                    textFieldCity.getText(),
                    textFieldRegion.getText() != null ? textFieldRegion.getText() : "Empty");
        }
        return null;
    }

    private boolean isInformationValid() {
        if (textFieldAddress.getText().length() < 1) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Address", "Please enter an address.");
            return false;
        }
        if (textFieldCity.getText().length() < 1) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Address", "Please enter a city.");
            return false;
        }
        return true;
    }

    @FXML
    private void OnPress_Button_Refresh(ActionEvent event) {
        displayInformationToTableView();
    }

    private void displayInformationToTableView() {
        ObservableList<AddressEntity> data = FXCollections.observableArrayList(addressDao.getAll());
        if (data.size() > 0) {
            tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
            tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            tableColumnAddress2.setCellValueFactory(new PropertyValueFactory<>("address2"));
            tableColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            tableColumnRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
            tableView.setItems(data);
        }
    }
}
