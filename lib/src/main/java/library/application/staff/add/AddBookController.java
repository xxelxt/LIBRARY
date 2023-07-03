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
import library.mysql.dao.BookDAO;

public class AddBookController {

	@FXML
    private TextField fieldAuthors;

    @FXML
    private TextField fieldCategory;

    @FXML
    private TextField fieldCountry;

    @FXML
    private DatePicker fieldPublishDate;

    @FXML
    private TextField fieldPublisher;

    @FXML
    private Spinner<Integer> fieldQuantity = new Spinner<>();

    @FXML
    private Spinner<Integer> fieldReissue = new Spinner<>();

    @FXML
    private TextField fieldTitle;

    @FXML
    private AnchorPane paneAdd;

    @FXML
    void initialize() {
        assert fieldAuthors != null : "fx:id=\"fieldAuthors\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldCategory != null : "fx:id=\"fieldCategory\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldCountry != null : "fx:id=\"fieldCountry\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldPublishDate != null : "fx:id=\"fieldPublishDate\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldPublisher != null : "fx:id=\"fieldPublisher\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldReissue != null : "fx:id=\"fieldReissue\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldTitle != null : "fx:id=\"fieldTitle\" was not injected: check your FXML file 'AddBook.fxml'.";

        fieldQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        fieldReissue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 1));
        fieldReissue.getValueFactory().setValue(0);
    }

    boolean isAnyFieldNull() {
        return fieldTitle.getText().isEmpty() || fieldPublishDate.getValue() == null ||
            fieldCountry.getText().isEmpty() || fieldQuantity.getValue() == null ||
            fieldCategory.getText().isEmpty() || fieldReissue.getValue() == null ||
            fieldAuthors.getText().isEmpty() || fieldPublisher.getText().isEmpty();
    }

    @FXML
    void btnAddBook(ActionEvent event) {
    	if (isAnyFieldNull()) {
    		highlightNullFields();
            Toaster.showError("Chưa đủ thông tin", "Điền hết chỗ nào còn trống đi mă");
            return;
        }

    	BookDAO bookDAO = new BookDAO();

    	try {
			bookDAO.addBook(
				fieldTitle.getText(),
				Date.valueOf(fieldPublishDate.getValue()),
				fieldCountry.getText(),
				fieldQuantity.getValue(),
				fieldCategory.getText(),
				fieldReissue.getValue(),
				fieldAuthors.getText(),
				fieldPublisher.getText()
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

    void highlightNullFields() {
        String highlightStyle = "-fx-border-color: red; -fx-border-radius: 5px;";

        if (fieldTitle.getText().isEmpty()) {
            fieldTitle.setStyle(highlightStyle);
        }
        if (fieldPublishDate.getValue() == null) {
            fieldPublishDate.setStyle(highlightStyle);
        }
        if (fieldCountry.getText().isEmpty()) {
            fieldCountry.setStyle(highlightStyle);
        }
        if (fieldQuantity.getValue() == null) {
            fieldQuantity.setStyle(highlightStyle);
        }
        if (fieldCategory.getText().isEmpty()) {
            fieldCategory.setStyle(highlightStyle);
        }
        if (fieldReissue.getValue() == null) {
            fieldReissue.setStyle(highlightStyle);
        }
        if (fieldAuthors.getText().isEmpty()) {
            fieldAuthors.setStyle(highlightStyle);
        }
        if (fieldPublisher.getText().isEmpty()) {
            fieldPublisher.setStyle(highlightStyle);
        }
    }

    void clearTextField() {
    	fieldTitle.clear();
    	fieldCountry.clear();
    	fieldPublishDate.setValue(null);
    	fieldQuantity.getValueFactory().setValue(1);
    	fieldAuthors.clear();
    	fieldReissue.getValueFactory().setValue(0);
    	fieldCategory.clear();
    	fieldPublisher.clear();
    }
}
