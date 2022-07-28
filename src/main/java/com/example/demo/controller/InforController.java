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
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.demo.HelloApplication.*;

public class InforController implements Initializable {

    @FXML
    public Button btnSubmitInfor;

    @FXML
    public TextField nameInfor;
    @FXML
    public TextField idInfor;
    @FXML
    public TextField mailInfor;
    @FXML
    public PasswordField passInfor;
    @FXML
    public ChoiceBox genderInfor;
    @FXML
    public DatePicker dobInfor;
    @FXML
    public ComboBox<String> addressInfor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderInfor.getItems().addAll("Male", "Female");
        String[] address = {"Bach Khoa", "Bach Dang", "Bach Mai", "Cau Den", "Dong Mac", "Dong Nhan", "Dong Tam",
                "Le Dai Hanh", "Minh Khai", "Nguyen Du", "Pham Dinh Ho", "Pho Hue", "Quynh Loi", "Quynh Mai", "Thanh Luong", "Thanh Nhan", "Truong Dinh", "Vinh Tuy"};
        String[] hospital = {
                "Quynh Mai", "Bach Mai", "Minh Khai", "Quynh Loi", "Truong Dinh", "Dong Tam", "Le Dai Hanh",
                "Nguyen Du", "Pham Dinh Ho", "Cau Den", "Pho Hue", "Bach Khoa", "Vinh Tuy", "Thanh Luong",
                "Bach Dang", "Dong Mac", "Thanh Nhan", "Dong Nhan"
        };

        addressInfor.getItems().addAll( address);
        nameInfor.setText( HelloApplication.first_name);
        idInfor.setText( HelloApplication.last_name);
        mailInfor.setText( email);
        passInfor.setText( HelloApplication.pass);
        if( HelloApplication.gender.equals("M") ){
            System.out.println("----------Nam nhe----------");
            genderInfor.setValue("Male");
        }else{
            System.out.println("----------Nu nhe----------");
            genderInfor.setValue("Female");

        }
        addressInfor.setValue( HelloApplication.ward_name);
//        hospitalInfor.setValue( HelloApplication.hospital);
        dobInfor.setValue(LocalDate.parse(HelloApplication.dob));
    }
    @FXML
    public void submitInfor(){

        System.out.println("******Database trong class InforController******");
        // thuc hien di nhe anh em nhe anh em nhe anh em nhe anh em nhe anh em enh
        String name = nameInfor.getText();
        String id = idInfor.getText();
        String mail = mailInfor.getText();
        String pass = passInfor.getText();
        String gd;
        if( genderInfor.getValue() == "Male") gd = "M";
        else gd = "F";
        String dob = String.valueOf(dobInfor.getValue());
        String address = addressInfor.getValue();
//        String hospital = hospitalInfor.getValue();
        String ward_id = null;
//        System.out.println( name + " " + gd + " " + dob + " " + address + " " + hospital);
        String query = "select ward_id from ward where name = '" +   address +"';" ;
        ResultSet hos = null;
        try {
            hos = DB.dbExecuteQuery(query);
            if( hos.next()) {
                ward_id = hos.getString( "ward_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        System.out.println( hos_id);
        query = "update patient set ward_id='"+ward_id+"', first_name='"+name+"', last_name='"+id+"', mail='"+ mail+"', dob = '" + dob+"',gender='" + gd+"', password='"+ pass +"' where pat_id='"+ HelloApplication.id+"';";
        try {
            int a =DB.dbExecuteUpdate(query);
            if( a== 0){
                AlertBox.displayAlert("Cap nhat thong tin thanh cong");
                btnSubmitInfor.setDisable( true);
                // set het tat ca cac gia tri vao day nhe anh em
                HelloApplication.first_name = name;
                HelloApplication.last_name = id;
                HelloApplication.email = mail;
                HelloApplication.dob = dob;
                HelloApplication.gender = (String) genderInfor.getValue();
                HelloApplication.ward_name = address;
                HelloApplication.pass = pass;
                HelloApplication.name = first_name + " " + last_name;


                HelloApplication.fxmlLoader = new FXMLLoader( HelloApplication.class.getResource("UserPage.fxml"));
                HelloApplication.scene = new Scene(HelloApplication.fxmlLoader.load());
                HelloApplication.window.setTitle("Hello!");
                HelloApplication.window.setScene(HelloApplication.scene);
                HelloApplication.window.show();







            }else{
                AlertBox.displayAlert("Cap nhat khong thanh cong");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
