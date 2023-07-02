package library.application.staff.add;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import library.application.util.Toaster;
import library.mysql.dao.StudentDAO;

public class AddStudentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboboxFineStatus;

    @FXML
    private ComboBox<String> comboboxGender;

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

    private ObservableList<String> genderItems = FXCollections.observableArrayList("Nam", "Nữ");

    private ObservableList<String> fineItems = FXCollections.observableArrayList("Không bị phạt", "Bị phạt");

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

        comboboxGender.setItems(genderItems);
        comboboxGender.setValue("Nữ");

        comboboxFineStatus.setItems(fineItems);
        comboboxFineStatus.setValue("Không bị phạt");

        fieldFine.setText("0");
    }

    @FXML
    void btnAddStudent(ActionEvent event) {
        StudentDAO studentDAO = new StudentDAO();

        String gender = comboboxGender.getValue();
        boolean isFemale = gender.equals("Nữ");

        String finestatus = comboboxFineStatus.getValue();
        boolean isFined = finestatus.equals("Bị phạt");

        try {
			studentDAO.addStudent(
			    fieldStudentID.getText(),
			    fieldName.getText(),
			    fieldClass.getText(),
			    fieldUsername.getText(),
			    fieldPassword.getText(),
			    isFemale,
			    fieldEmail.getText(),
			    fieldPhoneNum.getText(),
			    fieldAddress.getText(),
			    isFined,
			    Integer.parseInt(fieldFine.getText())
			);
		} catch (NumberFormatException e) { // Added Toast
			Toaster.showError("Input ERROR", e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) { // Added Toast
			Toaster.showError("SQL ERROR", e.getMessage());
			e.printStackTrace();
		}

        clearTextField();
    }


    void clearTextField() {
    	fieldStudentID.clear();
    	fieldName.clear();
    	fieldClass.clear();
    	fieldUsername.clear();
    	fieldPassword.clear();
    	fieldEmail.clear();
    	fieldPhoneNum.clear();
    	fieldAddress.clear();
    	fieldFine.setText("0");
    }

}
