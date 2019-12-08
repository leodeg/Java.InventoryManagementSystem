package com.main.model.export;

import com.main.controller.menu.MainController;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExport<T> {
    public void export(String fileName, TableView<T> tableView, Stage stage) {
        if (tableViewIsEmpty(tableView)) return;

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose directory");
        File selectedFile = directoryChooser.showDialog(stage);
        export(tableView, selectedFile.getPath(), fileName);
    }

    public void export(TableView<T> tableView, String path, String fileName) {
        if (tableViewIsEmpty(tableView)) return;

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Sheet 1");
        HSSFRow firstRow = hssfSheet.createRow(0);

        assignWorkbookColumnsTitles(tableView, firstRow);
        assignInformationToWorkbook(tableView, hssfSheet, firstRow);
        saveExcelWorkbook(hssfWorkbook, path, fileName);
    }

    private boolean tableViewIsEmpty(TableView<T> tableView) {
        if (tableView.getItems().isEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Export to excel", "Table view is empty");
            return true;
        }
        return false;
    }

    private void assignWorkbookColumnsTitles(TableView<T> tableView, HSSFRow firstRow) {
        for (int i = 0; i < tableView.getColumns().size(); i++) {
            firstRow.createCell(i).setCellValue(tableView.getColumns().get(i).getText());
        }
    }

    private void assignInformationToWorkbook(TableView<T> tableView, HSSFSheet hssfSheet, HSSFRow firstRow) {
        for (int row = 0; row < tableView.getItems().size(); row++) {
            HSSFRow newRow = hssfSheet.createRow(row + 1);
            for (int column = 0; column < tableView.getColumns().size(); column++) {
                Object cellValue = tableView.getColumns().get(column).getCellObservableValue(row).getValue();
                try {
                    if (cellValue != null && Double.parseDouble(cellValue.toString()) != 0.0)
                        newRow.createCell(column).setCellValue(Double.parseDouble(cellValue.toString()));
                } catch (NumberFormatException e) {
                    newRow.createCell(column).setCellValue(cellValue.toString());
                }
            }
        }
    }

    private void saveExcelWorkbook(HSSFWorkbook hssfWorkbook, String path, String fileName) {
        String fullPath = path + fileName + ".xls";
        MainController.showAlert(Alert.AlertType.INFORMATION, "Export to excel", "Export to excel was succeed. \n Path: " + fullPath);
        try {
            hssfWorkbook.write(new FileOutputStream(fullPath));
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
