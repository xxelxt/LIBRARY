package library.application.staff.add;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import library.application.util.Toaster;
import library.mysql.dao.PrintMediaDAO;
import library.mysql.dao.PublicationDAO;

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

    private TextField[] textFields;

    private AutoCompletionBinding<String> autoCompletionCountry;

    private AutoCompletionBinding<String> autoCompletionPrintType;

    private ArrayList<String> countries;

    private ArrayList<String> printTypes;

    private PrintMediaDAO pmDAO = new PrintMediaDAO();
    private PublicationDAO publicationDAO = new PublicationDAO();

    @FXML
    void btnAddPrintMedia(ActionEvent event) {
    	if (isAnyFieldNull()) {
            highlightFields();
            Toaster.showError("Lỗi", "Vui lòng nhập đầy đủ thông tin ấn phẩm.");
            return;
        }

    	PrintMediaDAO pmDAO = new PrintMediaDAO();

    	try {
    		Date publishDate = null;
    		if (fieldPublishDate.getValue() != null) {
    		    publishDate = Date.valueOf(fieldPublishDate.getValue());
    		}

        	pmDAO.addPrintMedia(
            		fieldTitle.getText(),
            		publishDate,
            		fieldCountry.getText(),
            		fieldQuantity.getValue(),
            		fieldReleaseNumber.getValue(),
            		fieldPrintType.getText()
            );

        	if (autoCompletionCountry != null) {
			    autoCompletionCountry.dispose();
			}
			countries = publicationDAO.loadAllCountries();
			autoCompletionCountry = TextFields.bindAutoCompletion(fieldCountry, countries);

			if (autoCompletionPrintType != null) {
			    autoCompletionPrintType.dispose();
			}
			printTypes = pmDAO.loadAllPrintTypes();
			autoCompletionPrintType = TextFields.bindAutoCompletion(fieldPrintType, printTypes);

        	highlightFields();
        	clearTextField();
        	Toaster.showSuccess("Thêm ấn phẩm thành công", "Đã thêm ẩn phẩm vào CSDL.");

    	} catch (SQLException e) {
			Toaster.showError("Lỗi CSDL", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			/* Catch DateError */
			Toaster.showError("Lỗi", e.getMessage());
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() throws SQLException {
        assert fieldCountry != null : "fx:id=\"fieldCountry\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldPrintType != null : "fx:id=\"fieldPrintType\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldPublishDate != null : "fx:id=\"fieldPublishDate\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldReleaseNumber != null : "fx:id=\"fieldReleaseNumber\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldTitle != null : "fx:id=\"fieldTitle\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";

        textFields = new TextField[]{fieldTitle, fieldCountry, fieldPrintType};

        fieldQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        fieldReleaseNumber.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1));

        fieldPublishDate.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });

        countries = publicationDAO.loadAllCountries();
        printTypes = pmDAO.loadAllPrintTypes();
        autoCompletionCountry = TextFields.bindAutoCompletion(fieldCountry, countries);
        autoCompletionPrintType = TextFields.bindAutoCompletion(fieldPrintType, printTypes);
    }

    private boolean isAnyFieldNull() {
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void highlightFields() {
    	String highlight = "-fx-border-color: red; -fx-border-radius: 5px;";
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                textField.setStyle(highlight);
            } else {
                textField.setStyle("");
            }
        }
    }

    void clearTextField() {
    	fieldTitle.clear();
    	fieldCountry.clear();
    	fieldPublishDate.setValue(null);
    	fieldQuantity.getValueFactory().setValue(1);
    	fieldReleaseNumber.getValueFactory().setValue(0);
    	fieldPrintType.clear();
    }

}