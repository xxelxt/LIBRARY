package library.application.staff.info;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import library.mysql.dao.StaffDAO;
import library.user.Staff;

public class StaffInfoSceneController /*implements Initializable*/ {

	private Staff currentStaff;

	public void setCurrentStaff(Staff currentUser) {
		this.currentStaff = currentUser;
        fieldStaffID.setText(Integer.toString(currentStaff.getStaffID()));
        fieldName.setText(currentStaff.getName());
        fieldGender.setText(currentStaff.getGenderText());
        fieldEmail.setText(currentStaff.getEmail());
        fieldPhone.setText(currentStaff.getPhone());
        fieldAddress.setText(currentStaff.getAddress());
        fieldPosition.setText(currentStaff.getPosition());
        fieldUsername.setText(currentStaff.getUsername());
        fieldPassword.setText(currentStaff.getPassword());

        setDisableAllAndPassword(true, true);
	}
	private void setDisableAllAndPassword(boolean all, boolean password) {
        fieldStaffID.setDisable(true);
        fieldName.setDisable(all);
        fieldGender.setDisable(true);	// Deal with later
        fieldEmail.setDisable(all);
        fieldPhone.setDisable(all);
        fieldAddress.setDisable(all);
        fieldPosition.setDisable(all);
        fieldUsername.setDisable(true);

        fieldPassword.setDisable(password);

        fieldStaffID.setEditable(false);
        fieldName.setEditable(!all);
        fieldGender.setEditable(false);
        fieldEmail.setEditable(!all);
        fieldPhone.setEditable(!all);
        fieldAddress.setEditable(!all);
        fieldPosition.setEditable(!all);
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
    private ToggleButton btnEditInfo;

    @FXML
    void btnActionEditInfo(ActionEvent event) {
    	if (btnEditInfo.isSelected()) {
    		setDisableAllAndPassword(false, true);
    	} else {
    		setDisableAllAndPassword(true, true);
    		currentStaff.setName(fieldName.getText());
    		currentStaff.setEmail(fieldEmail.getText());
    		currentStaff.setPhone(fieldPhone.getText());
    		currentStaff.setAddress(fieldAddress.getText());

    		try {
    			StaffDAO staffDAO = new StaffDAO();
				staffDAO.updateStaff(currentStaff);
			} catch (Exception e) {
				System.out.println(e);
			}
    	}
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
        assert fieldStaffID != null : "fx:id=\"fieldStaffID\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldUsername != null : "fx:id=\"fieldUsername\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";

    }
}
