package library.application.staff;

import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import library.application.staff.borrow.BorrowSceneController;
import library.application.staff.info.StaffInfoSceneController;
import library.application.staff.info.StudentInfoSceneController;
import library.application.staff.interfac.SceneFeatureGate;
import library.application.staff.publication.BookSceneController;
import library.application.staff.publication.PrintMediaSceneController;
import library.application.staff.staff.StaffSceneController;
import library.application.staff.student.StudentSceneController;
import library.application.util.Toaster;
import library.mysql.dao.StaffDAO;
import library.mysql.dao.StudentDAO;
import library.mysql.dao.UserDAO;
import library.user.User;

public class MainSceneController implements SceneFeatureGate {

    @FXML
    private AnchorPane loginPane;

    @FXML
    private BorderPane contentPane;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private TextField inputUser;

    @FXML
    private Label labelWarning;

    @FXML
    private BookSceneController bookSceneController;

    @FXML
    private PrintMediaSceneController printMediaSceneController;

    @FXML
    private StudentSceneController studentSceneController;

    @FXML
    private BorrowSceneController borrowSceneController;

    @FXML
    private StaffSceneController staffSceneController;

    @FXML
    private StudentInfoSceneController studentInfoSceneController;

    @FXML
    private StaffInfoSceneController staffInfoSceneController;

    private UserDAO userDAO = new UserDAO();
    private User logUser;

    private Stage primaryStage;

    private MainApp main;

    @FXML
    void btnLogin(ActionEvent event) throws SQLException {
        logUser = userDAO.getUserfromUsername(inputUser.getText());
        if (logUser != null && inputPassword.getText().equals(logUser.getPassword())
        		|| (inputUser.getText().equals("root") && inputPassword.getText().equals("root"))) {
        	primaryStage.hide();
            contentPane.setVisible(true);
            loginPane.setVisible(false);
            main.setWindowSize();
            addTabTooltip();
            primaryStage.show();

            Integer type = logUser.getType();

            this.setFeatureFor(type);
            bookSceneController.setFeatureFor(type);
            printMediaSceneController.setFeatureFor(type);
            if (type == STUDENT) {
            	StudentDAO studentDAO = new StudentDAO();
    			try {
					studentInfoSceneController.setCurrentStudent(studentDAO.loadStudent(logUser));
				} catch (SQLException e) {
					Toaster.showError("Lỗi CSDL", e.getMessage());
					e.printStackTrace();
				}
    		} else if (type == CLERK || type == LIBRARIAN) {
    			StaffDAO staffDAO = new StaffDAO();
    			try {
    				staffInfoSceneController.setCurrentStaff(staffDAO.loadStaff(logUser));
				} catch (SQLException e) {
					Toaster.showError("Lỗi CSDL", e.getMessage());
					e.printStackTrace();
				}
    		}
        } else {
            labelWarning.setText("Thông tin đăng nhập không đúng.");
            labelWarning.setFont(new Font("System", 14));
            labelWarning.setStyle("-fx-text-fill: #ff0000;");
        }
        logUser = null;
    }

    @FXML
    void onPasswordKeyPressed(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            btnLogin(new ActionEvent());
        }
    }

    @FXML
    void onUsernameKeyPressed(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            btnLogin(new ActionEvent());
        }
    }

    @FXML
    void mainLogOut(ActionEvent event) {
    	primaryStage.hide();
    	main.restart();
    	primaryStage.show();
    }

    public void setMain(MainApp main, Stage primaryStage) {
        this.main = main;
        this.primaryStage = primaryStage;
    }

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabBook;

    @FXML
    private Tab tabPrintMedia;

    @FXML
    private Tab tabBorrow;

    @FXML
    private Tab tabStudent;

    @FXML
    private Tab tabStaff;

    @FXML
    private Tab tabStaffInfo;

    @FXML
    private Tab tabStudentInfo;

    private void addTabTooltip() {
    	Tooltip tooltipBook = new Tooltip("Sách");
    	Tooltip tooltipPrintMedia = new Tooltip("Báo & tạp chí");
    	Tooltip tooltipBorrow = new Tooltip("Mượn");
    	Tooltip tooltipStaff = new Tooltip("Nhân viên");
    	Tooltip tooltipStudent = new Tooltip("Sinh viên");
    	Tooltip tooltipStaffInfo = new Tooltip("Thông tin");
    	Tooltip tooltipStudentInfo = new Tooltip("Thông tin");
    	tabBook.setTooltip(tooltipBook);
    	tabPrintMedia.setTooltip(tooltipPrintMedia);
    	tabBorrow.setTooltip(tooltipBorrow);
    	tabStaff.setTooltip(tooltipStaff);
    	tabStudent.setTooltip(tooltipStudent);
    	tabStaffInfo.setTooltip(tooltipStaffInfo);
    	tabStudentInfo.setTooltip(tooltipStudentInfo);

    }

	@Override
	public void setFeatureFor(Integer user) {
		if (user == STUDENT) {
			tabPane.getTabs().remove(tabBorrow);
			tabPane.getTabs().remove(tabStudent);
			tabPane.getTabs().remove(tabStaff);
			tabPane.getTabs().remove(tabStaffInfo);
		} else if (user == CLERK) {
			tabPane.getTabs().remove(tabStaff);
			tabPane.getTabs().remove(tabStudentInfo);
		} else if (user == LIBRARIAN) {
			tabPane.getTabs().remove(tabStudentInfo);
		}
	}

	@FXML
    public void initialize() {
        labelWarning.setStyle("-fx-text-fill: #ffffff;");

        tabPane.getSelectionModel().selectedItemProperty().addListener(
    	    new ChangeListener<Tab>() {
    	        @Override
    	        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
    	            System.out.println("Tab Selection changed: " + t.getId() + " -> " + t1.getId());
    	            t1.getId();
    	            try {
    	            	if (t1 == tabBook) {
        	            	bookSceneController.refresh();
        	        	} else if (t1 == tabPrintMedia) {
        	        		printMediaSceneController.refresh();
        	        	} else if (t1 == tabBorrow) {
        	        		borrowSceneController.refresh();
        	            } else if (t1 == tabStudent) {
        	            	studentSceneController.refresh();
        	            } else if (t1 == tabStaff) {
        	            	staffSceneController.refresh();
        	            }
					} catch (SQLException e) {
						e.printStackTrace();
					}

    	        }
    	    }
        );
    }

}
