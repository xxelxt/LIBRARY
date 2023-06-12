package library.application;

import java.net.URL;
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
import library.publication.Books;

public class MainSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Books> booksTableView;

    @FXML
    private TableColumn<Books, String> colCategory;

    @FXML
    private TableColumn<Books, String> colCountry;

    @FXML
    private TableColumn<Books, String> colID;

    @FXML
    private TableColumn<Books, String> colName;
    
    @FXML
    void btnAddBook(ActionEvent event) {

    }

    private ObservableList<Books> data;
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
		List<Books> allBooks = mainDb.loadAllBooks();
        
        for (Books book: allBooks){
            data.add(book);
        }

        // Bind the ObservableList to the TableView
        booksTableView.setItems(data);

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<Books ,String>("publicationID"));
        colName.setCellValueFactory(new PropertyValueFactory<Books ,String>("title"));	
	}

}
