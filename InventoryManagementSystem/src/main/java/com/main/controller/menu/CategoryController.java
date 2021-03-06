package com.main.controller.menu;

import com.main.database.JpaConnector;
import com.main.model.export.ExcelExport;
import com.main.model.entity.CategoryEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {
    @FXML
    public TextField textFieldTitle;

    @FXML
    public Button buttonNew;
    @FXML
    public Button buttonRefresh;
    @FXML
    public Button buttonChange;
    @FXML
    public Button buttonDelete;
    @FXML
    public Button buttonExportToExcel;

    @FXML
    public TableView<CategoryEntity> tableView;
    @FXML
    public TableColumn<CategoryEntity, String> tableColumnId;
    @FXML
    public TableColumn<CategoryEntity, String> tableColumnTitle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNew.setOnAction(this::OnPress_Button_NewCategory);
        buttonDelete.setOnAction(this::OnPress_Button_Delete);
        buttonRefresh.setOnAction(this::OnPress_Button_Refresh);
        buttonChange.setOnAction(this::OnPress_Button_Change);
        buttonExportToExcel.setOnAction(this::OnPress_Button_ExportToExcel);
        handleTableViewSelection();
    }

    private void handleTableViewSelection() {
        tableView.getSelectionModel().selectedItemProperty().addListener(newSelection -> {
            if (newSelection != null) {
                displaySelectedInfo(tableView.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void displaySelectedInfo(CategoryEntity entity) {
        textFieldTitle.setText(entity.getTitle());
    }

    @FXML
    private void OnPress_Button_ExportToExcel(ActionEvent event) {
        Stage stage = (Stage) buttonExportToExcel.getScene().getWindow();
        ExcelExport<CategoryEntity> excelExport = new ExcelExport<>();
        excelExport.export("Category", tableView, stage);
    }

    @FXML
    private void OnPress_Button_NewCategory(ActionEvent event) {
        CategoryEntity categoryEntity = getCategoryEntity();
        if (categoryEntity != null) try {
            saveNewCategoryToDatabase(categoryEntity);
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Category Error", ex.getMessage());
        }
    }

    private void saveNewCategoryToDatabase(CategoryEntity categoryEntity) {
        JpaConnector.getCategory().save(categoryEntity);
        MainController.showAlert(Alert.AlertType.CONFIRMATION, "Category added", "Category was successfully added to database");
        displayInformationToTableView();
    }

    private CategoryEntity getCategoryEntity() {
        if (informationIsValid())
            return new CategoryEntity(textFieldTitle.getText());
        return null;
    }

    private boolean informationIsValid() {
        if (textFieldTitle.getText().length() < 1 || textFieldTitle == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", "Category title is empty. Please enter category title.");
            return false;
        }
        return true;
    }

    @FXML
    private void OnPress_Button_Refresh(ActionEvent event) {
        displayInformationToTableView();
    }

    private void displayInformationToTableView() {
        clearTableView();
        ObservableList<CategoryEntity> data = FXCollections.observableArrayList(JpaConnector.getCategory().getAll());
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

    private void assignInformationToTableView(ObservableList<CategoryEntity> data) {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableView.setItems(data);
    }

    @FXML
    private void OnPress_Button_Delete(ActionEvent event) {
        if (selectedItemsIsEmpty()) return;
        CategoryEntity selectedItem = tableView.getSelectionModel().getSelectedItem();
        try {
            deleteCategoryFromDatabase(selectedItem);
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }

    }

    private void deleteCategoryFromDatabase(CategoryEntity selectedItem) {
        CategoryEntity entity = JpaConnector.getCategory().get(selectedItem.getIdCategory()).get();
        JpaConnector.getCategory().delete(entity);
        displayInformationToTableView();
    }

    @FXML
    private void OnPress_Button_Change(ActionEvent event) {
        if (selectedItemsIsEmpty()) return;
        if (informationIsValid()) {
            saveChangedInformationToDatabase();
        }
    }

    private void saveChangedInformationToDatabase() {
        CategoryEntity selectedItem = tableView.getSelectionModel().getSelectedItem();
        selectedItem.setTitle(textFieldTitle.getText());
        JpaConnector.getCategory().update(selectedItem);

        MainController.showAlert(Alert.AlertType.INFORMATION, "Change", "Category was changed");
        displayInformationToTableView();
    }

    private boolean selectedItemsIsEmpty() {
        if (tableView.getSelectionModel().isEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", "Please select item from table below.");
            return true;
        }
        return false;
    }
}
