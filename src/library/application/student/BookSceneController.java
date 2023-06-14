package library.application.student;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.application.staff.add.AddBookController;
import library.mysql.Database;
import library.publication.Books;
import library.publication.Publication;

public class BookSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button buttonBook;

    @FXML
    private Button buttonBorrow;

    @FXML
    private Button buttonInfo;

    @FXML
    private Button buttonLogOut;

    @FXML
    private Button buttonPrintMedia;

    @FXML
    private Button buttonStudent;

    @FXML
    private TableView<Books> booksTableView;

    @FXML
    private TableColumn<Books, String> colCategory;

    @FXML
    private TableColumn<Books, String> colCountry;
    
    @FXML
    private TableColumn<Books, Date> colPublicationDate;

    @FXML
    private TableColumn<Books, Integer> colQuantity;
    
    @FXML
    private TableColumn<Books, Integer> colID;

    @FXML
    private TableColumn<Books, String> colTitle;
    
    @FXML
    private TableColumn<Books, String> colAuthors;
    
    @FXML
    private TableColumn<Books, String> colPublisher;
    
    @FXML
    private TableColumn<Books, Integer> colReissue;
    
    
    private ObservableList<Books> data;
    private Database mainDb;

    
    public void refresh() {
        data = FXCollections.observableArrayList();

        List<Books> allBooks = mainDb.loadAllBooks();
		System.out.println(allBooks);
		
	    for (Books book: allBooks){
	    	data.add(book);
	    }
	    
	    booksTableView.setItems(data);
    }
    
    public void scrollToLast() {
    	int lastIndex = booksTableView.getItems().size() - 1;
    	booksTableView.scrollTo(lastIndex);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

        System.out.println("Controller initialized");
        // Add a default row
		mainDb = new Database();
		refresh();
        
        // Bind the ObservableList to the TableView
        booksTableView.setItems(data);

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<Books, Integer>("publicationID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<Books, Date>("releaseDate"));
        colCountry.setCellValueFactory(new PropertyValueFactory<Books, String>("country"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<Books, Integer>("quantity"));
        
        colAuthors.setCellValueFactory(new PropertyValueFactory<Books, String>("authors"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<Books, String>("publisher"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Books, String>("category"));
        colReissue.setCellValueFactory(new PropertyValueFactory<Books, Integer>("reissue"));
	}
	
	Date now = new Date(new java.util.Date().getTime());
    
    @FXML
    void btnSearch(ActionEvent event) {

    }

}
