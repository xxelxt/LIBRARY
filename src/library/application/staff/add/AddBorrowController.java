package library.application.staff.add;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import library.mysql.dao.BorrowDAO;

public class AddBorrowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox checkboxReturned;

    @FXML
    private CheckBox checkboxFineStatus;

    @FXML
    private DatePicker fieldDueDate;

    @FXML
    private TextField fieldPublicationID;

    @FXML
    private Spinner<Integer> fieldQuantity = new Spinner<>();

    @FXML
    private DatePicker fieldStartDate;

    @FXML
    private TextField fieldStudentID;

    @FXML
    void btnAddBorrow(ActionEvent event) {
    	BorrowDAO borrowDAO = new BorrowDAO();

    	borrowDAO.addBorrow(
    		fieldStudentID.getText(),
    		Integer.parseInt(fieldPublicationID.getText()),
    		fieldQuantity.getValue(),
    		Date.valueOf(fieldStartDate.getValue()),
    		Date.valueOf(fieldDueDate.getValue()),
    		checkboxFineStatus.isSelected(),
    		checkboxReturned.isSelected()
    	);

    	clearTextField();
    }

    @FXML
    void initialize() {
        assert checkboxReturned != null : "fx:id=\"checkboxReturned\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert checkboxFineStatus != null : "fx:id=\"checkboxFineStatus\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldDueDate != null : "fx:id=\"fieldDueDate\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldPublicationID != null : "fx:id=\"fieldPublicationID\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldStartDate != null : "fx:id=\"fieldStartDate\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldStudentID != null : "fx:id=\"fieldStudentID\" was not injected: check your FXML file 'AddBorrow.fxml'.";

        fieldQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
    }

    void clearTextField() {
    	fieldStudentID.clear();
    	fieldPublicationID.clear();
    	fieldQuantity.getValueFactory().setValue(1);
    	fieldStartDate.setValue(null);
    	fieldDueDate.setValue(null);
    	checkboxReturned.setSelected(false);
    	checkboxFineStatus.setSelected(false);

    }
}
