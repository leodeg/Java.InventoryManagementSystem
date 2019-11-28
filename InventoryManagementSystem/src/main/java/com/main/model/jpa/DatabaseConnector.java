package com.main.model.jpa;

import com.main.controller.MainController;
import javafx.scene.control.Alert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class DatabaseConnector {
    @PersistenceContext
    static protected EntityManager entityManager;
    static EntityManagerFactory managerFactory;

    static {
        try {
            managerFactory = Persistence.createEntityManagerFactory("jpa");
            entityManager = managerFactory.createEntityManager();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Database", "Can't create connection to the database. \nError: " + ex.getMessage());
        }
    }

    public static void close() {
        entityManager.close();
        managerFactory.close();
    }
}
