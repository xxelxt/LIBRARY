package library.application.staff.publication;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import library.application.staff.interfac.SceneFeatureGate;
import library.application.util.DatePickerTableCell;
import library.application.util.IntegerFieldTableCell;
import library.mysql.dao.AuthorDAO;
import library.mysql.dao.BookDAO;
import library.publication.Books;
import library.publication.Publication;

public class BookSceneController implements Initializable, SceneFeatureGate {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /**
     * TABLE
     */

    @FXML
    private TableView<Books> booksTableView;

    @FXML
    private TableColumn<Books, String> colAuthors;

    @FXML
    private TableColumn<Books, String> colCategory;

    @FXML
    private TableColumn<Books, String> colCountry;

    @FXML
    private TableColumn<Books, Integer> colID;

    @FXML
    private TableColumn<Books, Date> colPublicationDate;

    @FXML
    private TableColumn<Books, String> colPublisher;

    @FXML
    private TableColumn<Books, Integer> colQuantity;

    @FXML
    private TableColumn<Books, Integer> colReissue;

    @FXML
    private TableColumn<Books, String> colTitle;

    /**
     * SEARCH FUNCTION
     */

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    private ObservableList<String> comboBoxItems = FXCollections.observableArrayList("ID", "Tên sách", "Tác giả", "Quốc gia", "Thể loại");

    /**
     * DATA
     */

    @FXML
    private VBox paneMain;

    @FXML
    private AnchorPane paneAdd;

    private ObservableList<Books> data;

    private AuthorDAO authorDAO = new AuthorDAO();
    private BookDAO bookDAO = new BookDAO();

    public void refresh() throws SQLException {
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

    private void editCell() throws SQLException {
    	/**
    	 * String cells
    	 */

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
					e1.printStackTrace();
				}
        	}

        	if (col == colAuthors) {
        		try {
            		authorDAO.addManyAuthorWithCheck(book.getPublicationID(), e.getNewValue());
    				refresh();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			}
        	} else {
        		try {
            		bookDAO.updateBook(book);
    				refresh();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			}
        	}
        };

        colTitle.setOnEditCommit(commonHandler);
        colCountry.setOnEditCommit(commonHandler);
        colAuthors.setOnEditCommit(commonHandler);
        colPublisher.setOnEditCommit(commonHandler);
        colCategory.setOnEditCommit(commonHandler);

        /**
         * Date cells
         */

        colPublicationDate.setCellFactory(col -> new DatePickerTableCell<>(col));

        colPublicationDate.setOnEditCommit(e -> {
            if (btnEdit.isSelected()) {
                Books book = e.getRowValue();
                book.setReleaseDate(e.getNewValue());

                try {
            		bookDAO.updateBook(book);
    				refresh();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			}
            }
        });

        /**
         * Integer cells
         */

        EventHandler<CellEditEvent<Books, Integer>> commonIntegerHandler = e -> {
        	Books book = e.getRowValue();
        	TableColumn<Books, Integer> col = e.getTableColumn();
        	if (col == colReissue) { book.setReissue(e.getNewValue()); }
        	else if (col == colQuantity) { book.setQuantity(e.getNewValue()); }

        	try {
        		bookDAO.updateBook(book);
				refresh();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        };

        colReissue.setCellFactory(col -> new IntegerFieldTableCell<>());
        colQuantity.setCellFactory(col -> new IntegerFieldTableCell<>());

        colReissue.setOnEditCommit(commonIntegerHandler);
        colQuantity.setOnEditCommit(commonIntegerHandler);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Book controller initialized");

		try {
			refresh();
			editCell();
		} catch (SQLException e) {
			e.printStackTrace();
		}

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

    private void SearchData(String searchText, String searchOption) {
        if (searchText.isEmpty()) {
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
    void btnActionAddBook(ActionEvent event) {
    	paneMain.setVisible(false);
    	paneAdd.setVisible(true);
    }

    @FXML
    void btnActionReturn(ActionEvent event) throws SQLException {
    	paneMain.setVisible(true);
    	paneAdd.setVisible(false);
    	this.refresh();
    }

    @FXML
    void btnActionDeleteBook(ActionEvent event) throws SQLException {
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

    @FXML
    void btnActionEditBooksClerk(ActionEvent event) {
    	if (btnEditClerk.isSelected()) {
    		booksTableView.setEditable(true);
    	} else {
    		booksTableView.edit(-1, null);
    		booksTableView.setEditable(false);
    	}
    }

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private ToggleButton btnEdit;

    @FXML
    private ToggleButton btnEditClerk;

    @FXML
    private HBox hboxFeature;

	@Override
	public void setFeatureFor(Integer user) {
		if (user == LIBRARIAN) {
			hboxFeature.getChildren().remove(btnEditClerk);
		} else {
			hboxFeature.getChildren().remove(btnAdd);
			hboxFeature.getChildren().remove(btnEdit);
			hboxFeature.getChildren().remove(btnDelete);

			if (user == CLERK) {

			    colQuantity.setEditable(true);

			    colID.setEditable(false);
			    colTitle.setEditable(false);
			    colAuthors.setEditable(false);
			    colCountry.setEditable(false);
			    colCategory.setEditable(false);
			    colPublisher.setEditable(false);
			    colPublicationDate.setEditable(false);
			    colReissue.setEditable(false);

			} else if (user == STUDENT) {

			}
		}
	}
}