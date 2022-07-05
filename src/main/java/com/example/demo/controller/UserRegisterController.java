package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.utils.AlertBox;
import com.example.demo.utils.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserRegisterController implements Initializable {
    @FXML
    public TextField firstNameText;
    @FXML
    public TextField lastNameText;
    @FXML
    public ChoiceBox gender;
    @FXML
    public DatePicker dob;
    @FXML
    public TextField mailTextField;
    @FXML
    public ComboBox<String> addressComboBox;
    @FXML
    public ComboBox<String> hopistalComboBox;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField idNumber;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gender.getItems().addAll("Male", "Female");
        String[] address = {"Bach Khoa", "Bach Dang", "Bach Mai", "Cau Den", "Dong Mac", "Dong Nhan", "Dong Tam",
        "Le Dai Hanh", "Minh Khai", "Nguyen Du", "Pham Dinh Ho", "Pho Hue", "Quynh Loi", "Quynh Mai", "Thanh Luong", "Thanh Nhan", "Truong Dinh", "Vinh Tuy"};
        addressComboBox.getItems().addAll(address);
        String[] hospital = {
                "Quynh Mai", "Bach Mai", "Minh Khai", "Quynh Loi", "Truong Dinh", "Dong Tam", "Le Dai Hanh",
                "Nguyen Du", "Pham Dinh Ho", "Cau Den", "Pho Hue", "Bach Khoa", "Vinh Tuy", "Thanh Luong",
                "Bach Dang", "Dong Mac", "Thanh Nhan", "Dong Nhan"
        };
        hopistalComboBox.getItems().addAll( hospital);
    }
    @FXML
    public void registerAction(){
//        if( firstNameText.getText() == "" || lastNameText.getText() == "" || mailTextField.getText() == "" || passwordField.getText() == ""){
//            System.out.println("Nhap day du thong tin");
//
//        }
//        if( gender.getValue() != "Male" || gender.getValue() != "Female"){
//            System.out.println("Gender");
//        }
//        if( addressComboBox.getValue() != null){
//            System.out.println( addressComboBox.getValue());
//        }else{
//            System.out.println("Null nhe");
//        }
//        if( dob.getValue() != null){
//            System.out.println( dob.getValue());
//        }else{
//            System.out.println("Null nhe chon ngay sinh di bn oi");
//        }
        if( firstNameText.getText() == "" || lastNameText.getText() == "" || mailTextField.getText() == "" || passwordField.getText() == "" || idNumber.getText() == ""){
            if( gender.getValue() != "Male" || gender.getValue() != "Female" ){
                if( addressComboBox.getValue() == null || hopistalComboBox.getValue() == null){
                    if( dob.getValue() == null ){
                        AlertBox.displayAlert("Please nhap day du thong tin");
                    }
                }
            }
        }else{
            ResultSet rs = null;
            try{
                String queryStr = "select hos_id from hospital where name = '" + hopistalComboBox.getValue() + "';";
                rs = DB.dbExecuteQuery( queryStr);
                String hos_id = null;
                if( rs.next()){
                    hos_id = rs.getString("hos_id");
                    System.out.println(hos_id);
                }
                // thuc hien insert thong tin vao trong  database
                String first_name = firstNameText.getText();
                String last_name = lastNameText.getText();
                String gd = null;
                if( gender.getValue() == "Male") gd = "M";
                else gd = "F";
                String d = String.valueOf(dob.getValue());
                String m = mailTextField.getText();
                String address = addressComboBox.getValue();
                String hospital = hopistalComboBox.getValue();
                String pw = passwordField.getText();
                String id = idNumber.getText();
//                System.out.println(  first_name + "\n" + last_name + "\n" + gd +  "\n" + d +  "\n" + m +  "\n" + address +  "\n" + hospital +  "\n" + pw + "\n" + id );
                // tao query insert nhe
                queryStr = "insert into patient ( pat_id, hos_id, first_name, last_name, dob, gender, mail, address, password) values"+
                "('"+ id + "','" + hos_id + "','" + first_name + "','" + last_name + "','" + d + "','" + gd + "','" + m + "','" + address + "','" + pw +"');" ;
                System.out.println( queryStr);
                // thuc hien insert nhe
                if( DB.dbExecuteUpdate( queryStr) == 0){
                    // thanh cong nhe
                    HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
                    HelloApplication.scene = new Scene(HelloApplication.fxmlLoader.load());

                    HelloApplication.window.setTitle("Hello!");
                    HelloApplication.window.setScene(HelloApplication.scene);
                    HelloApplication.window.show();


                    // hien thi thong bao dang ky thanh cong nhe
                    AlertBox.displayAlert("Dang ky thanh cong");
                }else{
                    // thong bao co loi dang ky lai nhe
                    AlertBox.displayAlert("Dang ky thong tin khong hop le. Nhap lai");
                }


                // quay lai man hinh dang nhap nhe

            }catch( SQLException | IOException e){
                System.out.println( e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }
    @FXML
    public void cancelBtn() throws IOException{
        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        HelloApplication.scene = new Scene(HelloApplication.fxmlLoader.load());
//        HelloApplication.window.setMaximized( true);
        HelloApplication.window.setTitle("Hello!");
        HelloApplication.window.setScene(HelloApplication.scene);
        HelloApplication.window.show();
    }

}
