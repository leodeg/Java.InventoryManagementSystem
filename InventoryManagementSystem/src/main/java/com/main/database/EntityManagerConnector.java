package com.main.database;

import com.main.controller.menu.MainController;
import javafx.scene.control.Alert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class EntityManagerConnector {
    @PersistenceContext
    private static EntityManager entityManager;
    private static EntityManagerFactory managerFactory;

    public static boolean initialize () throws NullPointerException {
        try {
            managerFactory = Persistence.createEntityManagerFactory("jpa");
            entityManager = managerFactory.createEntityManager();
            return true;
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR,
                    "Database",
                    "Can't create connection to the database. \nError: " + ex.getMessage());
        }
        return false;
    }

    public static void close() {
        if (entityManager != null)
            entityManager.close();
        if (managerFactory != null)
            managerFactory.close();
    }

    public static EntityManager getEntityManager() throws NullPointerException {
        if (entityManager == null) initialize();
        return entityManager;
    }
}
