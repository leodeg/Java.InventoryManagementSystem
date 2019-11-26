package com.main.controller;

import com.main.model.entity.CategoryEntity;
import com.main.model.jpa.JpaCategoryDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class CategoryController {
    @FXML
    public TextField uiCategoryTextFieldTitle;

    @FXML
    public Button uiCategoryButtonNew;
    @FXML
    public Button uiCategoryButtonRefresh;

    @FXML
    public TableView uiCategoryTableView;
    @FXML
    public TableColumn uiCategoryTableColumnId;
    @FXML
    public TableColumn uiCategoryTableColumnTitle;
    @FXML
    public TableColumn uiCategoryTableColumnActions;

    JpaCategoryDao categoryDao;

    public CategoryController () {
        categoryDao = new JpaCategoryDao();
    }

    public void OnPress_Button_NewCategory(ActionEvent event) {
        CategoryEntity categoryEntity = getCategoryEntity();
        if (categoryEntity != null) {
            try {
                categoryDao.save(categoryEntity);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Category added");
                alert.setContentText("Category was successfully added to database");
                alert.show();
                showCategoriesToTableView();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Category Error");
                alert.setContentText("Category title duplicate." + "\n" + ex.getMessage());
                alert.show();
            }
        }
    }

    private CategoryEntity getCategoryEntity() {
        if (checkCategoryValidation())
            return new CategoryEntity(uiCategoryTextFieldTitle.getText());
        return null;
    }

    private boolean checkCategoryValidation() {
        if (uiCategoryTextFieldTitle.getText().length() < 1 || uiCategoryTextFieldTitle == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Category title is empty. Please enter category title.");
            alert.show();
            return false;
        }
        return true;
    }

    public void OnPress_Button_Refresh () {
        showCategoriesToTableView();
    }

    private void showCategoriesToTableView () {
        ObservableList<CategoryEntity> data = FXCollections.observableArrayList(categoryDao.getAll());
        uiCategoryTableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        uiCategoryTableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        uiCategoryTableView.setItems(data);
    }


}
