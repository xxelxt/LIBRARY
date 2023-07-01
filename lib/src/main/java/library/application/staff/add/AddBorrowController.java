package library.application.staff.add;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import library.mysql.dao.BorrowDAO;
import library.mysql.dao.StudentDAO;

public class AddBorrowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fieldPublicationID;

    @FXML
    private Spinner<Integer> fieldQuantity = new Spinner<>();

    @FXML
    private DatePicker fieldStartDate;
    
    @FXML
    private DatePicker fieldDueDate;
    
    @FXML
    private DatePicker fieldReturnedDate;

    @FXML
    private TextField fieldStudentID; 
    
    private boolean calcFineStatus(String studentID, LocalDate dueDate, LocalDate returnedDate) {
    	long daysDifference = 0;
    	LocalDate nowDate = LocalDate.now();
    	
    	if (returnedDate == null) {
	    	if (nowDate.isAfter(dueDate)) {
				daysDifference = nowDate.toEpochDay() - dueDate.toEpochDay();
				// Now - Due (newly added)
			}
    	} else {
    		// Due < Return (always)
			daysDifference = returnedDate.toEpochDay() - dueDate.toEpochDay();
			// Return - Due (newly added)
    	}
    	
		try {
			if (daysDifference > 0) {
				StudentDAO studentDAO = new StudentDAO();
				studentDAO.addToStudentFine(studentID, daysDifference);
				return true;
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return false;
    }
    
    @FXML
    void btnAddBorrow(ActionEvent event) {
    	BorrowDAO borrowDAO = new BorrowDAO();
    	
    	LocalDate dueDate = fieldDueDate.getValue();
    	LocalDate returnedDate = fieldReturnedDate.getValue();
    	
    	borrowDAO.addBorrow(
    		fieldStudentID.getText(),
    		Integer.parseInt(fieldPublicationID.getText()),
    		fieldQuantity.getValue(),
    		Date.valueOf(fieldStartDate.getValue()),
    		Date.valueOf(dueDate),
    		(returnedDate != null ? Date.valueOf(returnedDate) : null),
    		calcFineStatus(fieldStudentID.getText(), dueDate, returnedDate),
    		(returnedDate != null)
    	);

    	clearTextField();
    }

    @FXML
    void initialize() {
        assert fieldDueDate != null : "fx:id=\"fieldDueDate\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldPublicationID != null : "fx:id=\"fieldPublicationID\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldStartDate != null : "fx:id=\"fieldStartDate\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldStudentID != null : "fx:id=\"fieldStudentID\" was not injected: check your FXML file 'AddBorrow.fxml'.";

        fieldQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        fieldStartDate.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });
        fieldDueDate.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(fieldStartDate.getValue() == null || item.isBefore(fieldStartDate.getValue()));
            }
        });
        fieldReturnedDate.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(fieldDueDate.getValue() == null || 
                		item.isBefore(fieldDueDate.getValue()) ||
                		item.isAfter(LocalDate.now()));
            } // Return date will always be after Due Date
        });
    }

    void clearTextField() {
    	fieldStudentID.clear();
    	fieldPublicationID.clear();
    	fieldQuantity.getValueFactory().setValue(1);
    	fieldStartDate.setValue(null);
    	fieldDueDate.setValue(null);
    	fieldReturnedDate.setValue(null);
    }
}