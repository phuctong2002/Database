package com.example.demo.controller.admin;

import com.example.demo.model.User;
import com.example.demo.utils.AlertBox;
import com.example.demo.utils.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListPatientController implements Initializable {
    @FXML
    public ComboBox<String> addressPatient;
    @FXML
    public TableView<User> tbPatient1;
    @FXML
    public TableView<User> tbPatient2;
    @FXML
    public TableColumn<User, String > nameCol1;
    @FXML
    public TableColumn<User, String > emailCol1;
    @FXML
    public TableColumn<User,String> idCol1;
    @FXML
    public TableColumn<User, String> addressCol1;
    @FXML
    public TableColumn<User, String> genderCol1;
    @FXML
    public TableColumn<User, Integer> injectionCol1;
    @FXML
    public TableColumn<User, String > nameCol2;
    @FXML
    public TableColumn<User, String > emailCol2;
    @FXML
    public TableColumn<User,String> idCol2;
    @FXML
    public TableColumn<User, String> addressCol2;
    @FXML
    public TableColumn<User, String> genderCol2;
    @FXML
    public TableColumn<User, Integer> injectionCol2;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] address = {"Bach Khoa", "Bach Dang", "Bach Mai", "Cau Den", "Dong Mac", "Dong Nhan", "Dong Tam",
                "Le Dai Hanh", "Minh Khai", "Nguyen Du", "Pham Dinh Ho", "Pho Hue", "Quynh Loi", "Quynh Mai", "Thanh Luong", "Thanh Nhan", "Truong Dinh", "Vinh Tuy"};
        addressPatient.getItems().addAll(address);

        // list ra hai bang nay voi khoai chu mia no nua
        // bang 1 da duoc  tiem nhe
        try {
            String queryStr1 = "select first_name || ' ' || last_name as name, pat_id, gender, mail, ward.name as address, number_injection from patient left join ward on patient.ward_id = ward.ward_id where number_injection != 0;";
            ObservableList<User> userList = FXCollections.observableArrayList();
            ResultSet userPatient = getDeclare(queryStr1);
            User abc;
            while(userPatient.next()){
                abc = new User();
                abc.setName( userPatient.getString("name"));
                abc.setId( userPatient.getString("pat_id"));
                abc.setAddress(userPatient.getString("address"));
                abc.setGender( userPatient.getString("gender"));
                abc.setNumberInjection(userPatient.getInt("number_injection"));
                abc.setEmail(userPatient.getString("mail"));
                userList.add(abc);
//                System.out.println( userPatient.getString("name") + userPatient.getString("name") + userPatient.getString("pat_id") + userPatient.getString("address") );

            }
            nameCol1.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            idCol1.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
            addressCol1.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
            genderCol1.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
            injectionCol1.setCellValueFactory(new PropertyValueFactory<User, Integer>("numberInjection"));
            emailCol1.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            tbPatient1.setItems(userList);





        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



        // bang 2 la chua duoc tiem mui nao

        try {
            String queryStr1 = "select first_name || ' ' || last_name as name, pat_id, gender, mail, ward.name as address, number_injection from patient  left join ward on patient.ward_id = ward.ward_id where number_injection = 0;";
            ObservableList<User> userList = FXCollections.observableArrayList();
            ResultSet userPatient = getDeclare(queryStr1);
            User abc;
            while(userPatient.next()){
                abc = new User();
                abc.setName( userPatient.getString("name"));
                abc.setId( userPatient.getString("pat_id"));
                abc.setAddress(userPatient.getString("address"));
                abc.setGender( userPatient.getString("gender"));
                abc.setNumberInjection(userPatient.getInt("number_injection"));
                abc.setEmail(userPatient.getString("mail"));
                userList.add(abc);
//                System.out.println( userPatient.getString("name") + userPatient.getString("name") + userPatient.getString("pat_id") + userPatient.getString("address") );

            }
            nameCol2.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            idCol2.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
            addressCol2.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
            genderCol2.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
            injectionCol2.setCellValueFactory(new PropertyValueFactory<User, Integer>("numberInjection"));
            emailCol2.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            tbPatient2.setItems(userList);





        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }




    @FXML
    public void showPatient(){
        if( addressPatient.getValue() == null ){
            AlertBox.displayAlert("Chon dia diem");
        }else{
            String addr = addressPatient.getValue();
            // find ward_id nhe

            try {
                String queryStr1 = "select first_name || ' ' || last_name as name, pat_id, gender, mail, ward.name as address, number_injection from patient left join ward on patient.ward_id = ward.ward_id where number_injection != 0 and ward.name = '"+ addr + "';";
                ObservableList<User> userList = FXCollections.observableArrayList();
                ResultSet userPatient = getDeclare(queryStr1);
                User abc;
                while(userPatient.next()){
                    abc = new User();
                    abc.setName( userPatient.getString("name"));
                    abc.setId( userPatient.getString("pat_id"));
                    abc.setAddress(userPatient.getString("address"));
                    abc.setGender( userPatient.getString("gender"));
                    abc.setNumberInjection(userPatient.getInt("number_injection"));
                    abc.setEmail(userPatient.getString("mail"));
                    userList.add(abc);
//                System.out.println( userPatient.getString("name") + userPatient.getString("name") + userPatient.getString("pat_id") + userPatient.getString("address") );

                }
                nameCol1.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
                idCol1.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
                addressCol1.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
                genderCol1.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
                injectionCol1.setCellValueFactory(new PropertyValueFactory<User, Integer>("numberInjection"));
                emailCol1.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
                tbPatient1.setItems(userList);





            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }



            // bang 2 la chua duoc tiem mui nao

            try {
                ObservableList<User> userList = FXCollections.observableArrayList();
                String queryStr1 = "select first_name || ' ' || last_name as name, pat_id, gender, mail, ward.name as address, number_injection from patient left join ward on patient.ward_id = ward.ward_id where number_injection = 0 and ward.name = '"+ addr + "';";

                ResultSet userPatient = getDeclare(queryStr1);
                User abc;
                while(userPatient.next()){
                    abc = new User();
                    abc.setName( userPatient.getString("name"));
                    abc.setId( userPatient.getString("pat_id"));
                    abc.setAddress(userPatient.getString("address"));
                    abc.setGender( userPatient.getString("gender"));
                    abc.setNumberInjection(userPatient.getInt("number_injection"));
                    abc.setEmail(userPatient.getString("mail"));
                    userList.add(abc);
//                System.out.println( userPatient.getString("name") + userPatient.getString("name") + userPatient.getString("pat_id") + userPatient.getString("address") );

                }
                nameCol2.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
                idCol2.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
                addressCol2.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
                genderCol2.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
                injectionCol2.setCellValueFactory(new PropertyValueFactory<User, Integer>("numberInjection"));
                emailCol2.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
                tbPatient2.setItems(userList);





            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    public void showAllPatient(){
        try {
//            String queryStr1 = "select first_name || ' ' || last_name as name, pat_id, gender, mail, address, number_injection from patient where number_injection != 0;";
            String queryStr1 = "select first_name || ' ' || last_name as name, pat_id, gender, mail, ward.name as address, number_injection from patient  left join ward on patient.ward_id = ward.ward_id where number_injection != 0;";

            ObservableList<User> userList = FXCollections.observableArrayList();
            ResultSet userPatient = getDeclare(queryStr1);
            User abc;
            while(userPatient.next()){
                abc = new User();
                abc.setName( userPatient.getString("name"));
                abc.setId( userPatient.getString("pat_id"));
                abc.setAddress(userPatient.getString("address"));
                abc.setGender( userPatient.getString("gender"));
                abc.setNumberInjection(userPatient.getInt("number_injection"));
                abc.setEmail(userPatient.getString("mail"));
                userList.add(abc);
//                System.out.println( userPatient.getString("name") + userPatient.getString("name") + userPatient.getString("pat_id") + userPatient.getString("address") );

            }
            nameCol1.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            idCol1.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
            addressCol1.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
            genderCol1.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
            injectionCol1.setCellValueFactory(new PropertyValueFactory<User, Integer>("numberInjection"));
            emailCol1.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            tbPatient1.setItems(userList);





        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



        // bang 2 la chua duoc tiem mui nao

        try {
//            String queryStr1 = "select first_name || ' ' || last_name as name, pat_id, gender, mail, address, number_injection from patient where number_injection = 0;";
            String queryStr1 = "select first_name || ' ' || last_name as name, pat_id, gender, mail, ward.name as address, number_injection from patient  left join ward on patient.ward_id = ward.ward_id where number_injection = 0;";

            ObservableList<User> userList = FXCollections.observableArrayList();
            ResultSet userPatient = getDeclare(queryStr1);
            User abc;
            while(userPatient.next()){
                abc = new User();
                abc.setName( userPatient.getString("name"));
                abc.setId( userPatient.getString("pat_id"));
                abc.setAddress(userPatient.getString("address"));
                abc.setGender( userPatient.getString("gender"));
                abc.setNumberInjection(userPatient.getInt("number_injection"));
                abc.setEmail(userPatient.getString("mail"));
                userList.add(abc);
//                System.out.println( userPatient.getString("name") + userPatient.getString("name") + userPatient.getString("pat_id") + userPatient.getString("address") );

            }
            nameCol2.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            idCol2.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
            addressCol2.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
            genderCol2.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
            injectionCol2.setCellValueFactory(new PropertyValueFactory<User, Integer>("numberInjection"));
            emailCol2.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            tbPatient2.setItems(userList);





        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private ResultSet getDeclare( String queryStr) throws SQLException, ClassNotFoundException{
        ResultSet a = null;
        try{
            a = DB.dbExecuteQuery(queryStr);
        }catch (SQLException  e){
            System.out.println("Error in StaticController : " + e.getMessage());

        }
        return a;
    }
}
