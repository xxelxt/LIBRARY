package library.application.staff;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StudentSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> booksTableView;

    @FXML
    private Button buttonBook;

    @FXML
    private Button buttonBorrow;

    @FXML
    private Button buttonInfo;

    @FXML
    private Button buttonLogOut;

    @FXML
    private Button buttonPrintMedia;

    @FXML
    private Button buttonStudent;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colClass;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colFine;

    @FXML
    private TableColumn<?, ?> colFineStatus;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhoneNum;

    @FXML
    private TableColumn<?, ?> colStudentID;

    @FXML
    private TextField fieldSearch;

    @FXML
    void btnAddStudent(ActionEvent event) {

    }

    @FXML
    void btnBorrowHistory(ActionEvent event) {

    }

    @FXML
    void btnDeleteStudent(ActionEvent event) {

    }

    @FXML
    void btnEditStudent(ActionEvent event) {

    }

    @FXML
    void btnSearch(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert booksTableView != null : "fx:id=\"booksTableView\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert buttonBook != null : "fx:id=\"buttonBook\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert buttonBorrow != null : "fx:id=\"buttonBorrow\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert buttonInfo != null : "fx:id=\"buttonInfo\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert buttonLogOut != null : "fx:id=\"buttonLogOut\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert buttonPrintMedia != null : "fx:id=\"buttonPrintMedia\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert buttonStudent != null : "fx:id=\"buttonStudent\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert colAddress != null : "fx:id=\"colAddress\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert colClass != null : "fx:id=\"colClass\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert colEmail != null : "fx:id=\"colEmail\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert colFine != null : "fx:id=\"colFine\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert colFineStatus != null : "fx:id=\"colFineStatus\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert colGender != null : "fx:id=\"colGender\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert colPhoneNum != null : "fx:id=\"colPhoneNum\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert colStudentID != null : "fx:id=\"colStudentID\" was not injected: check your FXML file 'StudentScene.fxml'.";
        assert fieldSearch != null : "fx:id=\"fieldSearch\" was not injected: check your FXML file 'StudentScene.fxml'.";

    }

}
