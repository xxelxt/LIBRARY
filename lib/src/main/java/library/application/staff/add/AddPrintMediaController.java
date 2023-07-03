package library.application.staff.add;

import java.sql.Date;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import library.application.util.Toaster;
import library.mysql.dao.PrintMediaDAO;

public class AddPrintMediaController {

    @FXML
    private TextField fieldCountry;

    @FXML
    private TextField fieldPrintType;

    @FXML
    private DatePicker fieldPublishDate;

    @FXML
    private Spinner<Integer> fieldQuantity = new Spinner<>();

    @FXML
    private Spinner<Integer> fieldReleaseNumber = new Spinner<>();

    @FXML
    private TextField fieldTitle;

    @FXML
    private AnchorPane paneAdd;

    @FXML
    void btnAddPrintMedia(ActionEvent event) {
    	PrintMediaDAO pmDAO = new PrintMediaDAO();

    	try {
        	pmDAO.addPrintMedia(
            		fieldTitle.getText(),
            		Date.valueOf(fieldPublishDate.getValue()),
            		fieldCountry.getText(),
            		fieldQuantity.getValue(),
            		fieldReleaseNumber.getValue(),
            		fieldPrintType.getText()
            );
		} catch (SQLException e) { // Added Toast
			// Catch SQL
			Toaster.showError("SQL ERROR", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) { // Added Toast
			// Catch DateError
			Toaster.showError("Input ERROR", e.getMessage());
			e.printStackTrace();
		}

    	clearTextField();
    }

    @FXML
    void initialize() {
        assert fieldCountry != null : "fx:id=\"fieldCountry\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldPrintType != null : "fx:id=\"fieldPrintType\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldPublishDate != null : "fx:id=\"fieldPublishDate\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldReleaseNumber != null : "fx:id=\"fieldReleaseNumber\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldTitle != null : "fx:id=\"fieldTitle\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";

        fieldQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        fieldReleaseNumber.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
    }

    void clearTextField() {
    	fieldTitle.clear();
    	fieldCountry.clear();
    	fieldPublishDate.setValue(null);
    	fieldQuantity.getValueFactory().setValue(1);
    	fieldReleaseNumber.getValueFactory().setValue(1);
    	fieldPrintType.clear();
    }

}