package library.application.staff.info;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StudentInfoSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fieldAddress;

    @FXML
    private TextField fieldClass;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldFine;

    @FXML
    private TextField fieldFineStatus;

    @FXML
    private TextField fieldGender;

    @FXML
    private TextField fieldName;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextField fieldPhone;

    @FXML
    private TextField fieldStudentID;

    @FXML
    private TextField fieldUsername;

    @FXML
    void btnActionChangePassword(ActionEvent event) {

    }

    @FXML
    void btnActionEditInfo(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert fieldAddress != null : "fx:id=\"fieldAddress\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldClass != null : "fx:id=\"fieldClass\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldEmail != null : "fx:id=\"fieldEmail\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldFine != null : "fx:id=\"fieldFine\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldFineStatus != null : "fx:id=\"fieldFineStatus\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldGender != null : "fx:id=\"fieldGender\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldName != null : "fx:id=\"fieldName\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldPassword != null : "fx:id=\"fieldPassword\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldPhone != null : "fx:id=\"fieldPhone\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldStudentID != null : "fx:id=\"fieldStudentID\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldUsername != null : "fx:id=\"fieldUsername\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";

    }

}
