package library.application.staff.info;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import library.application.staff.borrow.BorrowHistorySceneController;
import library.mysql.dao.StudentDAO;
import library.mysql.dao.UserDAO;
import library.user.Student;

public class StudentInfoSceneController {

	private Student currentStudent;

	public void setCurrentStudent(Student currentUser) {
		this.currentStudent = currentUser;
        fieldStudentID.setText(currentStudent.getStudentID());
        fieldName.setText(currentStudent.getName());
        fieldClass.setText(currentStudent.getClassName());
        fieldGender.setText(currentStudent.getGenderText());
        fieldEmail.setText(currentStudent.getEmail());
        fieldPhone.setText(currentStudent.getPhone());
        fieldAddress.setText(currentStudent.getAddress());
        fieldFineStatus.setText(currentStudent.getFineStatusText());
        fieldFine.setText(Integer.toString(currentStudent.getFine()));
        fieldUsername.setText(currentStudent.getUsername());
        fieldPassword.setText(currentStudent.getPassword());
	}

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
    private TextField fieldPasswordAlt;
    
    @FXML
    private TextField fieldPhone;

    @FXML
    private TextField fieldStudentID;

    @FXML
    private TextField fieldUsername;

    
    @FXML
    private ToggleButton btnChangePassword;

    @FXML
    private ToggleButton btnEditInfo;
    
    @FXML
    private BorrowHistorySceneController borrowHistorySceneController;

    @FXML
    void btnActionChangePassword(ActionEvent event) {
    	if (!btnChangePassword.isSelected()) {
    		currentStudent.getAccount().setPassword(fieldPassword.getText());

    		try {
    			UserDAO userDAO = new UserDAO();
				userDAO.updatePassword(currentStudent.getAccount());
			} catch (Exception e) {
				System.out.println(e);
			}
    	}
    }

    
    @FXML
    void btnActionEditInfo(ActionEvent event) {
    	if (!btnEditInfo.isSelected()) {
    		currentStudent.setName(fieldName.getText());
    		currentStudent.setEmail(fieldEmail.getText());
    		currentStudent.setPhone(fieldPhone.getText());
    		currentStudent.setAddress(fieldAddress.getText());
    		
//     		currentStudent.setClassName(fieldClass.getText());    		
//    		currentStudent.setStudentID(fieldStudentID.getText());
//			currentStudent.setGender(fieldGender.getText());
//          currentStudent.set(fieldFineStatus.getText());
//          currentStudent.set(fieldFine.getText());
//  		currentStudent.getAccount().setUsername(fieldUsername.getText());

    		try {
    			StudentDAO studentDAO = new StudentDAO();
				studentDAO.updateStudent(currentStudent);
			} catch (Exception e) {
				System.out.println(e);
			}
    	}
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
        
        BooleanProperty editable = btnEditInfo.selectedProperty();
        
        fieldName.editableProperty().bind(editable);
        fieldEmail.editableProperty().bind(editable);
        fieldPhone.editableProperty().bind(editable);
        fieldAddress.editableProperty().bind(editable);
        
        fieldName.disableProperty().bind(editable.not());
        fieldEmail.disableProperty().bind(editable.not());
        fieldPhone.disableProperty().bind(editable.not());
        fieldAddress.disableProperty().bind(editable.not());
        
        fieldPasswordAlt.visibleProperty().bind(btnChangePassword.selectedProperty());
        fieldPassword.visibleProperty().bind(btnChangePassword.selectedProperty().not());
        
        // Bind textField to passwordField
        Bindings.bindBidirectional(fieldPassword.textProperty(), fieldPasswordAlt.textProperty());
        fieldPassword.setDisable(true);
    }
    
    @FXML
    private AnchorPane paneInfo;
    
    @FXML
    private AnchorPane paneBorrow;

    
    @FXML
    void btnActionBorrowHistory(ActionEvent event) {
    	paneInfo.setVisible(false);
    	paneBorrow.setVisible(true);
        borrowHistorySceneController.setThisStudentID(currentStudent.getStudentID());
        borrowHistorySceneController.refresh();
        borrowHistorySceneController.setParent(this);
    }
    
    public void btnActionReturn() {
    	paneInfo.setVisible(true);
    	paneBorrow.setVisible(false);
    }
}
