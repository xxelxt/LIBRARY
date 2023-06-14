package library.application.staff.add;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import library.application.staff.PrintMediaSceneController;
import library.mysql.Database;

public class AddPrintMediaController {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fieldCountry;

    @FXML
    private TextField fieldPrintType;

    @FXML
    private DatePicker fieldPublishDate;

    @FXML
    private Spinner<Integer> fieldQuantity = new Spinner<Integer>();
    
    @FXML
    private Spinner<Integer> fieldReleaseNumber = new Spinner<Integer>();

    @FXML
    private TextField fieldTitle;

    
    @FXML
    void btnAddPrintMedia(ActionEvent event) {
    	mainDb.addPrintMedia();
    	mainController.refresh();
    	mainController.scrollToLast();
    }
    
    private PrintMediaSceneController mainController;
    public void setMainController(PrintMediaSceneController control) {
    	this.mainController = control;
    }

    @FXML
    void initialize() {
        assert fieldCountry != null : "fx:id=\"fieldCountry\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldPrintType != null : "fx:id=\"fieldPrintType\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldPublishDate != null : "fx:id=\"fieldPublishDate\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldQuantity != null : "fx:id=\"fieldQuantity\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldReleaseNumber != null : "fx:id=\"fieldReleaseNumber\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";
        assert fieldTitle != null : "fx:id=\"fieldTitle\" was not injected: check your FXML file 'AddPrintMedia.fxml'.";

    }

    private Database mainDb;
    
    public void setDB(Database db) {
    	this.mainDb = db;
    }
}
