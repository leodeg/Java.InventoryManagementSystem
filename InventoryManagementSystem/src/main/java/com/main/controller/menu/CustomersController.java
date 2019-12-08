package com.main.controller.menu;

import com.main.controller.modalWindow.NewCustomerController;
import com.main.database.JpaConnector;
import com.main.model.export.ExcelExport;
import com.main.model.entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    @FXML
    public Button buttonNewCustomer;
    @FXML
    public Button buttonChangeCustomer;
    @FXML
    public Button buttonNewAddress;
    @FXML
    public Button buttonDeleteCustomer;
    @FXML
    public Button buttonRefresh;
    @FXML
    public Button buttonExportToExcel;

    @FXML
    public TableView<CustomerEntity> tableView;
    @FXML
    public TableColumn<CustomerEntity, Integer> tableColumnId;
    @FXML
    public TableColumn<CustomerEntity, Integer> tableColumnAddress;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnName;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnPhone;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnEmail;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnDescription;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNewCustomer.setOnAction(this::OnPress_Button_NewCustomer);
        buttonChangeCustomer.setOnAction(this::OnPress_Button_ChangeCustomer);
        buttonNewAddress.setOnAction(this::OnPress_Button_NewAddress);
        buttonRefresh.setOnAction(this::OnPress_Button_Refresh);
        buttonExportToExcel.setOnAction(this::OnPress_Button_ExportToExcel);
        buttonDeleteCustomer.setOnAction(this::OnPress_Button_DeleteCustomer);
    }

    @FXML
    public void OnPress_Button_DeleteCustomer(ActionEvent event) {
        if (informationIsValid()) {
            deleteCustomerFromDatabase();
        }
    }

    private void deleteCustomerFromDatabase() {
        JpaConnector.getCustomer().delete(getSelectedCustomer());
        MainController.showAlert(Alert.AlertType.INFORMATION, "Delete Address", "Information was deleted from the database.");
        displayInformationToTableView();
    }

    public void OnPress_Button_NewCustomer(ActionEvent event) {
        openNewOrderModalWindow("New Customer", new NewCustomerController());
    }

    public void OnPress_Button_ChangeCustomer(ActionEvent event) {
        if (informationIsValid()) {
            openNewOrderModalWindow("Change Customer", new NewCustomerController(getSelectedCustomer()));
        }
    }

    @FXML
    public void OnPress_Button_ExportToExcel(ActionEvent event) {
        exportTableViewToExcel();
    }

    private void exportTableViewToExcel() {
        Stage stage = (Stage) buttonExportToExcel.getScene().getWindow();
        ExcelExport<CustomerEntity> excelExport = new ExcelExport<>();
        excelExport.export("Customer", tableView, stage);
    }

    private void openNewOrderModalWindow(String title, NewCustomerController controller) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/modalWindow/new_customer.fxml"));
            fxmlLoader.setController(controller);
            root = fxmlLoader.load();
            MainController.showModalWindow(title, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean informationIsValid() {
        if (selectedItemsIsEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Customer", "Please select customer from the table below.");
            return false;
        }
        return true;
    }

    private CustomerEntity getSelectedCustomer() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private boolean selectedItemsIsEmpty() {
        return tableView.getSelectionModel().isEmpty();
    }

    @FXML
    public void OnPress_Button_Refresh(ActionEvent event) {
        displayInformationToTableView();
    }

    @FXML
    public void OnPress_Button_NewAddress(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/menu/address.fxml"));
            fxmlLoader.setController(new AddressController());
            root = fxmlLoader.load();
            MainController.showModalWindow("Addresses", root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayInformationToTableView() {
        clearTableView();
        ObservableList<CustomerEntity> data = FXCollections.observableArrayList(JpaConnector.getCustomer().getAll());
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

    private void assignInformationToTableView(ObservableList<CustomerEntity> data) {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(data);
    }
}
