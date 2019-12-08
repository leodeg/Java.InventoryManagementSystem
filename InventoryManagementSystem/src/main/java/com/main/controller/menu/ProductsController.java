package com.main.controller.menu;

import com.main.database.JpaConnector;
import com.main.model.export.ExcelExport;
import com.main.model.entity.CategoryEntity;
import com.main.model.entity.ProductEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    public Button buttonDeleteProduct;
    @FXML
    public Button buttonRefreshTable;
    @FXML
    public Button buttonExportToExcel;

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

    @FXML
    public AnchorPane anchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonChangeProduct.setOnAction(this::OnPress_Button_ChangeInformation);
        buttonNewProduct.setOnAction(this::OnPress_Button_NewProduct);
        buttonRefreshChoice.setOnAction(this::OnPress_Button_RefreshChoice);
        buttonRefreshTable.setOnAction(this::OnPress_Button_RefreshTable);
        buttonExportToExcel.setOnAction(this::OnPress_Button_ExportToExcel);
        buttonDeleteProduct.setOnAction(this::OnPress_Button_DeleteProduct);
        displaySelectedInfoToTextFieldsForChanges();
        showErrorWhenCategoryChoiceBoxIsEmpty();
    }

    private void displaySelectedInfoToTextFieldsForChanges() {
        tableView.getSelectionModel().selectedItemProperty().addListener(newSelection -> {
            if (newSelection != null) {
                displaySelectedInfo(tableView.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void showErrorWhenCategoryChoiceBoxIsEmpty() {
        choiceBoxCategory.setOnMouseClicked(event -> {
            if (choiceBoxCategory.getItems().size() < 1) {
                MainController.showAlert(Alert.AlertType.WARNING, "Choice Box", "Please refresh choice box.");
            }
        });
    }

    @FXML
    private void OnPress_TableView_ShowAdditionalInfo(MouseEvent event) {
        tableView.getSelectionModel().selectedItemProperty().addListener(newSelection -> {
            if (newSelection != null) {
                // TODO: show additional information for identifiers
            }
        });
    }

    @FXML
    public void OnPress_Button_RefreshTable(ActionEvent event) {
        displayInformationToTableView();
    }

    @FXML
    public void OnPress_Button_RefreshChoice(ActionEvent event) {
        populateChoiceBox();
    }

    private void populateChoiceBox() {
        ObservableList<String> data = FXCollections.observableArrayList();
        if (data.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Choice box", "Categories is empty.");
            return;
        }
        for (CategoryEntity entity : JpaConnector.getCategory().getAll())
            data.add(entity.getTitle());
        choiceBoxCategory.setItems(data);
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

    @FXML
    public void OnPress_Button_ChangeInformation(ActionEvent event) {
        if (selectionItemsIsEmpty()) return;

        try {
            JpaConnector.getProduct().update(getChangedProductEntity());
            MainController.showAlert(Alert.AlertType.INFORMATION, "Change Information", "Product information was changed.");
            displayInformationToTableView();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Information Error", ex.getMessage());
        }
    }

    @FXML
    public void OnPress_Button_DeleteProduct(ActionEvent event) {
        if (!selectionItemsIsEmpty()) {
            JpaConnector.getProduct().delete(getSelectedProduct());
            MainController.showAlert(Alert.AlertType.INFORMATION, "Delete Product", "Information was deleted from the database.");
            displayInformationToTableView();
        }
    }

    @FXML
    public void OnPress_Button_ExportToExcel(ActionEvent event) {
        Stage stage = (Stage) buttonExportToExcel.getScene().getWindow();
        ExcelExport<ProductEntity> excelExport = new ExcelExport<>();
        excelExport.export("Products", tableView, stage);
    }

    private void displaySelectedInfo(ProductEntity entity) {
        textFieldName.setText(entity.getName());
        textFieldPrice.setText(String.valueOf(entity.getPrice()));
        textFieldDescription.setText(entity.getDescription());
        choiceBoxCategory.setValue(JpaConnector.getCategory().get(entity.getIdCategory()).get().getTitle());
    }

    private boolean selectionItemsIsEmpty() {
        if (isSelectedItemsEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Change Information Error", "Please select product from the table below.");
            return true;
        }
        return false;
    }

    private boolean isSelectedItemsEmpty() {
        return tableView.getSelectionModel().isEmpty();
    }

    private ProductEntity getSelectedProduct() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @Nullable
    private ProductEntity getProductEntity() {
        try {
            if (isInformationValid())
                return new ProductEntity(
                        JpaConnector.getCategory().getIdByTitle(choiceBoxCategory.getValue()),
                        textFieldName.getText(),
                        Double.parseDouble(textFieldPrice.getText()),
                        textFieldDescription.getText());
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Product Error", ex.getMessage());
        }
        return null;
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

    private boolean isInformationValid() {
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
