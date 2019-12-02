package com.main.database;

import com.main.controller.menu.MainController;
import javafx.scene.control.Alert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class EntityManagerConnector {
    private static EntityManagerFactory managerFactory;
    @PersistenceContext
    private static EntityManager entityManager;

    static {
        try {
            managerFactory = Persistence.createEntityManagerFactory("jpa");
            entityManager = managerFactory.createEntityManager();
        } catch (NullPointerException ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Database", "Can't create connection to the database. \nError::NullPointerException:: " + ex.getMessage());
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Database", "Can't create connection to the database. \nError: " + ex.getMessage());
        }
    }

    public static void close() {
        if (getEntityManager() != null)
            getEntityManager().close();
        if (managerFactory != null)
            managerFactory.close();
    }

    public static EntityManager getEntityManager() throws NullPointerException {
        if (entityManager == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Database", "Can't create connection to the database. Entity manager is empty.");
        }
        return entityManager;
    }
}
