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

    private TextField[] textFields;

    @FXML
    void btnAddPrintMedia(ActionEvent event) {
    	if (isAnyFieldNull()) {
            highlightFields();
            Toaster.showError("Lỗi", "Vui lòng nhập đầy đủ thông tin ấn phẩm.");
            return;
        }

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

        	highlightFields();
        	clearTextField();
        	Toaster.showSuccess("Thêm ấn phẩm thành công", "Đã thêm ẩn phẩm vào CSDL.");

    	} catch (SQLException e) {
			Toaster.showError("Lỗi CSDL", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			/* Catch DateError */
			Toaster.showError("Lỗi nhập ngày tháng", e.getMessage());
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert fieldCountry != null : "fx:id=\"fieldCountry\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldPrintType != null : "fx:id=\"fieldPrintType\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldPublishDate != null : "fx:id=\"fieldPublishDate\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldReleaseNumber != null : "fx:id=\"fieldReleaseNumber\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldTitle != null : "fx:id=\"fieldTitle\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";

        textFields = new TextField[]{fieldTitle, fieldCountry, fieldPrintType};

        fieldQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        fieldReleaseNumber.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));

        fieldPublishDate.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });
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
    	fieldReleaseNumber.getValueFactory().setValue(1);
    	fieldPrintType.clear();
    }

}