package com.example.demo.controller.doctor;

import com.example.demo.HelloApplication;
import com.example.demo.model.Patient;
import com.example.demo.model.User;
import com.example.demo.utils.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewScheduleController implements Initializable {
    @FXML
    public TableColumn<Patient, String> DOBpat;

    @FXML
    public TableColumn<Patient, String> DateInjection;

    @FXML
    public TableColumn<Patient, String> Idpatient;

    @FXML
    public TableColumn<Patient, String> NamePat;

    @FXML
    public TableView<Patient> tableSchedule;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            ObservableList<Patient> view_list = FXCollections.observableArrayList();
            ResultSet view_Patient = getInfor_pat();
            Patient someone;
            while(view_Patient.next()){
                someone = new Patient();
                someone.setName( view_Patient.getString("name"));
                someone.setId( view_Patient.getString("id"));
                someone.setDob(view_Patient.getString("dob"));
                someone.setDate(view_Patient.getString("injection_time"));
                view_list.add(someone);
            }
            NamePat.setCellValueFactory(new PropertyValueFactory<Patient, String>("Name"));
            DateInjection.setCellValueFactory(new PropertyValueFactory<Patient, String>("date"));
            DOBpat.setCellValueFactory(new PropertyValueFactory<Patient, String>("dob"));
            Idpatient.setCellValueFactory(new PropertyValueFactory<Patient, String>("id"));
            tableSchedule.setItems(view_list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public ResultSet getInfor_pat() throws SQLException, ClassNotFoundException{
//        String queryStr = "SELECT p.pat_id as id, first_name||' '||last_name AS name, dob, injection_time FROM patient p, injection_registration_form f WHERE p.pat_id = f.pat_id ;";
        String query = "select patient.pat_id as id, first_name || ' ' || last_name as name, dob, injection_time from patient inner join ( select * from injection_registration_form where hos_id = '"+HelloApplication.hos_id+"' and injection_time = current_date) as tmp on patient.pat_id = tmp.pat_id ; ";

        ResultSet a = null;
        try{
            a = DB.dbExecuteQuery(query);
        }catch (SQLException  e){
            System.out.println("Error in StaticController : " + e.getMessage());

        }
        return a;
    }

    @FXML
    public void Back(ActionEvent event) throws IOException {
        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Doc/doctor.fxml"));
        HelloApplication.scene = new Scene(HelloApplication.fxmlLoader.load());
        HelloApplication.window.setTitle("Doctor");
        HelloApplication.window.setScene(HelloApplication.scene);
        HelloApplication.window.show();
    }

}
