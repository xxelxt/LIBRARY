package library.application.staff.add;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class AddBorrowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker fieldDueDate;

    @FXML
    private TextField fieldPublicationID;

    @FXML
    private Spinner<?> fieldQuantity;

    @FXML
    private DatePicker fieldStartDate;

    @FXML
    private TextField fieldStudentID;

    @FXML
    void btnAddBorrow(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert fieldDueDate != null : "fx:id=\"fieldDueDate\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldPublicationID != null : "fx:id=\"fieldPublicationID\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldStartDate != null : "fx:id=\"fieldStartDate\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldStudentID != null : "fx:id=\"fieldStudentID\" was not injected: check your FXML file 'AddBorrow.fxml'.";

    }

}
