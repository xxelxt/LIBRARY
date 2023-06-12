package library.application;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.mysql.Database;
import library.publication.Publication;

public class MainSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Publication> pubTableView;

    @FXML
    private TableColumn<Publication, String> colCategory;

    @FXML
    private TableColumn<Publication, String> colCountry;
    
    @FXML
    private TableColumn<Publication, Date> colPublicationDate;

    @FXML
    private TableColumn<Publication, Integer> colQuantity;
    
    @FXML
    private TableColumn<Publication, Integer> colID;

    @FXML
    private TableColumn<Publication, String> colTitle;
    
    @FXML
    void btnAddBook(ActionEvent event) {

    }

    private ObservableList<Publication> data;
    private Database mainDb;

//    // Add a new row dynamically
//    @FXML
//    private void addRow() {
//        MyDataModel newRow = new MyDataModel("Jane", 30);
//        data.add(newRow);
//    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
        // Initialize the ObservableList
        data = FXCollections.observableArrayList();
        System.out.println("Controller initialized");
        // Add a default row
		mainDb = new Database();
		List<Publication> allPublication = mainDb.loadAllPublication();
        
        for (Publication pub: allPublication){
            data.add(pub);
        }

        // Bind the ObservableList to the TableView
        pubTableView.setItems(data);

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<Publication, Integer>("publicationID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Publication, String>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<Publication, Date>("releaseDate"));
        colCountry.setCellValueFactory(new PropertyValueFactory<Publication, String>("country"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<Publication, Integer>("quantity"));
	}

}
