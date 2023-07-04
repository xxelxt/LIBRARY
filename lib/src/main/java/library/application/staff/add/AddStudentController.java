package library.application.staff.add;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private TextField[] textFields;

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

        textFields = new TextField[]{fieldStudentID, fieldName, fieldClass, fieldUsername, fieldPassword, fieldEmail, fieldPhoneNum, fieldFine};

        comboboxGender.setItems(genderItems);
        comboboxGender.setValue("Nữ");

        comboboxFineStatus.setItems(fineItems);
        comboboxFineStatus.setValue("Không bị phạt");

        fieldPassword.setText("hvnh1961");
    	fieldEmail.setText("@hvnh.edu.vn");
        fieldFine.setText("0");

        fieldStudentID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fieldUsername.setText(newValue);
                String emailPrefix = newValue.toLowerCase();
                String domain = "@hvnh.edu.vn";
                fieldEmail.setText(emailPrefix + domain);
            }
        });
    }

    @FXML
    void btnAddStudent(ActionEvent event) {
        if (isAnyFieldNull()) {
            highlightFields();
            Toaster.showError("Lỗi", "Vui lòng nhập đầy đủ thông tin sinh viên.");
            return;
        }

        StudentDAO studentDAO = new StudentDAO();

        String gender = comboboxGender.getValue();
        boolean isFemale = gender.equals("Nữ");

        String finestatus = comboboxFineStatus.getValue();
        boolean isFined = finestatus.equals("Bị phạt");

        try {
            int fineAmount = Integer.parseInt(fieldFine.getText());
            if (fineAmount < 0) {
                throw new IllegalArgumentException("Số tiền phạt không hợp lệ.");
            }

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
                fineAmount
            );

            highlightFields();
            clearTextField();
            Toaster.showSuccess("Thêm sinh viên thành công", "Đã thêm sinh viên vào CSDL.");

        } catch (NumberFormatException e) {
            Toaster.showError("Lỗi nhập tiền phạt", "Dữ liệu nhập vào không hợp lệ.");
            e.printStackTrace();
        } catch (SQLException e) {
            Toaster.showError("Lỗi CSDL", e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            Toaster.showError("Lỗi nhập tiền phạt", e.getMessage());
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
    	fieldStudentID.clear();
    	fieldName.clear();
    	fieldClass.clear();
    	fieldUsername.clear();
    	fieldPassword.setText("hvnh1961");
    	fieldEmail.setText("@hvnh.edu.vn");
    	fieldPhoneNum.clear();
    	fieldAddress.clear();
    	fieldFine.setText("0");
    }

}
