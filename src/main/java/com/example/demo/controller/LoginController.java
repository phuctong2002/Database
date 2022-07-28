package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.utils.AlertBox;
import com.example.demo.utils.DB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Handler;

public class LoginController {
    @FXML
    public TextField loginTextField;
    @FXML
    public PasswordField loginPasswordField;
    @FXML
    public CheckBox checkUser;
    @FXML
    public CheckBox checkAdmin;

    @FXML
    public void loginAction() throws SQLException, ClassNotFoundException {
        System.out.println(loginTextField.getText() + " : " + loginPasswordField.getText());
        ResultSet user = null;
        try {
            if (checkUser.isSelected()) {
                String queryStr = "select * from doctor where mail = '" + loginTextField.getText() + "';";

                user = DB.dbExecuteQuery(queryStr);
                if (user.next()) {
                    String a;
                    String b;
                    a = user.getString("mail");
                    b = user.getString("password");
                    if (a.equals(loginTextField.getText()) && b.equals(loginPasswordField.getText())) {
                        System.out.println("Login successfully");
                        HelloApplication.email = user.getString("mail");
                        HelloApplication.name = user.getString("first_name") + " " + user.getString("last_name");
                        HelloApplication.id = user.getString("doc_id");
                        HelloApplication.dob = user.getString("dob");
                        HelloApplication.pass = user.getString("password");
                        HelloApplication.first_name = user.getString("first_name");
                        HelloApplication.last_name = user.getString("last_name");
                        HelloApplication.address = user.getString("address");
                        HelloApplication.gender = user.getString("gender");
                        HelloApplication.hos_id = user.getString("hos_id");
                        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Doc/doctor.fxml"));
                        HelloApplication.scene = new Scene(HelloApplication.fxmlLoader.load());

                        HelloApplication.window.setTitle("Hello!");
                        HelloApplication.window.setScene(HelloApplication.scene);
                        HelloApplication.window.show();
                        AlertBox.displayAlert("Dang Nhap Thanh Cong");
                    } else {
                        System.out.println("Login Failed");
                        AlertBox.displayAlert("Email hoac mat khau khong chinh xac");
                    }

                } else {
                    AlertBox.displayAlert("Email hoac mat khau khong chinh xac");
                }
            } else if (checkAdmin.isSelected()) {
                String queryStr = "select * from boss where mail = '" + loginTextField.getText() + "';";

                user = DB.dbExecuteQuery(queryStr);
                if (user.next()) {
                    String a;
                    String b;
                    a = user.getString("mail");
                    b = user.getString("password");
                    if (a.equals(loginTextField.getText()) && b.equals(loginPasswordField.getText())) {
                        System.out.println("Login successfully");
                        HelloApplication.email = user.getString("mail");
                        HelloApplication.name = user.getString("first_name") + " " + user.getString("last_name");
                        HelloApplication.id = user.getString("boss_id");
                        HelloApplication.dob = user.getString("dob");
                        HelloApplication.pass = user.getString("password");
                        HelloApplication.gender = user.getString("gender");
                        HelloApplication.address = user.getString("address");
                        HelloApplication.first_name = user.getString("first_name");
                        HelloApplication.last_name = user.getString("last_name");



                        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("adminPage.fxml"));
                        HelloApplication.scene = new Scene(HelloApplication.fxmlLoader.load());

                        HelloApplication.window.setTitle("Hello!");
                        HelloApplication.window.setScene(HelloApplication.scene);
                        HelloApplication.window.show();
                        AlertBox.displayAlert("Dang Nhap Thanh Cong");
                    } else {
                        System.out.println("Login Failed");
                        AlertBox.displayAlert("Email hoac mat khau khong chinh xac");
                    }

                } else {
                    AlertBox.displayAlert("Email hoac mat khau khong chinh xac");
                }
                // thuc hien cho admin
            } else {
                String queryStr = "select * from patient where mail = '" + loginTextField.getText() + "';";

                user = DB.dbExecuteQuery(queryStr);
                String ward_id = null;
                if (user.next()) {
                    String a;
                    String b;
                    a = user.getString("mail");
                    b = user.getString("password");
                    if (a.equals(loginTextField.getText()) && b.equals(loginPasswordField.getText())) {
                        System.out.println("Login successfully");
                        HelloApplication.email = user.getString("mail");
                        HelloApplication.name = user.getString("first_name") + " " + user.getString("last_name");
                        HelloApplication.id = user.getString("pat_id");
                        HelloApplication.dob = user.getString("dob");
                        HelloApplication.pass = user.getString("password");
//                        HelloApplication.address = user.getString("address");
                        ward_id = user.getString("ward_id");
                        HelloApplication.ward_id = ward_id;
                        HelloApplication.gender = user.getString("gender");
                        HelloApplication.first_name = user.getString("first_name");
                        HelloApplication.last_name = user.getString("last_name");
                        HelloApplication.num = user.getInt("number_injection");



                        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UserPage.fxml"));
                        HelloApplication.scene = new Scene(HelloApplication.fxmlLoader.load());

                        HelloApplication.window.setTitle("Hello!");
                        HelloApplication.window.setScene(HelloApplication.scene);
                        HelloApplication.window.show();
                        AlertBox.displayAlert("Dang Nhap Thanh Cong");
                    } else {
                        System.out.println("Login Failed");
                        AlertBox.displayAlert("Email hoac mat khau khong chinh xac");
                    }

                } else {
                    AlertBox.displayAlert("Email hoac mat khau khong chinh xac");
                }
                if( ward_id != null){
                    queryStr = "select name from ward where ward_id ='"+ward_id+"';";
                    user = DB.dbExecuteQuery( queryStr);
                    if( user.next()){
                        HelloApplication.ward_name = user.getString("name");
                    }
                }
            }

        } catch (SQLException | IOException e) {
            System.out.println("Error in loginController : " + e.getMessage());
        } finally {
            System.out.println("finally");

        }


    }

    @FXML
    public void registerAction() throws SQLException, ClassNotFoundException {
        try {
            System.out.println("Register");
            HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("userRegister.fxml"));
            HelloApplication.scene = new Scene(HelloApplication.fxmlLoader.load());
            System.out.println("Register");
            HelloApplication.window.setScene(HelloApplication.scene);
            HelloApplication.window.show();

        } catch (IOException e) {

            System.out.println("Error in registerAction method in logincontroller\n" + e.getMessage() + "\n");
        }
    }
    @FXML
    public void checkUserAction() {
        System.out.println("Da Bam check use Action");
        checkAdmin.setSelected( false);
    }
    @FXML
    public void checkAdminAction() {
        checkUser.setSelected(false);
        System.out.println("Da Bam check Admin Action");
    }
}
