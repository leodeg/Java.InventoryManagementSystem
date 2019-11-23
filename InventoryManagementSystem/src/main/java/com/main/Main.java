package com.main;

import com.main.model.entity.AddressEntity;
import com.main.model.jpa.JpaAddressDao;

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
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}


public class Main {
    public static void main(String[] args) {
        JpaAddressDao addressDao = new JpaAddressDao();
        AddressEntity addressEntity = new AddressEntity("some address", "some address2", "Lugansk", "Art");
        System.out.print(addressDao.toString());
        addressDao.save(addressEntity);
    }
}
