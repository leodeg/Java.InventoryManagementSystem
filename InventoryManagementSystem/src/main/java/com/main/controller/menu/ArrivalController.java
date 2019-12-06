package com.main.controller.menu;

import com.main.controller.modalWindow.NewArrivalController;
import com.main.database.JpaConnector;
import com.main.model.ExcelExport;
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
import javafx.stage.Stage;
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
    public Button buttonExportToExcel;

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
    public TableColumn<ArrivalEntity, Double> tableArrivalColumnTotalPrice;
    @FXML
    public TableColumn<ArrivalEntity, Date> tableArrivalColumnDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonArrivalRefreshTable.setOnAction(this::OnPress_Button_RefreshArrivalTable);
        buttonArrivalNew.setOnAction(this::OnPress_Button_NewArrival);
        buttonArrivalChange.setOnAction(this::OnPress_Button_ChangeInformation);
        buttonExportToExcel.setOnAction(this::OnPress_Button_ExportToExcel);
    }

    @FXML
    public void OnPress_Button_ExportToExcel(ActionEvent event) {
        Stage stage = (Stage) buttonExportToExcel.getScene().getWindow();
        ExcelExport<ArrivalEntity> excelExport = new ExcelExport<>();
        excelExport.export("Arrival", tableArrivalView, stage);
    }

    @FXML
    public void OnPress_Button_NewArrival(ActionEvent event) {
        Parent root;
        try {
            openNewWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openNewWindow() throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/modalWindow/new_arrival.fxml"));
        fxmlLoader.setController(new NewArrivalController());
        root = fxmlLoader.load();
        MainController.showModalWindow("New Arrival", root);
    }

    @FXML
    public void OnPress_Button_ChangeInformation(ActionEvent event) {
        if (informationIsValid()) try {
            saveChangedInformationToDatabase();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Information Error", ex.getMessage());
        }
    }

    private void saveChangedInformationToDatabase() {
        ArrivalEntity arrivalEntity = getChangedArrivalEntity();
        JpaConnector.getArrival().update(arrivalEntity);
        updateFactDatabaseTable(arrivalEntity);
        MainController.showAlert(Alert.AlertType.INFORMATION, "Change Information", "Product information was changed.");
        displayInformationToArrivalTableView();
    }

    private boolean informationIsValid() {
        if (tableArrivalView.getSelectionModel().isEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Information Error", "Please select product from the table below.");
            return false;
        }
        if (textFieldArrivalAmount.getText().length() < 1) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Information Error", "Amount is empty.");
            return false;
        }
        return true;
    }

    private ArrivalEntity getSelectedItem() {
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

    private void updateFactDatabaseTable(ArrivalEntity newEntity) {
        ArrivalEntity oldEntity = getSelectedItem();
        FactEntity factEntity = JpaConnector.getFact().getFirstByIdProduct(newEntity.getIdProduct());

        setFactEntityAmount(factEntity, newEntity.getAmount(), oldEntity.getAmount());
        factEntity.setPrice(newEntity.getPrice());
        factEntity.setTotalPrice(factEntity.getAmount() * factEntity.getPrice());
        factEntity.setDate(newEntity.getDate());
        JpaConnector.getFact().update(factEntity);
    }

    private void setFactEntityAmount(FactEntity factEntity, int newEntityAmount, int oldEntityAmount) {
        int difference;
        boolean amountBecomeLower = newEntityAmount < oldEntityAmount;
        if (amountBecomeLower) {
            difference = oldEntityAmount - newEntityAmount;
            factEntity.setAmount(factEntity.getAmount() - difference);
        } else {
            difference = newEntityAmount - oldEntityAmount;
            factEntity.setAmount(newEntityAmount + difference);
        }
    }

    @FXML
    public void OnPress_Button_RefreshArrivalTable(ActionEvent event) {
        displayInformationToArrivalTableView();
    }

    private void displayInformationToArrivalTableView() {
        clearTableView();
        ObservableList<ArrivalEntity> list = FXCollections.observableArrayList(JpaConnector.getArrival().getAll());
        if (list.size() < 1) {
            MainController.showAlert(Alert.AlertType.INFORMATION, "Table View", "Table is empty.");
            return;
        }
        assignInformationToTableView(list);
    }

    private void clearTableView() {
        if (tableArrivalView.getItems().size() > 0) {
            tableArrivalView.getItems().clear();
        }
    }

    private void assignInformationToTableView(ObservableList<ArrivalEntity> list) {
        tableArrivalColumnId.setCellValueFactory(new PropertyValueFactory<>("idArrival"));
        tableArrivalColumnIdProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        tableArrivalColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableArrivalColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableArrivalColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tableArrivalColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableArrivalView.setItems(list);
    }
}
