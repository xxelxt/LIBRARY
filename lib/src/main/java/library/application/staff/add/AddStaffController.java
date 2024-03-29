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

    private TextField[] textFields;

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

        textFields = new TextField[]{fieldName, fieldUsername, fieldPassword, fieldEmail, fieldPhoneNum};

        comboboxGender.setItems(genderItems);
        comboboxGender.setValue("Nữ");

        comboboxPosition.setItems(posItems);
        comboboxPosition.setValue("Nhân viên");
    }

    @FXML
    void btnAddStaff(ActionEvent event) {
    	if (isAnyFieldNull()) {
    		highlightFields();
            Toaster.showError("Lỗi", "Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        StaffDAO staffDAO = new StaffDAO();

        String gender = comboboxGender.getValue();
        boolean isFemale = gender.equals("Nữ");

        try {
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

        	highlightFields();
	    	clearTextField();
	    	Toaster.showSuccess("Thêm nhân viên thành công", "Đã thêm nhân viên vào CSDL.");

		} catch (SQLException e) {
			Toaster.showError("Lỗi CSDL", e.getMessage());
			e.printStackTrace();
		}
    }

    private boolean isAnyFieldNull() {
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void highlightFields() {
    	String highlight = "-fx-border-color: red; -fx-border-radius: 5px;";
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                textField.setStyle(highlight);
            } else {
                textField.setStyle("");
            }
        }
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
