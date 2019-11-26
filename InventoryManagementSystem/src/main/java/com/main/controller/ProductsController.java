package com.main.controller;

import com.main.model.entity.CategoryEntity;
import com.main.model.entity.ProductEntity;
import com.main.model.jpa.JpaCategoryDao;
import com.main.model.jpa.JpaProductDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.Nullable;

public class ProductsController {
    @FXML
    public ChoiceBox<String> choiceBoxCategory;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldPrice;
    @FXML
    public TextField textFieldDescription;

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
    public TableColumn tableColumnActions;

    private JpaProductDao productDao;
    private JpaCategoryDao categoryDao;

    public ProductsController() {
        productDao = new JpaProductDao();
        categoryDao = new JpaCategoryDao();
    }

    //    -------------------------------
    //    PRESS NEW PRODUCT
    //    -------------------------------
    public void OnPress_Button_NewProduct(ActionEvent event) {
        ProductEntity productEntity = getProductEntity();
        if (productEntity != null) {
            try {
                productDao.save(productEntity);
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
                return new ProductEntity(categoryDao.getIdByTitle(choiceBoxCategory.getValue()), textFieldName.getText(), Double.parseDouble(textFieldPrice.getText()), textFieldDescription.getText());
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
        if (!priceHasOnlyNumbers()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", "Price contains letters.");
            return false;
        }
        return true;
    }

    private boolean priceHasOnlyNumbers() {
        try {
            Double.parseDouble(textFieldPrice.getText());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    //    -------------------------------
    //    PRESS REFRESH TABLE
    //    -------------------------------
    public void OnPress_Button_Refresh(ActionEvent event) {
        displayInformationToTableView();
    }

    private void displayInformationToTableView() {
        ObservableList<ProductEntity> data = FXCollections.observableArrayList(productDao.getAll());
        tableColumnIdProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        tableColumnIdCategory.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(data);
    }

    //    -------------------------------
    //    PRESS REFRESH CHOICES
    //    -------------------------------
    public void OnPress_Button_RefreshChoice(ActionEvent event) {
        populateChoiceBox();
    }

    private void populateChoiceBox() {
        ObservableList<String> data = FXCollections.observableArrayList();
        for (CategoryEntity entity : categoryDao.getAll())
            data.add(entity.getTitle());
        choiceBoxCategory.setItems(data);
    }
}
