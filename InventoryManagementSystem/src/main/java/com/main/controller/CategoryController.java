package com.main.controller;

import com.main.database.jpa.JpaConnector;
import com.main.model.entity.CategoryEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    public void OnPress_Button_NewCategory(ActionEvent event) {
        CategoryEntity categoryEntity = getCategoryEntity();
        if (categoryEntity != null) try {
            JpaConnector.getCategory().save(categoryEntity);
            MainController.showAlert(Alert.AlertType.CONFIRMATION, "Category added", "Category was successfully added to database");
            displayInformationToTableView();
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

    public void OnPress_Button_Refresh(ActionEvent event) {
        displayInformationToTableView();
    }

    private void displayInformationToTableView() {
        if (tableView.getItems().size() > 0)
            tableView.getItems().clear();

        ObservableList<CategoryEntity> data = FXCollections.observableArrayList(JpaConnector.getCategory().getAll());
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableView.setItems(data);
    }

    public void OnPress_Button_Delete(ActionEvent event) {
        CategoryEntity selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", "Please select item from table below.");
            return;
        }

        try {
            CategoryEntity entity = JpaConnector.getCategory().get(selectedItem.getIdCategory()).get();
            JpaConnector.getCategory().delete(entity);
            displayInformationToTableView();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
    }
}
