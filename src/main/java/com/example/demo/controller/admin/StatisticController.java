package com.example.demo.controller.admin;

import com.example.demo.model.User;
import com.example.demo.utils.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StatisticController implements Initializable {
    @FXML
    public TableView<User> tbStatistic;
    @FXML
    public TableColumn<User, String> nameStatistic;
    @FXML
    public TableColumn<User, String> idStatistic;
    @FXML
    public TableColumn<User, String> addressStatistic;
    @FXML
    public TableColumn<User, Integer> statistic_1;
    @FXML
    public TableColumn<User, Integer> statistic_2;
    @FXML
    public TableColumn<User, Integer> statistic_3;
    @FXML
    public TableColumn<User, Integer> statistic_4;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // thuc hien voi co so du lieu da
        try {
            ObservableList<User> userList = FXCollections.observableArrayList();
            ResultSet userPatient = getDeclare();
            User abc;
            while(userPatient.next()){
                abc = new User();
                abc.setName( userPatient.getString("name"));
                abc.setId( userPatient.getString("pat_id"));
                abc.setAddress(userPatient.getString("address"));
                abc.setA1(userPatient.getInt("symptom_14_days"));
                abc.setA2(userPatient.getInt("contact"));
                abc.setA3(userPatient.getInt("contact_foreign"));
                abc.setA4(userPatient.getInt("contact_symptom"));
                userList.add(abc);
//                System.out.println( userPatient.getString("name") + userPatient.getString("name") + userPatient.getString("pat_id") + userPatient.getString("address") );

            }
            nameStatistic.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            idStatistic.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
            addressStatistic.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
            statistic_1.setCellValueFactory(new PropertyValueFactory<User, Integer>("a1"));
            statistic_2.setCellValueFactory(new PropertyValueFactory<User, Integer>("a2"));
            statistic_3.setCellValueFactory(new PropertyValueFactory<User, Integer>("a3"));
            statistic_4.setCellValueFactory(new PropertyValueFactory<User, Integer>("a4"));
            tbStatistic.setItems(userList);





        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    private ResultSet getDeclare() throws SQLException, ClassNotFoundException{
//        String queryStr = "SELECT    first_name || ' ' || last_name as name, d.pat_id, ward.name as address, symptom_14_days, contact, contact_foreign, contact_symptom FROM (( SELECT * FROM declaration WHERE (date(time_declare) - CURRENT_DATE )::INTEGER  <= 14)  AS d LEFT JOIN patient ON d.pat_id = patient.pat_id ) as c inner join ward on ward.ward_id = c.ward_id;";
        String queryStr = "SELECT first_name || ' ' || last_name as name, patient.pat_id,ward.name as address, symptom_14_days, contact, contact_foreign, contact_symptom FROM (SELECT * FROM declaration WHERE (date(time_declare) - CURRENT_DATE)::INTEGER <= 14) AS d LEFT JOIN patient ON d.pat_id = patient.pat_id inner join ward on ward.ward_id = patient.ward_id;";
        ResultSet a = null;
        try{
            a = DB.dbExecuteQuery(queryStr);
        }catch (SQLException  e){
            System.out.println("Error in StaticController : " + e.getMessage());

        }
        return a;
    }

}
