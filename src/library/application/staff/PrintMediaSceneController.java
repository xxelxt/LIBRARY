package library.application.staff;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.application.staff.add.AddPrintMediaController;
import library.mysql.Database;

import library.publication.PrintMedia;
import library.publication.Publication;

public class PrintMediaSceneController implements Initializable {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<PrintMedia> pmTableView;
    
    @FXML
    private TableColumn<PrintMedia, String> colCountry;

    @FXML
    private TableColumn<PrintMedia, Integer> colID;

    @FXML
    private TableColumn<PrintMedia, Date> colPublicationDate;

    @FXML
    private TableColumn<PrintMedia, Integer> colQuantity;

    @FXML
    private TableColumn<PrintMedia, Integer> colReleaseNumber;

    @FXML
    private TableColumn<PrintMedia, String> colTitle;
    
    @FXML
    private TableColumn<PrintMedia, String> colPrintType;

    
    private ObservableList<PrintMedia> data;
    private Database mainDb;

    
    public void refresh() {
        data = FXCollections.observableArrayList();

        List<PrintMedia> allPM = mainDb.loadAllPrintMedias();
		System.out.println(allPM);
		
	    for (PrintMedia pm: allPM){
	    	data.add(pm);
	    }
	    
	    pmTableView.setItems(data);
    }
    
    public void scrollToLast() {
    	int lastIndex = pmTableView.getItems().size() - 1;
    	pmTableView.scrollTo(lastIndex);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

        System.out.println("Controller initialized");
        // Add a default row
		mainDb = new Database();
		refresh();
        
        // Bind the ObservableList to the TableView
        pmTableView.setItems(data);

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<PrintMedia, Integer>("publicationID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<PrintMedia, String>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<PrintMedia, Date>("releaseDate"));
        colCountry.setCellValueFactory(new PropertyValueFactory<PrintMedia, String>("country"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<PrintMedia, Integer>("quantity"));
        
        colReleaseNumber.setCellValueFactory(new PropertyValueFactory<PrintMedia, Integer>("releaseNumber"));
        colPrintType.setCellValueFactory(new PropertyValueFactory<PrintMedia, String>("printType"));
	}
	
	Date now = new Date(new java.util.Date().getTime());
	
    @FXML
    void btnAddPrintMedia(ActionEvent event) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("add/AddPrintMedia.fxml"));
	        Parent root = loader.load();
	        AddPrintMediaController pmcontroller = (AddPrintMediaController) loader.getController();
	        pmcontroller.setDB(mainDb);
	        pmcontroller.setMainController(this);
	        
	        Stage secondStage = new Stage();
	        secondStage.setTitle("Second Window");
	        
	        secondStage.setScene(new Scene(root));
	        secondStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void btnDeletePrintMedia(ActionEvent event) {
    	Integer selectedIndex = pmTableView.getSelectionModel().getSelectedIndex();
    	Publication selectedRow = pmTableView.getSelectionModel().getSelectedItem();
    	
    	if (selectedRow != null) {
	    	mainDb.deletePublication(selectedRow.getPublicationID());
	    	this.refresh();
	    	
	        if (selectedIndex >= 0 && selectedIndex < data.size()) {
	        	pmTableView.getSelectionModel().select(selectedIndex);
	        } 
    	} else {
        	pmTableView.getSelectionModel().clearSelection();
        }
    }
    
    @FXML
    void btnEditPrintMedia(ActionEvent event) {

    }


}
