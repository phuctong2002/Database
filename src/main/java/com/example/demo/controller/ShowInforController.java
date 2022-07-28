package com.example.demo.controller;

import com.example.demo.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowInforController implements Initializable {
    @FXML
    public TextField nameShow;
    @FXML
    public TextField idShow;
    @FXML
    public TextField mailShow;
    @FXML
    public TextField passShow;
    @FXML
    public TextField genderShow;
    @FXML
    public TextField dobShow;
    @FXML
    public TextField addressShow;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameShow.setText(HelloApplication.name);
        idShow.setText(HelloApplication.id);
        mailShow.setText(HelloApplication.email);
        passShow.setText(HelloApplication.pass);
        genderShow.setText(HelloApplication.gender);
        dobShow.setText(HelloApplication.dob);
        addressShow.setText(HelloApplication.ward_name);
//        hospitalShow.setText(HelloApplication.hospital);
    }
}
