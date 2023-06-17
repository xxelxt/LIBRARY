package library.application.staff.publication;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import library.mysql.dao.BookDAO;
import library.publication.Books;
import library.publication.Publication;


public class BookSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Books> booksTableView;
    
    @FXML
    private ComboBox<String> comboBox;
    
    @FXML
    private TextField fieldSearch;

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
    
    @FXML
    private VBox paneMain;
    
    @FXML
    private AnchorPane paneAdd;
    
    private ObservableList<Books> data;
    
    private ObservableList<String> items = FXCollections.observableArrayList("ID", "Tên sách", "Tác giả", "Quốc gia", "Thể loại");
    
    private BookDAO bookDAO = new BookDAO();

    public void refresh() {
        data = FXCollections.observableArrayList();

        List<Books> allBooks = bookDAO.loadAllBooks();
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
        System.out.println("Book controller initialized");
        // Add a default row
		refresh();
        
        // Bind the ObservableList to the TableView
        booksTableView.setItems(data);
        
        fieldSearch.setPromptText("Thông tin tìm kiếm");
        
        comboBox.setPromptText("Thuộc tính tìm kiếm");
        comboBox.setItems(items);
        comboBox.setValue("Tên sách");
        
        fieldSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String searchText = newValue;
                String searchOption = comboBox.getValue();
                SearchData(searchText, searchOption);
            }
        });

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<>("publicationID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        colAuthors.setCellValueFactory(new PropertyValueFactory<Books, String>("authors"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<Books, String>("publisher"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Books, String>("category"));
        colReissue.setCellValueFactory(new PropertyValueFactory<Books, Integer>("reissue"));
        
        // Editing ;
        colID.setCellFactory(TextFieldTableCell.forTableColumn());
        colTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        colPublicationDate.setCellFactory(TextFieldTableCell.forTableColumn());
        colCountry.setCellFactory(TextFieldTableCell.forTableColumn());
        colQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
        
        colAuthors.setCellFactory(TextFieldTableCell.forTableColumn());
        colPublisher.setCellFactory(TextFieldTableCell.forTableColumn());
        colCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        colReissue.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	Date now = new Date(new java.util.Date().getTime());
	
    @FXML
    void btnAddBook(ActionEvent event) {
    	paneMain.setVisible(false);
    	paneAdd.setVisible(true);
    }
    
    @FXML
    void btnReturn(ActionEvent event) {
    	paneMain.setVisible(true);
    	paneAdd.setVisible(false);
    	this.refresh();
    }
    
    @FXML
    void btnDeleteBook(ActionEvent event) {
    	Integer selectedIndex = booksTableView.getSelectionModel().getSelectedIndex();
    	Publication selectedRow = booksTableView.getSelectionModel().getSelectedItem();
    	
    	if (selectedRow != null) {
	    	bookDAO.deleteBook(selectedRow.getPublicationID());
	    	this.refresh();
	    	
	        if (selectedIndex >= 0 && selectedIndex < data.size()) {
	            booksTableView.getSelectionModel().select(selectedIndex);
	        } 
    	} else {
        	booksTableView.getSelectionModel().clearSelection();
        }
    }
    
    @FXML
    void btnEditBook(ActionEvent event) {
    	booksTableView.setEditable(true);
    }
    
    private void filterBooksbyID(String idText) {
        FilteredList<Books> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(book -> {
            if (idText == null || idText.isEmpty()) {
                return true;
            }
            try {
                int id = Integer.parseInt(idText);
                return book.getPublicationID() == id;
            } catch (NumberFormatException e) {
                return false;
            }
        });

        booksTableView.setItems(filteredList);
    }

    
    private void filterBooksbyTitle(String title) {
        FilteredList<Books> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(book -> {
            if (title == null || title.isEmpty()) {
                return true;
            }
            String lowerCaseTitle = title.toLowerCase();
            return book.getTitle().toLowerCase().contains(lowerCaseTitle);
        });

        booksTableView.setItems(filteredList);
    }
    
    private void filterBooksbyCountry(String country) {
        FilteredList<Books> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(book -> {
            if (country == null || country.isEmpty()) {
                return true;
            }
            String lowerCaseTitle = country.toLowerCase();
            return book.getCountry().toLowerCase().contains(lowerCaseTitle);
        });

        booksTableView.setItems(filteredList);
    }
    
    private void filterBooksbyCategory(String category) {
        FilteredList<Books> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(book -> {
            if (category == null || category.isEmpty()) {
                return true;
            }
            String lowerCaseTitle = category.toLowerCase();
            return book.getCategory().toLowerCase().contains(lowerCaseTitle);
        });

        booksTableView.setItems(filteredList);
    }
    
    private void SearchData(String searchText, String searchOption) {
    	switch (searchOption) {
        case "ID":
        	filterBooksbyID(searchText);
            break;
        case "Tên sách":
            filterBooksbyTitle(searchText);
            break;
        case "Tác giả":
            
            break;
        case "Quốc gia":
        	filterBooksbyCountry(searchText);
            break;
        case "Thể loại":
        	filterBooksbyCategory(searchText);
            break;
    	}
    }
}