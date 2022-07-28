package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class HelloApplication extends Application {
    public static String name = "";
    public static String first_name ="";
    public static String last_name = "";
    public static String email = "";
    public static String id = "";
    public static String dob = "";
    public static String pass = "";

    public static int num = 0;

    // sau khi lam xong co the bo di nhe
    public static String address = "";
    public static String hospital = "";
    // sau khi fix xong co the bo di nhe

    public static String gender = "";
    public static String ward_id = "";
    public static String ward_name = "";
    public static String hos_id = "";

    public static Stage window = null;
    public static Scene scene = null;
    public static FXMLLoader fxmlLoader = null;
    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.window = stage;
        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        HelloApplication.scene = new Scene(HelloApplication.fxmlLoader.load());
//        HelloApplication.window.setMaximized( true);
        HelloApplication.window.setTitle("Hello!");
        HelloApplication.window.setScene(scene);
        HelloApplication.window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}