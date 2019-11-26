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

import javax.swing.*;
import java.util.List;

public class ProductsController {
    @FXML
    public ChoiceBox<String> uiChoiceBoxCategory;
    @FXML
    public TextField uiTextFieldName;
    @FXML
    public TextField uiTextFieldPrice;
    @FXML
    public TextField uiTextFieldDescription;

    @FXML
    public TableView<ProductEntity> uiTableView;
    @FXML
    public TableColumn uiTableColumnIdProduct;
    @FXML
    public TableColumn uiTableColumnIdCategory;
    @FXML
    public TableColumn uiTableColumnName;
    @FXML
    public TableColumn uiTableColumnPrice;
    @FXML
    public TableColumn uiTableColumnDescription;
    @FXML
    public TableColumn uiTableColumnActions;

    JpaProductDao productDao;
    JpaCategoryDao categoryDao;

    public ProductsController() {
        productDao = new JpaProductDao();
        categoryDao = new JpaCategoryDao();
    }

    public void OnPress_Button_NewProduct(ActionEvent event) {
        ProductEntity productEntity = getProductEntity();
        if (productEntity != null) {
            try {
                productDao.save(productEntity);
                showAlert(Alert.AlertType.CONFIRMATION, "Product added", "Product was successfully added to database");
                showCategoriesToTableView();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Product Error", ex.getMessage());
            }
        }
    }

    private ProductEntity getProductEntity() {
        try {
            CategoryEntity entity = categoryDao.getByTitle(uiChoiceBoxCategory.getValue()).get(0);
            showAlert(Alert.AlertType.CONFIRMATION, "Info", entity.toString());
            if (checkCategoryValidation())
                return new ProductEntity(entity.getIdCategory(), uiTextFieldName.getText(), Double.parseDouble(uiTextFieldPrice.getText()), uiTextFieldDescription.getText());
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Product Error", ex.getMessage());
        }
        return null;
    }

    private boolean checkCategoryValidation() {
        if (uiTextFieldName.getText().length() < 1 || uiTextFieldPrice.getText().length() < 1) {
            showAlert(Alert.AlertType.ERROR, "Error", "Name or price is empty. Please enter an information.");
            return false;
        }
        return true;
    }

    public void OnPress_Button_Refresh (ActionEvent event) {
        showCategoriesToTableView();
        populateChoiceBox();
    }

    private void showCategoriesToTableView () {
        ObservableList<ProductEntity> data = FXCollections.observableArrayList(productDao.getAll());
        uiTableColumnIdProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        uiTableColumnIdCategory.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        uiTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        uiTableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        uiTableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        uiTableView.setItems(data);
    }

    public void populateChoiceBox() {
        ObservableList<String> data = FXCollections.observableArrayList();
        List<CategoryEntity> categoryEntities = categoryDao.getAll();
        for (CategoryEntity entity : categoryEntities)
            data.add(entity.getTitle());
        uiChoiceBoxCategory.setItems(data);
    }

    private void showAlert(Alert.AlertType error, String s, String message) {
        Alert alert = new Alert(error);
        alert.setTitle(s);
        alert.setContentText(message);
        alert.show();
    }
}
