package com.main.controller.menu;

import com.main.database.JpaConnector;
import com.main.model.entity.CategoryEntity;
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
import java.util.ResourceBundle;

public class ProductsController implements Initializable {
    @FXML
    public ChoiceBox<String> choiceBoxCategory;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldPrice;
    @FXML
    public TextField textFieldDescription;

    @FXML
    public Button buttonRefreshChoice;
    @FXML
    public Button buttonNewProduct;
    @FXML
    public Button buttonChangeProduct;
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
        buttonChangeProduct.setOnAction(this::OnPress_Button_ChangeInformation);
        buttonNewProduct.setOnAction(this::OnPress_Button_NewProduct);
        buttonRefreshChoice.setOnAction(this::OnPress_Button_RefreshChoice);
        buttonRefreshTable.setOnAction(this::OnPress_Button_RefreshTable);
    }

    private boolean isSelectedItemsEmpty() {
        return tableView.getSelectionModel().isEmpty();
    }

    @FXML
    public void OnPress_Button_NewProduct(ActionEvent event) {
        ProductEntity productEntity = getProductEntity();
        if (productEntity != null) {
            try {
                JpaConnector.getProduct().save(productEntity);
                MainController.showAlert(Alert.AlertType.CONFIRMATION, "Product added", "Product was successfully added to database");
                displayInformationToTableView();
            } catch (Exception ex) {
                MainController.showAlert(Alert.AlertType.ERROR, "Product Error", ex.getMessage());
            }
        }
    }

    @Nullable
    private ProductEntity getProductEntity() {
        try {
            if (checkInformationValidation())
                return new ProductEntity(JpaConnector.getCategory().getIdByTitle(choiceBoxCategory.getValue()), textFieldName.getText(), Double.parseDouble(textFieldPrice.getText()), textFieldDescription.getText());
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Product Error", ex.getMessage());
        }
        return null;
    }

    private boolean checkInformationValidation() {
        if (textFieldName.getText().length() < 1 || textFieldPrice.getText().length() < 1) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", "Name or price is empty. Please enter an information.");
            return false;
        }
        if (!MainController.hasOnlyNumbers(textFieldPrice.getText())) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", "Price contains letters.");
            return false;
        }
        return true;
    }

    @FXML
    public void OnPress_Button_ChangeInformation(ActionEvent event) {
        if (isSelectedItemsEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Information Error", "Please select product from the table below.");
            return;
        }

        try {
            JpaConnector.getProduct().update(getChangedProductEntity());
            MainController.showAlert(Alert.AlertType.INFORMATION, "Change Information", "Product information was changed.");
            displayInformationToTableView();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Information Error", ex.getMessage());
        }
    }

    @NotNull
    private ProductEntity getChangedProductEntity() {
        ProductEntity selectedItem = tableView.getSelectionModel().getSelectedItem();
        selectedItem.setIdCategory(JpaConnector.getCategory().getIdByTitle(choiceBoxCategory.getValue()));
        selectedItem.setName(textFieldName.getText());
        selectedItem.setPrice(Double.parseDouble(textFieldPrice.getText()));
        selectedItem.setDescription(textFieldDescription.getText());
        return selectedItem;
    }

    @FXML
    public void OnPress_Button_RefreshTable(ActionEvent event) {
        displayInformationToTableView();
    }

    private void displayInformationToTableView() {
        if (tableView.getItems().size() > 0)
            tableView.getItems().clear();

        ObservableList<ProductEntity> data = FXCollections.observableArrayList(JpaConnector.getProduct().getAll());

        if (data.size() < 1) {
            MainController.showAlert(Alert.AlertType.INFORMATION, "Table View", "Table is empty.");
            return;
        }

        tableColumnIdProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        tableColumnIdCategory.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(data);
    }

    @FXML
    public void OnPress_Button_RefreshChoice(ActionEvent event) {
        populateChoiceBox();
    }

    private void populateChoiceBox() {
        ObservableList<String> data = FXCollections.observableArrayList();
        for (CategoryEntity entity : JpaConnector.getCategory().getAll())
            data.add(entity.getTitle());
        choiceBoxCategory.setItems(data);
    }
}
