package library.application.staff.add;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import library.application.util.Toaster;
import library.mysql.dao.BorrowDAO;
import library.mysql.dao.PublicationDAO;
import library.mysql.dao.StudentDAO;

public class AddBorrowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fieldPublicationID;

    @FXML
    private TextField fieldPublicationTitle;

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

    @FXML
    private TextField fieldStudentName;

    private TextField[] textFields;

    private boolean calcFineStatus(String studentID, LocalDate dueDate, LocalDate returnedDate) {
    	long daysDifference = 0;
    	LocalDate nowDate = LocalDate.now();

    	if (returnedDate == null) {
	    	if (nowDate.isAfter(dueDate)) {
				daysDifference = nowDate.toEpochDay() - dueDate.toEpochDay();
				/* Now - Due (newly added) */
			}
    	} else {
    		/* Due < Return (always) */
			daysDifference = returnedDate.toEpochDay() - dueDate.toEpochDay();
			/* Return - Due (newly added) */
    	}

		try {
			if (daysDifference > 0) {
				StudentDAO studentDAO = new StudentDAO();
				studentDAO.addToStudentFine(studentID, daysDifference);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

    	return false;
    }

    @FXML
    void btnAddBorrow(ActionEvent event) {
    	if (isAnyFieldNull()) {
            highlightFields();
            Toaster.showError("Lỗi", "Vui lòng nhập đầy đủ thông tin mượn.");
            return;
        }

    	BorrowDAO borrowDAO = new BorrowDAO();

    	LocalDate dueDate = fieldDueDate.getValue();
    	LocalDate returnedDate = fieldReturnedDate.getValue();

    	try {
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

    		highlightFields();
        	clearTextField();
        	Toaster.showSuccess("Thêm mượn thành công", "Đã thêm mượn vào CSDL.");

    	} catch (SQLException e) {
			Toaster.showError("Lỗi", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			/* Catch DateError */
			Toaster.showError("Lỗi nhập ngày tháng", e.getMessage());
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert fieldDueDate != null : "fx:id=\"fieldDueDate\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldPublicationID != null : "fx:id=\"fieldPublicationID\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldPublicationTitle != null : "fx:id=\"fieldPublicationTitle\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldStartDate != null : "fx:id=\"fieldStartDate\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldStudentID != null : "fx:id=\"fieldStudentID\" was not injected: check your FXML file 'AddBorrow.fxml'.";
        assert fieldStudentName != null : "fx:id=\"fieldStudentName\" was not injected: check your FXML file 'AddBorrow.fxml'.";

        textFields = new TextField[]{fieldStudentID, fieldPublicationID};

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
            } /* Return date will always be after Due date */
        });

        fieldStudentID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    displayStudentName(newValue);
                } else {
                	fieldStudentName.setText(null);
                }
            }
        });

        fieldPublicationID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null && !newValue.isEmpty()) {
                    displayPublicationTitle(Integer.parseInt(newValue));
                } else {
                	fieldPublicationTitle.setText(null);
                }
            }
        });
    }

    private void displayStudentName(String studentID) {
        try {
            StudentDAO studentDAO = new StudentDAO();
            String studentName = studentDAO.getStudentNamebyStudentID(studentID);
            fieldStudentName.setText(studentName);
        } catch (SQLException e) {
            Toaster.showError("Lỗi", e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayPublicationTitle(int publicationID) {
        try {
            PublicationDAO publicationDAO = new PublicationDAO();
            String title = publicationDAO.getTitlebyPublicationID(publicationID);
            fieldPublicationTitle.setText(title);
        } catch (SQLException e) {
            Toaster.showError("Lỗi", e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isAnyFieldNull() {
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                return true;
            }
        }

        return fieldStartDate.getValue() == null ||
                fieldDueDate.getValue() == null;
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

        if (fieldStartDate.getValue() == null) {
            fieldStartDate.setStyle(highlight);
        } else {
            fieldStartDate.setStyle("");
        }

        if (fieldDueDate.getValue() == null) {
            fieldDueDate.setStyle(highlight);
        } else {
            fieldDueDate.setStyle("");
        }
    }

    void clearTextField() {
    	fieldStudentID.clear();
    	fieldPublicationID.clear();
    	fieldQuantity.getValueFactory().setValue(1);
    	fieldStartDate.setValue(LocalDate.now());
    	fieldDueDate.setValue(null);
    	fieldReturnedDate.setValue(null);
    }

}
