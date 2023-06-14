package library.application.staff;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StudentHistorySceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> borrowTableView;

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
    private TableColumn<?, ?> colBorrowQuantity;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colFineStatus;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colPublicationID;

    @FXML
    private TableColumn<?, ?> colPublicationTitle;

    @FXML
    private TableColumn<?, ?> colReturnedDate;

    @FXML
    private TableColumn<?, ?> colReturnedStatus;

    @FXML
    private TableColumn<?, ?> colStartDate;

    @FXML
    private TableColumn<?, ?> colStudentID;

    @FXML
    void btnReturnBack(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert borrowTableView != null : "fx:id=\"borrowTableView\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert buttonBook != null : "fx:id=\"buttonBook\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert buttonBorrow != null : "fx:id=\"buttonBorrow\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert buttonInfo != null : "fx:id=\"buttonInfo\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert buttonLogOut != null : "fx:id=\"buttonLogOut\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert buttonPrintMedia != null : "fx:id=\"buttonPrintMedia\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert buttonStudent != null : "fx:id=\"buttonStudent\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert colBorrowQuantity != null : "fx:id=\"colBorrowQuantity\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert colDueDate != null : "fx:id=\"colDueDate\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert colFineStatus != null : "fx:id=\"colFineStatus\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert colID != null : "fx:id=\"colID\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert colPublicationID != null : "fx:id=\"colPublicationID\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert colPublicationTitle != null : "fx:id=\"colPublicationTitle\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert colReturnedDate != null : "fx:id=\"colReturnedDate\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert colReturnedStatus != null : "fx:id=\"colReturnedStatus\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert colStartDate != null : "fx:id=\"colStartDate\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";
        assert colStudentID != null : "fx:id=\"colStudentID\" was not injected: check your FXML file 'StudentHistoryScene.fxml'.";

    }

}
