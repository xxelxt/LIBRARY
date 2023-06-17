package library.application.staff.add;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddStudentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> comboboxFineStatus;

    @FXML
    private ComboBox<?> comboboxGender;

    @FXML
    private TextField fieldAddress;

    @FXML
    private TextField fieldClass;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldFine;

    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldPassword;

    @FXML
    private TextField fieldPhoneNum;

    @FXML
    private TextField fieldStudentID;

    @FXML
    private TextField fieldUsername;

    @FXML
    void btnAddStudent(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert comboboxFineStatus != null : "fx:id=\"comboboxFineStatus\" was not injected: check your FXML file 'AddStudent.fxml'.";
        assert comboboxGender != null : "fx:id=\"comboboxGender\" was not injected: check your FXML file 'AddStudent.fxml'.";
        assert fieldAddress != null : "fx:id=\"fieldAddress\" was not injected: check your FXML file 'AddStudent.fxml'.";
        assert fieldClass != null : "fx:id=\"fieldClass\" was not injected: check your FXML file 'AddStudent.fxml'.";
        assert fieldEmail != null : "fx:id=\"fieldEmail\" was not injected: check your FXML file 'AddStudent.fxml'.";
        assert fieldFine != null : "fx:id=\"fieldFine\" was not injected: check your FXML file 'AddStudent.fxml'.";
        assert fieldName != null : "fx:id=\"fieldName\" was not injected: check your FXML file 'AddStudent.fxml'.";
        assert fieldPassword != null : "fx:id=\"fieldPassword\" was not injected: check your FXML file 'AddStudent.fxml'.";
        assert fieldPhoneNum != null : "fx:id=\"fieldPhoneNum\" was not injected: check your FXML file 'AddStudent.fxml'.";
        assert fieldStudentID != null : "fx:id=\"fieldStudentID\" was not injected: check your FXML file 'AddStudent.fxml'.";
        assert fieldUsername != null : "fx:id=\"fieldUsername\" was not injected: check your FXML file 'AddStudent.fxml'.";

    }

}
