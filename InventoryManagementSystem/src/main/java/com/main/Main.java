package com.main;

import com.main.model.entity.BeginEntity;
import com.main.model.entity.ProductEntity;
import com.main.model.jpa.JpaBeginDao;
import com.main.model.jpa.JpaProductDao;

import java.util.Date;

//public class Main extends Application {
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

public class Main {
    public static void main(String[] args) {
//        CreateBeginEntity();
//        UpdateProduct();
    }

    private static void CreateBeginEntity() {
        JpaBeginDao beginDao = new JpaBeginDao();
        BeginEntity beginEntity = new BeginEntity(37, 10, 15.00, new Date());
        System.out.print(beginEntity.toString());
        beginDao.save(beginEntity);
        beginDao.close();
    }

    private static void UpdateProduct () {
        JpaProductDao productDao = new JpaProductDao();

        ProductEntity productEntity = productDao.get(36).get();
        System.out.print("\n\n");
        System.out.print(productEntity.toString());
        System.out.print("\n\n");
        productDao.update(productEntity, new String[] {"1", "Computer", "15.0", "Computer"});

        productEntity = productDao.get(36).get();
        System.out.print("\n\n");
        System.out.print(productEntity.toString());
        System.out.print("\n\n");
        productDao.close();
    }
}
