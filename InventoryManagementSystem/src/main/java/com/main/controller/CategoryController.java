package com.main.controller;

import com.main.model.entity.CategoryEntity;
import com.main.model.jpa.JpaCategoryDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryController {
    @FXML
    public TextField textFieldTitle;

    @FXML
    public Button buttonNew;
    @FXML
    public Button buttonRefresh;

    @FXML
    public TableView<CategoryEntity> tableView;
    @FXML
    public TableColumn<CategoryEntity, String> tableColumnId;
    @FXML
    public TableColumn<CategoryEntity, String> tableColumnTitle;
    @FXML
    public TableColumn tableColumnActions;

    private JpaCategoryDao categoryDao;

    public CategoryController () {
        categoryDao = new JpaCategoryDao();
    }

    public void OnPress_Button_NewCategory(ActionEvent event) {
        CategoryEntity categoryEntity = getCategoryEntity();
        if (categoryEntity != null) try {
            categoryDao.save(categoryEntity);
            MainController.showAlert(Alert.AlertType.CONFIRMATION, "Category added", "Category was successfully added to database");
            showCategoriesToTableView();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Category Error", ex.getMessage());
        }
    }

    private CategoryEntity getCategoryEntity() {
        if (checkCategoryValidation())
            return new CategoryEntity(textFieldTitle.getText());
        return null;
    }

    private boolean checkCategoryValidation() {
        if (textFieldTitle.getText().length() < 1 || textFieldTitle == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", "Category title is empty. Please enter category title.");
            return false;
        }
        return true;
    }

    public void OnPress_Button_Refresh (ActionEvent event) {
        showCategoriesToTableView();
    }

    private void showCategoriesToTableView () {
        ObservableList<CategoryEntity> data = FXCollections.observableArrayList(categoryDao.getAll());
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableView.setItems(data);
    }

    public void OnPress_Button_Delete (ActionEvent event) {
        if (checkCategoryValidation()) try {
            categoryDao.delete(categoryDao.get(textFieldTitle.getText()));
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
    }
}
