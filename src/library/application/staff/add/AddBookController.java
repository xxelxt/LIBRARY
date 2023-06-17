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
import javafx.scene.layout.AnchorPane;
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
    private Spinner<Integer> fieldQuantity = new Spinner<Integer>();
    
    @FXML
    private Spinner<Integer> fieldReissue = new Spinner<Integer>();

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
        fieldReissue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 255, 1));
    }
    
    @FXML
    void btnAddBook(ActionEvent event) {
    	BookDAO bookDAO = new BookDAO();
    	
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
    	
    	clearTextField();
    }
    
    void clearTextField() {
    	fieldTitle.clear();
    	fieldCountry.clear();
    	fieldPublishDate.setValue(null);
    	fieldQuantity.getValueFactory().setValue(1);
    	fieldAuthors.clear();
    	fieldReissue.getValueFactory().setValue(1);
    	fieldCategory.clear();
    	fieldPublisher.clear();
    }
}
