package com.main.controller;

import com.main.database.jpa.JpaConnector;
import com.main.model.entity.ArrivalEntity;
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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ArrivalController implements Initializable {
    @FXML
    public TextField textFieldArrivalAmount;
    @FXML
    public DatePicker datePickerArrival;

    @FXML
    public Button buttonArrivalNew;
    @FXML
    public Button buttonArrivalChange;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonArrivalRefreshTable.setOnAction(this::OnPress_Button_RefreshArrivalTable);
        buttonArrivalNew.setOnAction(this::OnPress_Button_NewArrival);
        buttonArrivalChange.setOnAction(this::OnPress_Button_ChangeInformation);
    }

    @FXML
    public void OnPress_Button_NewArrival(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/new_arrival.fxml"));
            fxmlLoader.setController(new NewArrivalController());
            root = fxmlLoader.load();
            MainController.showModalWindow("New Arrival", root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void OnPress_Button_ChangeInformation(ActionEvent event) {
        if (isSelectedItemsEmpty()) return;

        try {
            ArrivalEntity arrivalEntity = getChangedArrivalEntity();
            JpaConnector.getArrival().update(arrivalEntity);
            updateFactDatabaseTable(arrivalEntity);

            MainController.showAlert(Alert.AlertType.INFORMATION, "Change Information", "Product information was changed.");
            displayInformationToArrivalTableView();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Information Error", ex.getMessage());
        }
    }

    private boolean isSelectedItemsEmpty() {
        if (tableArrivalView.getSelectionModel().isEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Information Error", "Please select product from the table below.");
            return true;
        }
        return false;
    }

    private ArrivalEntity getSelectedItem () {
        return tableArrivalView.getSelectionModel().getSelectedItem();
    }

    @NotNull
    private ArrivalEntity getChangedArrivalEntity() {
        ArrivalEntity selectedItem = getSelectedItem();

        Integer amount = Integer.parseInt(textFieldArrivalAmount.getText());
        Double productPrice = JpaConnector.getProduct().get(selectedItem.getIdProduct()).get().getPrice();

        selectedItem.setAmount(amount);
        selectedItem.setPrice(productPrice);
        selectedItem.setTotalPrice(amount * productPrice);
        return selectedItem;
    }

    public void updateFactDatabaseTable(ArrivalEntity newEntity) {
        FactEntity factEntity = JpaConnector.getFact().getFirstByIdProduct(newEntity.getIdProduct());
        factEntity.setAmount(newEntity.getAmount());
        factEntity.setPrice(newEntity.getPrice());
        factEntity.setTotalPrice(factEntity.getPrice() * factEntity.getAmount());
        factEntity.setDate(newEntity.getDate());
        JpaConnector.getFact().update(factEntity);
    }

    @FXML
    public void OnPress_Button_RefreshArrivalTable(ActionEvent event) {
        displayInformationToArrivalTableView();
    }

    private void displayInformationToArrivalTableView() {
        ObservableList<ArrivalEntity> list = FXCollections.observableArrayList(JpaConnector.getArrival().getAll());
        if (tableArrivalView.getItems().size() > 0)
            tableArrivalView.getItems().clear();

        if (list != null && list.size() > 0) {
            tableArrivalColumnId.setCellValueFactory(new PropertyValueFactory<>("idArrival"));
            tableArrivalColumnIdProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
            tableArrivalColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableArrivalColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            tableArrivalColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            tableArrivalColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableArrivalView.setItems(list);
        }
    }
}
