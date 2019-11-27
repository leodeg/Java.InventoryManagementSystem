package com.main.controller;

import com.main.model.entity.ArrivalEntity;
import com.main.model.entity.CategoryEntity;
import com.main.model.entity.FactEntity;
import com.main.model.entity.ProductEntity;
import com.main.model.jpa.JpaArrivalDao;
import com.main.model.jpa.JpaCategoryDao;
import com.main.model.jpa.JpaFactDao;
import com.main.model.jpa.JpaProductDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;

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
    @FXML
    public TextField textFieldArrivalAmount;
    @FXML
    public DatePicker datePickerArrival;

    private JpaProductDao productDao;
    private JpaCategoryDao categoryDao;
    private JpaArrivalDao arrivalDao;
    private JpaFactDao factDao;

    public ProductsController() {
        productDao = new JpaProductDao();
        categoryDao = new JpaCategoryDao();
        arrivalDao = new JpaArrivalDao();
        factDao = new JpaFactDao();
    }

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
        if (!priceHasOnlyNumbers(textFieldPrice)) {
            MainController.showAlert(Alert.AlertType.ERROR, "Error", "Price contains letters.");
            return false;
        }
        return true;
    }

    private boolean priceHasOnlyNumbers(TextField textField) {
        try {
            Double.parseDouble(textField.getText());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean amountHastOnlyNumbers(TextField textField) {
        try {
            Integer.parseInt(textField.getText());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

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

    public void OnPress_Button_RefreshChoice(ActionEvent event) {
        populateChoiceBox();
    }

    private void populateChoiceBox() {
        ObservableList<String> data = FXCollections.observableArrayList();
        for (CategoryEntity entity : categoryDao.getAll())
            data.add(entity.getTitle());
        choiceBoxCategory.setItems(data);
    }

    public void OnPress_Button_AddArrival(ActionEvent event) {
        saveNewArrivalToDatabase();
    }

    private void saveNewArrivalToDatabase() {
        ArrivalEntity entity = getNewArrival();
        if (entity != null) {
            arrivalDao.save(entity);
            updateFactDatabaseTable(entity);
        }
    }

    private void updateFactDatabaseTable(ArrivalEntity entity) {
            FactEntity factEntity = factDao.getFirstByIdProduct(entity.getIdProduct());
            MainController.showAlert(Alert.AlertType.CONFIRMATION, "ProductEntity", factEntity.toString());
            if (factEntity == null) {
                factDao.save(new FactEntity(entity.getIdProduct(), entity.getAmount(), entity.getPrice(), entity.getDate()));
            } else {
                factEntity.setAmount(factEntity.getAmount() + entity.getAmount());
                factEntity.setPrice(entity.getPrice());
                factEntity.setDate(entity.getDate());
                factDao.update(factEntity);
            }
    }

    @Nullable
    private ArrivalEntity getNewArrival() {
        if (checkArrivalInformation()) {
            ProductEntity entity = tableView.getSelectionModel().getSelectedItem();
            MainController.showAlert(Alert.AlertType.CONFIRMATION, "ProductEntity", entity.toString());
            return new ArrivalEntity(entity.getIdProduct(),
                    Integer.parseInt(textFieldArrivalAmount.getText()),
                    entity.getPrice(),
                    Date.valueOf(datePickerArrival.getValue()));
        }
        return null;
    }

    private boolean checkArrivalInformation() {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Arrival Error", "Please select a product from the table below.");
            return false;
        }
        if (!amountHastOnlyNumbers(textFieldArrivalAmount)) {
            MainController.showAlert(Alert.AlertType.ERROR, "Arrival Error", "Amount must contain only numbers.");
            return false;
        }
        if (datePickerArrival.getValue() == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Arrival Error", "Choose date from date picker.");
            return false;
        }

        return true;
    }
}
