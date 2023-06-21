package library.application.staff.info;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StaffInfoSceneController /*implements Initializable*/ {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fieldAddress;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldGender;

    @FXML
    private TextField fieldName;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextField fieldPhone;

    @FXML
    private TextField fieldPosition;

    @FXML
    private TextField fieldStaffID;

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
        assert fieldAddress != null : "fx:id=\"fieldAddress\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldEmail != null : "fx:id=\"fieldEmail\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldGender != null : "fx:id=\"fieldGender\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldName != null : "fx:id=\"fieldName\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldPassword != null : "fx:id=\"fieldPassword\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldPhone != null : "fx:id=\"fieldPhone\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldPosition != null : "fx:id=\"fieldPosition\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldStaffID != null : "fx:id=\"fieldStudentID\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldUsername != null : "fx:id=\"fieldUsername\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";

    }
}
