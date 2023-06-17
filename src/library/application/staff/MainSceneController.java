package library.application.staff;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import library.application.staff.interfac.SceneFeatureGate;
import library.application.staff.publication.BookSceneController;
import library.application.staff.publication.PrintMediaSceneController;
import library.application.staff.student.StudentSceneController;
import library.mysql.dao.UserDAO;
import library.user.User;

public class MainSceneController implements SceneFeatureGate {

    @FXML
    private Label labelWarning;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private BorderPane contentPane;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private TextField inputUser;
    
    @FXML private BookSceneController bookSceneController;
    @FXML private PrintMediaSceneController printMediaSceneController;
    @FXML private StudentSceneController studentSceneController;

    private UserDAO userDAO = new UserDAO();
    private User logUser;
    
    @FXML
    void btnLogin(ActionEvent event) {
    	logUser = userDAO.getUserfromUsername(inputUser.getText());
    	if( inputPassword.getText().equals(logUser.getPassword()) ) {
    		contentPane.setVisible(true);
    		loginPane.setVisible(false);
    		main.setWindowSize();
    		
    		Integer type = logUser.getType();
    		
    		this.setFeatureFor(type);
    		bookSceneController.setFeatureFor(type);
    		printMediaSceneController.setFeatureFor(type);
    		
    	} else {
    		labelWarning.setText("Username hoặc Password sai!\n");
    		labelWarning.setFont(new Font("Calibri", 14));
    		labelWarning.setStyle("-fx-text-fill: #110000 !important;");
    	}
    	logUser = null;
    }

    
    private Main main;
    
    @FXML
    void mainLogOut(ActionEvent event) {
		main.restart();
    }
    
    public void setMain(Main main) {
    	this.main = main;
    }
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private Tab tabBorrow;
    
    @FXML
    private Tab tabStudent;

	@Override
	public void setFeatureFor(Integer user) {
		if (user == STUDENT) {
			tabPane.getTabs().remove(tabBorrow);
			tabPane.getTabs().remove(tabStudent);
		}
	}

}
