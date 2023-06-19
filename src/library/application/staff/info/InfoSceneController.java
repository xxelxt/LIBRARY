package library.application.staff.info;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import library.user.User;

public class InfoSceneController {

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
    private TextField fieldUsername;

    private User currentUser;

    public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	@FXML
    void btnActionChangePassword(ActionEvent event) {

    }

    @FXML
    void btnActionEditInfo(ActionEvent event) {

    }

}
