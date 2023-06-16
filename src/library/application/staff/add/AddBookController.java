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
import library.mysql.dao.BookDAO;

public class AddBookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private CheckBox fieldReissue;

    @FXML
    private TextField fieldTitle;

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
    		fieldReissue.isSelected(),
    		fieldAuthors.getText()
    	);    	
    }
}
