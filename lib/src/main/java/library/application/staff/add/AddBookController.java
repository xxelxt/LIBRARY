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
import library.mysql.dao.AuthorDAO;
import library.mysql.dao.BookDAO;
import library.mysql.dao.PublicationDAO;

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

    private TextField[] textFields;

    private AutoCompletionBinding<String> autoCompletionCategory;

    private AutoCompletionBinding<String> autoCompletionAuthor;

    private AutoCompletionBinding<String> autoCompletionCountry;

    private AutoCompletionBinding<String> autoCompletionPublisher;

    private ArrayList<String> categories;

    private ArrayList<String> authors;

    private ArrayList<String> countries;

    private ArrayList<String> publishers;

    private BookDAO bookDAO = new BookDAO();
    private AuthorDAO authorDAO = new AuthorDAO();
    private PublicationDAO publicationDAO = new PublicationDAO();

    @FXML
    void initialize() throws SQLException {
        assert fieldAuthors != null : "fx:id=\"fieldAuthors\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldCategory != null : "fx:id=\"fieldCategory\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldCountry != null : "fx:id=\"fieldCountry\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldPublishDate != null : "fx:id=\"fieldPublishDate\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldPublisher != null : "fx:id=\"fieldPublisher\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldReissue != null : "fx:id=\"fieldReissue\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldTitle != null : "fx:id=\"fieldTitle\" was not injected: check your FXML file 'AddBook.fxml'.";

        textFields = new TextField[]{fieldTitle, fieldCountry, fieldAuthors, fieldCategory, fieldPublisher};

        fieldQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        fieldReissue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 1));
        fieldReissue.getValueFactory().setValue(0);

        fieldPublishDate.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });

        categories = bookDAO.loadAllCategories();
        authors = authorDAO.getAuthors();
        countries = publicationDAO.loadAllCountries();
        publishers = bookDAO.loadAllPublishers();
        autoCompletionCategory = TextFields.bindAutoCompletion(fieldCategory, categories);
        autoCompletionAuthor = TextFields.bindAutoCompletion(fieldAuthors, authors);
        autoCompletionCountry = TextFields.bindAutoCompletion(fieldCountry, countries);
        autoCompletionPublisher = TextFields.bindAutoCompletion(fieldPublisher, publishers);
    }

    @FXML
    void btnAddBook(ActionEvent event) throws SQLException {
    	if (isAnyFieldNull()) {
    		highlightFields();
            Toaster.showError("Lỗi", "Vui lòng nhập đầy đủ thông tin sách.");
            return;
        }

    	try {
    		Date publishDate = null;
    		if (fieldPublishDate.getValue() != null) {
    		    publishDate = Date.valueOf(fieldPublishDate.getValue());
    		}

			bookDAO.addBook(
				fieldTitle.getText(),
				publishDate,
				fieldCountry.getText(),
				fieldQuantity.getValue(),
				fieldCategory.getText(),
				fieldReissue.getValue(),
				fieldAuthors.getText(),
				fieldPublisher.getText()
			);

			rebindAutoComplete();

			highlightFields();
	    	clearTextField();
	    	Toaster.showSuccess("Thêm sách thành công", "Đã thêm sách vào CSDL.");

		} catch (SQLException e) {
			Toaster.showError("Lỗi CSDL", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			/* Catch DateError */
			Toaster.showError("Lỗi", e.getMessage());
			e.printStackTrace();
		}
    }

    private void rebindAutoComplete() throws SQLException {
    	if (autoCompletionCategory != null) {
		    autoCompletionCategory.dispose();
		}
		categories = bookDAO.loadAllCategories();
		autoCompletionCategory = TextFields.bindAutoCompletion(fieldCategory, categories);

		if (autoCompletionAuthor != null) {
		    autoCompletionAuthor.dispose();
		}
		authors = authorDAO.getAuthors();
		autoCompletionAuthor = TextFields.bindAutoCompletion(fieldAuthors, authors);

		if (autoCompletionCountry != null) {
		    autoCompletionCountry.dispose();
		}
		countries = publicationDAO.loadAllCountries();
		autoCompletionCountry = TextFields.bindAutoCompletion(fieldCountry, countries);

		if (autoCompletionPublisher != null) {
		    autoCompletionPublisher.dispose();
		}
		publishers = bookDAO.loadAllPublishers();
		autoCompletionPublisher = TextFields.bindAutoCompletion(fieldPublisher, publishers);
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
    	fieldAuthors.clear();
    	fieldReissue.getValueFactory().setValue(0);
    	fieldCategory.clear();
    	fieldPublisher.clear();
    }

}
