package library.application.addbook;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import library.application.MainSceneController;
import library.mysql.Database;

public class AddBookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fieldCountry;

    @FXML
    private DatePicker fieldPublishDate;

    @FXML
    private Spinner<Integer> fieldQuantity = new Spinner<Integer>();

    @FXML
    private TextField fieldTitle;
    
    @FXML
    void btnAddBook(ActionEvent event) {
    	mainDb.addPublication(fieldTitle.getText(), Date.valueOf(fieldPublishDate.getValue()), fieldCountry.getText(), fieldQuantity.getValue());
    	mainController.refresh();
    	mainController.scrollToLast();
    }
    
    private MainSceneController mainController;
    public void setMainController(MainSceneController control) {
    	this.mainController = control;
    }

    @FXML
    void initialize() {
        assert fieldCountry != null : "fx:id=\"fieldCountry\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldPublishDate != null : "fx:id=\"fieldPublishDate\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddBook.fxml'.";
        assert fieldTitle != null : "fx:id=\"fieldTitle\" was not injected: check your FXML file 'AddBook.fxml'.";
        
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999, 1);
        
        fieldQuantity.setValueFactory(valueFactory);

    }

    private Database mainDb;
    
    public void setDB(Database db) {
    	this.mainDb = db;
    }
}
