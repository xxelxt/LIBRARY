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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import library.application.staff.interfac.SceneFeatureGate;
import library.mysql.dao.BookDAO;
import library.mysql.dao.AuthorDAO;
import library.publication.Books;
import library.publication.Publication;
import library.application.staff.publication.IntegerSpinnerCell;

public class BookSceneController implements Initializable, SceneFeatureGate {

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
    
    private AuthorDAO authorDAO = new AuthorDAO();
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
    
    private void editableCols(){
        colTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        colCountry.setCellFactory(TextFieldTableCell.forTableColumn());
        colAuthors.setCellFactory(TextFieldTableCell.forTableColumn());
        colPublisher.setCellFactory(TextFieldTableCell.forTableColumn());
        colCategory.setCellFactory(TextFieldTableCell.forTableColumn());

//        colPublicationDate.setCellFactory(TextFieldTableCell.forTableColumn());
//        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        colReissue.setCellValueFactory(new PropertyValueFactory<Books, Integer>("reissue"));
        
        EventHandler<CellEditEvent<Books, String>> commonHandler = e -> {
        	Books book = e.getTableView().getItems().get(e.getTablePosition().getRow());
        	TableColumn<Books, String> col = e.getTableColumn();
        	if (col == colTitle) { book.setTitle(e.getNewValue()); }
        	else if (col == colCountry) { book.setCountry(e.getNewValue()); }
        	else if (col == colPublisher) { book.setPublisher(e.getNewValue()); }
        	else if (col == colCategory) { book.setCategory(e.getNewValue()); }
        	
        	if (col == colAuthors) { 
        		authorDAO.addManyAuthorWithCheck(book.getPublicationID(), e.getNewValue()); 
        	} else {
            	bookDAO.updateBook(book);	
        	}
        };

        colTitle.setOnEditCommit(commonHandler);
        colCountry.setOnEditCommit(commonHandler);
        colAuthors.setOnEditCommit(commonHandler);
        colPublisher.setOnEditCommit(commonHandler);
        colCategory.setOnEditCommit(commonHandler);


        
//        colReissue.setCellFactory(new IntegerSpinnerCell);
//        colReissue.setOnEditCommit(e-> { 	
////        	Books book = e.getTableView().getItems().get(e.getTablePosition().getRow());
////        	book.setTitle(e.getNewValue()); 
////        	bookDAO.updateBook(book);
//        });

        /* Allow for the values in each cell to be changable */
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Book controller initialized");
        // Add a default row
		refresh();
		editableCols();
        // Bind the ObservableList to the TableView
        booksTableView.setItems(data);
        
        fieldSearch.setPromptText("Thông tin tìm kiếm");
        
        comboBox.setPromptText("Thuộc tính tìm kiếm");
        comboBox.setItems(items);
        comboBox.setValue("Tên sách");
        
//        fieldSearch.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                String searchText = newValue;
//                String searchOption = comboBox.getValue();
//                SearchData(searchText, searchOption);
//            }
//        });

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
//        colID.setCellFactory(TextFieldTableCell.forTableColumn());
//        colTitle.setCellFactory(TextFieldTableCell.forTableColumn());
//        colPublicationDate.setCellFactory(TextFieldTableCell.forTableColumn());
//        colCountry.setCellFactory(TextFieldTableCell.forTableColumn());
//        colQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
//        
//        colAuthors.setCellFactory(TextFieldTableCell.forTableColumn());
//        colPublisher.setCellFactory(TextFieldTableCell.forTableColumn());
//        colCategory.setCellFactory(TextFieldTableCell.forTableColumn());
//        colReissue.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	Date now = new Date(new java.util.Date().getTime());
	
    @FXML
    void btnActionAddBook(ActionEvent event) {
    	paneMain.setVisible(false);
    	paneAdd.setVisible(true);
    }
    
    @FXML
    void btnActionReturn(ActionEvent event) {
    	paneMain.setVisible(true);
    	paneAdd.setVisible(false);
    	this.refresh();
    }
    
    @FXML
    void btnActionDeleteBook(ActionEvent event) {
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
    void btnActionEditBook(ActionEvent event) {
    	if (btnEdit.isSelected()) {
    		booksTableView.setEditable(true);
    	} else {
    		booksTableView.setEditable(false);
    	}
    }
    
    
    @FXML
    void inputSearch(KeyEvent event) {
        String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();
        SearchData(searchText, searchOption);
    }
    
    private void SearchData(String searchText, String searchOption) {
        FilteredList<Books> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(book -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            if (searchOption.equals("ID")) {
	            try {
	                int id = Integer.parseInt(searchText);
	                return book.getPublicationID() == id;
	            } catch (NumberFormatException e) {
	                return false;
	            }
            } else {
            	String originalText = "";
            	switch (searchOption) {
	    	        case "Tên sách":	originalText = book.getTitle(); break;
	    	        case "Tác giả":		originalText = book.getAuthors(); break;	
	    	        case "Quốc gia":	originalText = book.getCountry(); break;
	    	        case "Thể loại":	originalText = book.getCategory(); break;
	        	}
            	if (originalText != "") {
            		return originalText.toLowerCase().contains(searchText.toLowerCase());
            	}
            	return false;
            }
        });

        booksTableView.setItems(filteredList);
    }

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private ToggleButton btnEdit;
    
    @FXML
    private HBox hboxFeature;

	@Override
	public void setFeatureFor(Integer user) {
		// TODO Auto-generated method stub
		if (user == CLERK) {
			hboxFeature.getChildren().remove(btnAdd);
			hboxFeature.getChildren().remove(btnEdit);
			hboxFeature.getChildren().remove(btnDelete);
		} else if (user == STUDENT) {
			hboxFeature.getChildren().remove(btnAdd);
			hboxFeature.getChildren().remove(btnEdit);
			hboxFeature.getChildren().remove(btnDelete);
		}
	}
}