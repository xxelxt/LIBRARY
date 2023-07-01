package library.application.staff.publication;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import library.application.staff.interfac.SceneFeatureGate;
import library.application.util.DatePickerTableCell;
import library.application.util.IntegerFieldTableCell;
import library.mysql.dao.AuthorDAO;
import library.mysql.dao.BookDAO;
import library.publication.Books;
import library.publication.Publication;
import library.user.Student;

public class BookSceneController implements Initializable, SceneFeatureGate {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    // Table
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

    // Searching
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    private ObservableList<String> comboBoxItems = FXCollections.observableArrayList("ID", "Tên sách", "Tác giả", "Quốc gia", "Thể loại");

    // PANES & DATA
    @FXML
    private VBox paneMain;

    @FXML
    private AnchorPane paneAdd;

    private ObservableList<Books> data;

    private AuthorDAO authorDAO = new AuthorDAO();
    private BookDAO bookDAO = new BookDAO();

    public void refresh() {
        data = FXCollections.observableArrayList();

        List<Books> allBooks = bookDAO.loadAllBooks();

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

        EventHandler<CellEditEvent<Books, String>> commonHandler = e -> {
        	Books book = e.getRowValue();
        	TableColumn<Books, String> col = e.getTableColumn();
        	if (col == colTitle) { book.setTitle(e.getNewValue()); }
        	else if (col == colCountry) { book.setCountry(e.getNewValue()); }
        	else if (col == colCategory) { book.setCategory(e.getNewValue()); }
        	else if (col == colPublisher) {
        		try {
        			String publisherName = e.getNewValue();
        			book.setPublisher(publisherName);
        			Integer publisherID = bookDAO.addPublisherWithCheck(publisherName);
					bookDAO.updateBookPublisherID(book, publisherID);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}

        	if (col == colAuthors) {
        		authorDAO.addManyAuthorWithCheck(book.getPublicationID(), e.getNewValue());
        		refresh();
        	} else {
            	bookDAO.updateBook(book);
        	}
        };

        colTitle.setOnEditCommit(commonHandler);
        colCountry.setOnEditCommit(commonHandler);
        colAuthors.setOnEditCommit(commonHandler);
        colPublisher.setOnEditCommit(commonHandler);
        colCategory.setOnEditCommit(commonHandler);
        


        colPublicationDate.setCellFactory(col -> new DatePickerTableCell<Books>(col));
        colPublicationDate.setOnEditCommit(e -> {
            if (btnEdit.isSelected()) {
                Books book = e.getRowValue();
                book.setReleaseDate(e.getNewValue());

                bookDAO.updateBook(book);
            }
        });
        
        EventHandler<CellEditEvent<Books, Integer>> commonIntegerHandler = e -> {
        	Books book = e.getRowValue();
        	TableColumn<Books, Integer> col = e.getTableColumn();
        	if (col == colReissue) { book.setReissue(e.getNewValue()); }
        	else if (col == colQuantity) { book.setQuantity(e.getNewValue()); }
        	
        	bookDAO.updateBook(book);
        };
        
        colReissue.setCellFactory(col -> new IntegerFieldTableCell<Books>());
        colQuantity.setCellFactory(col -> new IntegerFieldTableCell<Books>());
        
        colReissue.setOnEditCommit(commonIntegerHandler);
        colQuantity.setOnEditCommit(commonIntegerHandler);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Book controller initialized");
        // Add a default row
		refresh();
		editableCols();

        // Bind the ObservableList to the TableView
        booksTableView.setItems(data);

        comboBox.setItems(comboBoxItems);
        comboBox.setValue("Tên sách");

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<>("publicationID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        colAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colReissue.setCellValueFactory(new PropertyValueFactory<>("reissue"));
	}

    @FXML
    void inputSearch(KeyEvent event) {
        String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();
        SearchData(searchText, searchOption);
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
    void btnActionEditBooks(ActionEvent event) {
    	if (btnEdit.isSelected()) {
    		booksTableView.setEditable(true);
    	} else {
    		booksTableView.edit(-1, null);
    		booksTableView.setEditable(false);
    	}
    }

    private void SearchData(String searchText, String searchOption) {
        if (searchText.isEmpty()) {
            // If the search text is empty, revert to the original unfiltered list
            booksTableView.setItems(data);
        } else {
            // Apply filtering based on the search text and option
            FilteredList<Books> filteredList = new FilteredList<>(data);

            filteredList.setPredicate(book -> {
                String originalText = "";
                switch (searchOption) {
                    case "ID":
                        originalText = Integer.toString(book.getPublicationID());
                        break;

                    case "Tên sách":
                        originalText = book.getTitle();
                        break;

                    case "Tác giả":
                        originalText = book.getAuthors();
                        break;

                    case "Quốc gia":
                        originalText = book.getCountry();
                        break;

                    case "Thể loại":
                        originalText = book.getCategory();
                        break;
                }

                if (!originalText.isEmpty()) {
                    if (searchOption.equals("ID")) {
                        return originalText.startsWith(searchText);
                    } else {
                        return originalText.toLowerCase().contains(searchText.toLowerCase());
                    }
                }

                return false;
            });

            booksTableView.setItems(filteredList);
        }
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