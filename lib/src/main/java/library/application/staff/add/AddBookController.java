package library.application.staff.add;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
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

    private TextField[] textFields;

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
    }

    @FXML
    void btnAddBook(ActionEvent event) {
    	if (isAnyFieldNull()) {
    		highlightFields();
            Toaster.showError("Lỗi", "Vui lòng nhập đầy đủ thông tin sách.");
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

			highlightFields();
	    	clearTextField();
	    	Toaster.showSuccess("Thêm sách thành công", "Đã thêm sách vào CSDL.");

		} catch (SQLException e) {
			Toaster.showError("Lỗi CSDL", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			/* Catch DateError */
			Toaster.showError("Lỗi nhập ngày tháng", e.getMessage());
			e.printStackTrace();
		}
    }

    private boolean isAnyFieldNull() {
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                return true;
            }
        }
        return fieldPublishDate.getValue() == null;
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
        if (fieldPublishDate.getValue() == null) {
            fieldPublishDate.setStyle(highlight);
        } else {
            fieldPublishDate.setStyle("");
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
