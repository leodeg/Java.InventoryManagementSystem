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
//        CreateAndSaveAddress();
//        GetAddress();
        Update();
    }

    private static void CreateAndSaveAddress() {
        System.out.print("Start program\n\n");
        JpaAddressDao addressDao = new JpaAddressDao();

        System.out.print("\n\nCreate JpaAddressDao\n");
        AddressEntity addressEntity = new AddressEntity("some address", "some address2", "Lugansk", "Art");

        System.out.print("\n\nCreate a new addressEntity\n");
        System.out.print(addressEntity.toString());

        System.out.print("\n\nSave to database\n");
        addressDao.save(addressEntity);


        System.out.print("\n\nEnd of the program\n");
        addressDao.close();
    }

    private static void GetAddress() {
        System.out.print("Start program\n\n");
        JpaAddressDao addressDao = new JpaAddressDao();

        System.out.print("\n\nGet entity from database\n");
        AddressEntity addressEntity = addressDao.get(1).get();
        System.out.print(addressEntity.toString());

        System.out.print("\n\nEnd of the program\n");
        addressDao.close();
    }

    private static void Update () {
        System.out.print("Start program\n\n");
        JpaAddressDao addressDao = new JpaAddressDao();

        System.out.print("\n\nCreate JpaAddressDao\n");
        AddressEntity addressEntity = new AddressEntity("some address", "some address2", "Lugansk", "Art");

        System.out.print("\n\nCreate a new addressEntity\n");
        System.out.print(addressEntity.toString());

        System.out.print("\n\nSave to database\n");
        addressDao.update(addressEntity, new String[] {"some address", "some address2", "Amsterdam", "Art"});

        System.out.print("\n\nEnd of the program\n");
        addressDao.close();
    }
}
