package com.main.controller.modalWindow;

import com.main.controller.menu.MainController;
import com.main.database.JpaConnector;
import com.main.model.entity.ArrivalEntity;
import com.main.model.entity.FactEntity;
import com.main.model.entity.ProductEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class NewArrivalController implements Initializable {
    @FXML
    public TextField textFieldArrivalAmount;
    @FXML
    public DatePicker datePickerArrival;

    @FXML
    public Button buttonArrivalNewArrival;
    @FXML
    public Button buttonRefreshTable;

    @FXML
    public TableView<ProductEntity> tableView;
    @FXML
    public TableColumn<ProductEntity, Integer> tableColumnIdProduct;
    @FXML
    public TableColumn<ProductEntity, Integer> tableColumnIdCategory;
    @FXML
    public TableColumn<ProductEntity, String> tableColumnName;
    @FXML
    public TableColumn<ProductEntity, Double> tableColumnPrice;
    @FXML
    public TableColumn<ProductEntity, String> tableColumnDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonRefreshTable.setOnAction(this::OnPress_Button_RefreshTable);
        buttonArrivalNewArrival.setOnAction(this::OnPress_Button_NewArrival);
        displayInformationToTableView();
    }

    @FXML
    private void OnPress_Button_NewArrival(ActionEvent event) {
        saveNewArrivalToDatabase();
    }

    private void saveNewArrivalToDatabase() {
        ArrivalEntity entity = getArrivalEntity();
        if (entity != null) {
            JpaConnector.getArrival().save(entity);
            updateFactDatabaseTable(entity);
        }
    }

    @Nullable
    private ArrivalEntity getArrivalEntity() {
        if (checkArrivalInformation()) {
            ProductEntity entity = tableView.getSelectionModel().getSelectedItem();
            MainController.showAlert(Alert.AlertType.CONFIRMATION, "ProductEntity", entity.toString());
            return new ArrivalEntity(entity.getIdProduct(),
                    Integer.parseInt(textFieldArrivalAmount.getText()),
                    entity.getPrice(),
                    Date.valueOf(datePickerArrival.getValue()));
        }
        return null;
    }

    public void updateFactDatabaseTable(@NotNull ArrivalEntity entity) {
        if (!JpaConnector.getFact().isExists(entity.getIdProduct()))
            createNewFactEntity(entity);
        else updateOldFactEntity(entity);
    }

    private void createNewFactEntity(@NotNull ArrivalEntity entity) {
        JpaConnector.getFact().save(new FactEntity(entity.getIdProduct(), entity.getAmount(), entity.getPrice(), entity.getDate()));
    }

    private void updateOldFactEntity(@NotNull ArrivalEntity entity) {
        FactEntity factEntity = JpaConnector.getFact().get(entity.getIdProduct()).get();
        factEntity.setAmount(factEntity.getAmount() + entity.getAmount());
        factEntity.setPrice(entity.getPrice());
        factEntity.setDate(entity.getDate());
        JpaConnector.getFact().update(factEntity);
    }

    private boolean checkArrivalInformation() {
        if (isSelectedItemsEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Arrival Error", "Please select a product from the table below.");
            return false;
        }
        if (textFieldArrivalAmount.getText().length() < 1) {
            MainController.showAlert(Alert.AlertType.ERROR, "Arrival Error", "Please enter amount.");
            return false;
        }
        if (!MainController.hasOnlyNumbers(textFieldArrivalAmount.getText())) {
            MainController.showAlert(Alert.AlertType.ERROR, "Arrival Error", "Amount must contain only numbers.");
            return false;
        }
        if (datePickerArrival.getValue() == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Arrival Error", "Choose date from date picker.");
            return false;
        }
        return true;
    }

    private boolean isSelectedItemsEmpty() {
        return tableView.getSelectionModel().isEmpty();
    }

    @FXML
    private void OnPress_Button_RefreshTable(ActionEvent event) {
        displayInformationToTableView();
    }

    private void displayInformationToTableView() {
        clearTableView();
        ObservableList<ProductEntity> data = FXCollections.observableArrayList(JpaConnector.getProduct().getAll());
        if (data.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table View", "Table is empty.");
            return;
        }
        assignInformationToTableView(data);
    }

    private void clearTableView() {
        if (tableView.getItems().size() > 0)
            tableView.getItems().clear();
    }

    private void assignInformationToTableView(ObservableList<ProductEntity> data) {
        tableColumnIdProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        tableColumnIdCategory.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(data);
    }
}
