package com.main.database;

import com.main.controller.menu.MainController;
import javafx.scene.control.Alert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class EntityManagerConnector {
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
