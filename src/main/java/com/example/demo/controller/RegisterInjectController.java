package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.utils.AlertBox;
import com.example.demo.utils.DB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterInjectController implements Initializable {
    @FXML
    public TextField nameRegister;
    @FXML
    public TextField idRegister;
    @FXML
    public ComboBox<String> hospitalRegister;
    @FXML
    public DatePicker timeRegister;
    @FXML
    public Button btnReg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameRegister.setText( HelloApplication.name);
        idRegister.setText(HelloApplication.id);
//        String[] hospital = {
//                "Quynh Mai", "Bach Mai", "Minh Khai", "Quynh Loi", "Truong Dinh", "Dong Tam", "Le Dai Hanh",
//                "Nguyen Du", "Pham Dinh Ho", "Cau Den", "Pho Hue", "Bach Khoa", "Vinh Tuy", "Thanh Luong",
//                "Bach Dang", "Dong Mac", "Thanh Nhan", "Dong Nhan"
//        };
//        hospitalRegister.getItems().addAll( hospital);
//        hospitalRegister.setValue( HelloApplication.hospital);
        String queryStr = "select name from hospital";
        ResultSet hos = null;
        try {
            hos = DB.dbExecuteQuery(queryStr);
            while( hos.next()) {
                hospitalRegister.getItems().add(hos.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void submitRegister(){
        System.out.println("Form dang ky gui len nhe anh em");
        String hos_name = hospitalRegister.getValue();
        String inject_time = String.valueOf(timeRegister.getValue());
        String queryStr = "select hos_id from hospital where name = '" + hos_name + "';";
        String hos_id = "";
        try {
            ResultSet hos = DB.dbExecuteQuery(queryStr);
            if( hos.next()){
                hos_id = hos.getString("hos_id");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        queryStr = "insert into injection_registration_form ( hos_id, pat_id, injection_time) values "
                +"('" + hos_id + "', '" + HelloApplication.id+ "', '" + inject_time + "');";
        try {
            int a = DB.dbExecuteUpdate(queryStr);
            if( a == 0){
                btnReg.setDisable(true);
                AlertBox.displayAlert("Dang ky thanh cong");
            }else{
                AlertBox.displayAlert("Dang ky khong thanh cong");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
