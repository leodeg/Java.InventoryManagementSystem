package com.main.controller.menu;

import com.main.database.JpaConnector;
import com.main.model.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public Button buttonUpdateProducts;
    @FXML
    public PieChart pieChartProducts;
    @FXML
    public BarChart barCharProducts;

    @FXML
    public Button buttonUpdateFact;
    @FXML
    public PieChart pieChartFact;
    @FXML
    public BarChart barCharFact;

    @FXML
    public Button buttonUpdateArrival;
    @FXML
    public LineChart<String, Double> lineChartArrival;

    @FXML
    public Button buttonUpdateConsumption;
    @FXML
    public LineChart<String, Double> lineChartConsumption;

    @FXML
    public Button buttonUpdateOrders;
    @FXML
    public LineChart lineChartOrders;

    @FXML
    public Button buttonUpdateSales;
    @FXML
    public LineChart<String, Double> lineChartSales;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonUpdateProducts.setOnAction(this::OnPress_Button_UpdateProducts);
        buttonUpdateFact.setOnAction(this::OnPress_Button_UpdateFact);
        buttonUpdateArrival.setOnAction(this::OnPress_Button_UpdateArrival);
        buttonUpdateConsumption.setOnAction(this::OnPress_Button_UpdateConsumption);
        buttonUpdateOrders.setOnAction(this::OnPress_Button_UpdateOrders);
        buttonUpdateSales.setOnAction(this::OnPress_Button_UpdateSales);
    }

    @FXML
    private void OnPress_Button_UpdateProducts(ActionEvent event) {
        updateProductPiChartInformation();
        updateToolTipForProductPieChart(pieChartProducts, "%");
        updateProductsBarChar();
    }

    @FXML
    private void OnPress_Button_UpdateFact(ActionEvent event) {
        updateFactPiChartInformation();
        updateToolTipForProductPieChart(pieChartFact, " руб.");
        updateFactBarChar();
    }

    @FXML
    private void OnPress_Button_UpdateArrival(ActionEvent event) {
        updateArrivalLineChartInformation();
    }

    @FXML
    private void OnPress_Button_UpdateConsumption(ActionEvent event) {
        updateConsumptionLineChartInformation();
    }

    @FXML
    private void OnPress_Button_UpdateOrders(ActionEvent event) {
        updateOrdersLineChartInformation();
    }

    @FXML
    private void OnPress_Button_UpdateSales(ActionEvent event) {
        updateSalesLineChartInformation();
    }

    private void updateProductPiChartInformation() {
        List<CategoryEntity> categoryEntityList = JpaConnector.getCategory().getAll();
        if (categoryEntityList.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table is empty", "No data in database.");
            return;
        }

        if (pieChartProducts.getData().size() > 0)
            pieChartProducts.getData().clear();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (CategoryEntity entity : categoryEntityList) {
            long productsCount = JpaConnector.getProduct().getCount(entity.getIdCategory());
            pieChartData.add(new PieChart.Data(entity.getTitle(), productsCount));
        }

        pieChartProducts.setData(pieChartData);
        pieChartProducts.setTitle("Продукция по категориям");
    }

    private void updateToolTipForProductPieChart(PieChart chart, String ending) {
        for (final PieChart.Data data : chart.getData()) {
            data.setName(data.getName() + " " + data.getPieValue() + ending);
        }
    }

    private void updateProductsBarChar() {
        List<String> names = JpaConnector.getProduct().getAllNames();
        if (names.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table is empty", "No data in database.");
            return;
        }

        if (barCharProducts.getData().size() > 0)
            barCharProducts.getData().clear();

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (String name : names) {
            double price = JpaConnector.getProduct().getPriceByName(name);
            series.getData().add(new XYChart.Data<>(name, price));
        }

        barCharProducts.getData().setAll(series);
        barCharProducts.setTitle("Цены продуктов");
    }

    private void updateFactPiChartInformation() {
        List<FactEntity> factEntityList = JpaConnector.getFact().getAll();
        if (factEntityList.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table is empty", "No data in database.");
            return;
        }

        if (pieChartFact.getData().size() > 0)
            pieChartFact.getData().clear();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (FactEntity entity : factEntityList) {
            String productName = JpaConnector.getProduct().get(entity.getIdProduct()).get().getName();
            pieChartData.add(new PieChart.Data(productName, entity.getTotalPrice()));
        }

        pieChartFact.setData(pieChartData);
        pieChartFact.setTitle("Доля продукции на складе");
    }

    private void updateFactBarChar() {
        List<FactEntity> entityList = JpaConnector.getFact().getAll();
        if (entityList.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table is empty", "No data in database.");
            return;
        }

        if (barCharFact.getData().size() > 0)
            barCharFact.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (FactEntity entity : entityList) {
            String name = JpaConnector.getProduct().get(entity.getIdProduct()).get().getName();
            series.getData().add(new XYChart.Data<>(name, entity.getAmount()));
        }

        barCharFact.getData().setAll(series);
        barCharFact.setTitle("Товар и кол-во на складе");
    }

    private void updateArrivalLineChartInformation() {
        List<ArrivalEntity> arrivalEntityList = JpaConnector.getArrival().getAll();
        if (arrivalEntityList.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table is empty", "No data in database.");
            return;
        }

        if (lineChartArrival.getData().size() > 0)
            lineChartArrival.getData().clear();

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (ArrivalEntity entity : arrivalEntityList) {
            Double totalPrice = JpaConnector.getArrival().getSumTotalPriceByDate(entity.getDate());
            Long totalAmount = JpaConnector.getArrival().getSumAmountByDate(entity.getDate());
            series.getData().add(new XYChart.Data<>(getEntityString(totalPrice, totalAmount, entity.getDate()).toString(), totalPrice));
        }

        lineChartArrival.getData().setAll(series);
        lineChartArrival.setTitle("Дата и сумма");
    }

    private void updateConsumptionLineChartInformation() {
        List<ConsumptionEntity> consumptionEntityList = JpaConnector.getConsumption().getAll();
        if (consumptionEntityList.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table is empty", "No data in database.");
            return;
        }

        if (lineChartConsumption.getData().size() > 0)
            lineChartConsumption.getData().clear();

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (ConsumptionEntity entity : consumptionEntityList) {
            Double totalPrice = JpaConnector.getConsumption().getSumTotalPriceByDate(entity.getDate());
            Long totalAmount = JpaConnector.getConsumption().getSumAmountByDate(entity.getDate());
            series.getData().add(new XYChart.Data<>(getEntityString(totalPrice, totalAmount, entity.getDate()).toString(), totalPrice));
        }

        lineChartConsumption.getData().setAll(series);
        lineChartConsumption.setTitle("Дата и сумма");
    }

    private void updateOrdersLineChartInformation() {
        List<OrderEntity> list = JpaConnector.getOrder().getAll();
        if (list.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table is empty", "No data in database.");
            return;
        }

        if (lineChartOrders.getData().size() > 0)
            lineChartOrders.getData().clear();

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (OrderEntity entity : list) {
            Double totalPrice = JpaConnector.getOrder().getSumTotalPriceByDate(entity.getDate());
            Long totalAmount = JpaConnector.getOrder().getSumAmountByDate(entity.getDate());
            series.getData().add(new XYChart.Data<>(getEntityString(totalPrice, totalAmount, entity.getDate()).toString(), totalPrice));
        }

        lineChartOrders.getData().setAll(series);
        lineChartOrders.setTitle("Дата и сумма");
    }

    private void updateSalesLineChartInformation() {
        List<SaleEntity> list = JpaConnector.getSale().getAll();
        if (list.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table is empty", "No data in database.");
            return;
        }

        if (lineChartSales.getData().size() > 0)
            lineChartSales.getData().clear();

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (SaleEntity entity : list) {
            Double totalPrice = JpaConnector.getSale().getSumTotalPriceByDate(entity.getDate());
            Long totalAmount = JpaConnector.getSale().getSumAmountByDate(entity.getDate());
            series.getData().add(new XYChart.Data<>(getEntityString(totalPrice, totalAmount, entity.getDate()).toString(), totalPrice));
        }

        lineChartSales.getData().setAll(series);
        lineChartSales.setTitle("Дата и сумма");
    }

    @NotNull
    private StringBuilder getEntityString(Double totalPrice, Long totalAmount, Date date) {
        StringBuilder string = new StringBuilder();
        string.append(date);
        string.append("\n");
        string.append("Сумма: ").append(totalPrice.toString()).append(" руб.");
        string.append("\n");
        string.append("Кол-во товара: ").append(totalAmount);
        return string;
    }
}
