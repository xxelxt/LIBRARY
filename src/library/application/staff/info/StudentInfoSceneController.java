package library.application.staff.info;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import library.application.staff.borrow.BorrowHistorySceneController;
import library.mysql.dao.StudentDAO;
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

        setDisableAllAndPassword(true, true);
	}

	private void setDisableAllAndPassword(boolean all, boolean password) {
        fieldStudentID.setDisable(true);
        fieldName.setDisable(all);
        fieldClass.setDisable(true);
        fieldGender.setDisable(true);	// Deal with later
        fieldEmail.setDisable(all);
        fieldPhone.setDisable(all);
        fieldAddress.setDisable(all);
        fieldFineStatus.setDisable(true);
        fieldFine.setDisable(true);
        fieldUsername.setDisable(true);

        fieldPassword.setDisable(password);

        fieldStudentID.setEditable(false);
        fieldName.setEditable(!all);
        fieldClass.setEditable(false);
        fieldGender.setEditable(false);
        fieldEmail.setEditable(!all);
        fieldPhone.setEditable(!all);
        fieldAddress.setEditable(!all);
        fieldFineStatus.setEditable(false);
        fieldFine.setEditable(false);
        fieldUsername.setEditable(false);

        fieldPassword.setEditable(!password);
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
    private TextField fieldPhone;

    @FXML
    private TextField fieldStudentID;

    @FXML
    private TextField fieldUsername;

    @FXML
    void btnActionChangePassword(ActionEvent event) {

    	setDisableAllAndPassword(true, false);
    }

    @FXML
    private ToggleButton btnEditInfo;
    
    @FXML
    private BorrowHistorySceneController borrowHistorySceneController;

    @FXML
    void btnActionEditInfo(ActionEvent event) {
    	if (btnEditInfo.isSelected()) {
    		setDisableAllAndPassword(false, true);
    	} else {
    		setDisableAllAndPassword(true, true);
//    		currentStudent.setStudentID(fieldStudentID.getText());
    		currentStudent.setName(fieldName.getText());
    		currentStudent.setClassName(fieldClass.getText());
            // currentStudent.setGender(fieldGender.getText());
    		currentStudent.setEmail(fieldEmail.getText());
    		currentStudent.setPhone(fieldPhone.getText());
    		currentStudent.setAddress(fieldAddress.getText());
//            currentStudent.set(fieldFineStatus.getText());
//            currentStudent.set(fieldFine.getText());
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
