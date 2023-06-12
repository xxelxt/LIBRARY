package library.application;

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
import library.mysql.Database;
import library.publication.Books;
import library.publication.Publication;

import library.application.addbook.AddBookController;

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
    private TableColumn<Publication, String> colAuthors;
    
    @FXML
    private TableColumn<Publication, String> colPublisher;
    
    @FXML
    private TableColumn<Publication, Integer> colReissue;
    
    

    private ObservableList<Publication> data;
    private Database mainDb;

//    // Add a new row dynamically
//    @FXML
//    private void addRow() {
//        MyDataModel newRow = new MyDataModel("Jane", 30);
//        data.add(newRow);
//    }

    
    public void refresh() {
        data = FXCollections.observableArrayList();
        
		List<Publication> allPublication = mainDb.loadAllPublication();
        List<Books> allBooks = mainDb.loadAllBooks();
		System.out.println(allBooks);
		
//        for (Books book: allBooks){
//            data.add(book);
//        }
        
        for (Publication pub: allPublication){
            data.add(pub);
        }
        
        pubTableView.setItems(data);
    }
    
    public void scrollToLast() {
    	int lastIndex = pubTableView.getItems().size() - 1;
    	pubTableView.scrollTo(lastIndex);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
        // Initialize the ObservableList

        System.out.println("Controller initialized");
        // Add a default row
		mainDb = new Database();
		refresh();
        
        // Bind the ObservableList to the TableView
        pubTableView.setItems(data);

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<Publication, Integer>("publicationID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Publication, String>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<Publication, Date>("releaseDate"));
        colCountry.setCellValueFactory(new PropertyValueFactory<Publication, String>("country"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<Publication, Integer>("quantity"));
        
//        colAuthors.setCellValueFactory(new PropertyValueFactory<Publication, String>("authors"));
//        colPublisher.setCellValueFactory(new PropertyValueFactory<Publication, String>("publisher"));
//        colCategory.setCellValueFactory(new PropertyValueFactory<Publication, String>("category"));
//        colReissue.setCellValueFactory(new PropertyValueFactory<Publication, Integer>("reissue"));
	}
	
	Date now = new Date(new java.util.Date().getTime());
	
    @FXML
    void btnAddBook(ActionEvent event) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("addbook/AddBook.fxml"));
	        Parent root = loader.load();
	        AddBookController bookcontroller = (AddBookController) loader.getController();
	        bookcontroller.setDB(mainDb);
	        bookcontroller.setMainController(this);
	        
	        Stage secondStage = new Stage();
	        secondStage.setTitle("Second Window");
	        
	        secondStage.setScene(new Scene(root));
	        secondStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void btnDeleteBook(ActionEvent event) {
    	Integer selectedIndex = pubTableView.getSelectionModel().getSelectedIndex();
    	Publication selectedRow = pubTableView.getSelectionModel().getSelectedItem();
    	
    	if (selectedRow != null) {
	    	mainDb.deletePublication(selectedRow.getPublicationID());
	    	this.refresh();
	    	
	        if (selectedIndex >= 0 && selectedIndex < data.size()) {
	            pubTableView.getSelectionModel().select(selectedIndex);
	        } 
    	} else {
        	pubTableView.getSelectionModel().clearSelection();
        }
    }

}
