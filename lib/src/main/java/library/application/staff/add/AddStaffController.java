package library.application.staff.add;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import library.mysql.dao.StaffDAO;

public class AddStaffController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboboxGender;

    @FXML
    private ComboBox<String> comboboxPosition;

    @FXML
    private TextField fieldAddress;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldPassword;

    @FXML
    private TextField fieldPhoneNum;

    @FXML
    private TextField fieldUsername;

    private ObservableList<String> genderItems = FXCollections.observableArrayList("Nam", "Nữ");

    private ObservableList<String> posItems = FXCollections.observableArrayList("Thủ thư", "Nhân viên");

    @FXML
    void btnAddStudent(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert comboboxGender != null : "fx:id=\"comboboxGender\" was not injected: check your FXML file 'AddStaff.fxml'.";
        assert comboboxPosition != null : "fx:id=\"comboboxPosition\" was not injected: check your FXML file 'AddStaff.fxml'.";
        assert fieldAddress != null : "fx:id=\"fieldAddress\" was not injected: check your FXML file 'AddStaff.fxml'.";
        assert fieldEmail != null : "fx:id=\"fieldEmail\" was not injected: check your FXML file 'AddStaff.fxml'.";
        assert fieldName != null : "fx:id=\"fieldName\" was not injected: check your FXML file 'AddStaff.fxml'.";
        assert fieldPassword != null : "fx:id=\"fieldPassword\" was not injected: check your FXML file 'AddStaff.fxml'.";
        assert fieldPhoneNum != null : "fx:id=\"fieldPhoneNum\" was not injected: check your FXML file 'AddStaff.fxml'.";
        assert fieldUsername != null : "fx:id=\"fieldUsername\" was not injected: check your FXML file 'AddStaff.fxml'.";

        comboboxGender.setItems(genderItems);
        comboboxGender.setValue("Nữ");

        comboboxPosition.setItems(posItems);
        comboboxPosition.setValue("Nhân viên");
    }

    @FXML
    void btnAddStaff(ActionEvent event) {
        StaffDAO staffDAO = new StaffDAO();

        String gender = comboboxGender.getValue();
        boolean isFemale = gender.equals("Nữ");

        staffDAO.addStaff(
            fieldName.getText(),
            fieldUsername.getText(),
            fieldPassword.getText(),
            isFemale,
            fieldEmail.getText(),
            fieldPhoneNum.getText(),
            fieldAddress.getText(),
            comboboxPosition.getValue()
        );

        clearTextField();
    }

    void clearTextField() {
    	fieldName.clear();
    	fieldUsername.clear();
    	fieldPassword.clear();
    	fieldEmail.clear();
    	fieldPhoneNum.clear();
    	fieldAddress.clear();
    }
}
