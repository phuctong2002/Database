package com.example.demo.controller.doctor;

import com.example.demo.HelloApplication;
import com.example.demo.model.Patient_Inject;
import com.example.demo.utils.AlertBox;
import com.example.demo.utils.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.demo.utils.DB.dbExecuteUpdate;

public class ImportVaccinationInfController implements Initializable {
    @FXML
    public TableColumn<Patient_Inject, String> address;

    @FXML
    public TableColumn<Patient_Inject, String> dob;

    @FXML
    public TableColumn<Patient_Inject, String> id;

    @FXML
    public TextField id_Textfield;
    @FXML
    public TextField Name_text;
    @FXML
    public TextField dob_text;
    @FXML
    public TextField address_text;
    @FXML
    public TableColumn<Patient_Inject, String> NamePat;

    @FXML
    public TableView<Patient_Inject> table;
    @FXML
    public ComboBox<String> vaccine;

    @FXML
    public void Cancel(ActionEvent event) throws IOException {
        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Doc/doctor.fxml"));
        HelloApplication.scene = new Scene(HelloApplication.fxmlLoader.load());
        HelloApplication.window.setTitle("Doctor");
        HelloApplication.window.setScene(HelloApplication.scene);
        HelloApplication.window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String query = "select name from vaccine";
        ResultSet rs = null;
        try {
            rs = DB.dbExecuteQuery(query);
            while( rs.next() ) {
                String name = rs.getString("name");
                vaccine.getItems().add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        update();
    }
    public void update(){
        try {
            ObservableList<Patient_Inject> view_list = FXCollections.observableArrayList();
            ResultSet list_Patient = get_pat();
            Patient_Inject someone;
            while (list_Patient.next()) {
                someone = new Patient_Inject();
                someone.setName(list_Patient.getString("name"));
                someone.setID_patient(list_Patient.getString("id"));
                someone.setDob(list_Patient.getString("dob"));
                someone.setAddress(list_Patient.getString("address"));
//                someone.setName_vaccine(get_vaccine(list_Patient.getString("id")));
//                someone.setNthInjection(list_Patient.getInt("nthinjection"));
                view_list.add(someone);
            }

            NamePat.setCellValueFactory(new PropertyValueFactory<Patient_Inject, String>("Name"));
            id.setCellValueFactory(new PropertyValueFactory<Patient_Inject, String>("ID_patient"));
            dob.setCellValueFactory(new PropertyValueFactory<Patient_Inject, String>("dob"));
            address.setCellValueFactory(new PropertyValueFactory<Patient_Inject, String>("address"));
//            NthInjection.setCellValueFactory(new PropertyValueFactory<Patient_Inject, String>("NthInjection"));
//            NameVaccine.setCellValueFactory(new PropertyValueFactory<Patient_Inject, String>("name_vaccine"));

            table.setItems(view_list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public String get_vaccine(String id) throws SQLException {
        String quer = "SELECT name FROM injection WHERE pat_id = '"+id+"' ORDER BY inj_time DESC LIMIT 1;";
        ResultSet a = null;
        try {
            a = DB.dbExecuteQuery(quer);
            if(a.next()){
//                System.out.println(a.getString("name"));
                quer = a.getString("name");
            }
            else {
                System.out.println("jnfas sai roi nhe anh em");
                quer = "";
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error in StaticController : " + e.getMessage());

        }
//        System.out.println("---");
//        System.out.println(a.getString("name"));
//        System.out.println("---");
//        return a.getString("name");
        return quer;
    }
    private ResultSet get_pat() throws SQLException, ClassNotFoundException {
//        String queryStr = "SELECT  p.pat_id as id, first_name ||' '|| last_name as name, dob, address FROM patient p, injection_registration_form f where p.pat_id = f.pat_id and injection_time = current_date;" ;
        String query = "select patient.pat_id as id, first_name || ' ' || last_name as name, dob, ward.name as address from patient inner join ( select * from injection_registration_form where hos_id = '"+HelloApplication.hos_id+"' and injection_time = current_date  and pat_id not in ( select pat_id from injection where inj_time = current_date and hos_id = '"+HelloApplication.hos_id+"')) as tmp on patient.pat_id = tmp.pat_id left join ward on patient.ward_id = ward.ward_id ;";

        ResultSet a = null;
        try {
            a = DB.dbExecuteQuery(query);
//            if(a.next()){
//                System.out.println(a.getString("nthinjection"));
//            }
//            else {
//                System.out.println("jnfas");
//            }
        } catch (SQLException e) {
            System.out.println("Error in StaticController : " + e.getMessage());

        }
        System.out.println("---");
        System.out.println(a);
        System.out.println("---");
        return a;
    }

    private int index = -1;

    @FXML
    public void getSelected(MouseEvent mouseEvent) {
        index = table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        id_Textfield.setText(id.getCellData(index));
        Name_text.setText(NamePat.getCellData(index));
        dob_text.setText(dob.getCellData(index));
        address_text.setText(address.getCellData(index));

    }

    @FXML
    public void addInfor(ActionEvent event) throws SQLException, ClassNotFoundException {
        import_data();
        update();
        id_Textfield.setText("");
        Name_text.setText("");
        dob_text.setText("");
        address_text.setText("");
        vaccine.setValue("");
    }
    private void import_data() throws SQLException, ClassNotFoundException {
        String x = id_Textfield.getText();
//        String b = NameVaccineText.getText();
        String b = vaccine.getValue();
        String queryStr = "INSERT INTO injection (pat_id, name, doc_id) VALUES ('"+x+"','"+b+"','"+HelloApplication.id+"');";

        System.out.println(HelloApplication.id);
        System.out.println(b);

        try {
            int a = dbExecuteUpdate(queryStr);
            if(a==1) AlertBox.displayAlert("Error");
            else AlertBox.displayAlert("Da Them");
        } catch (SQLException e) {
            System.out.println("Error in StaticController : " + e.getMessage());

        }
    }
}
