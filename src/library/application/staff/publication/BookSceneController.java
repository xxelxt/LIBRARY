package library.application.staff.publication;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
import javafx.scene.control.ContentDisplay;
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
import library.mysql.dao.AuthorDAO;
import library.mysql.dao.BookDAO;
import library.publication.Books;
import library.publication.Publication;

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

    private <T> void setupEditableColumn(TableColumn<Books, T> column, StringConverter<T> converter, BiConsumer<Books, T> updateAction) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(converter));

        column.setOnEditCommit(event -> {
            if (btnEdit.isSelected()) {
                TableCell<Books, T> cell = event.getTablePosition().getTableColumn().getCellFactory().call(column);
                if (cell != null) {
                    cell.commitEdit(event.getNewValue());
                }

                Books book = event.getRowValue();
                updateAction.accept(book, event.getNewValue());

                bookDAO.updateBook(book); // Call the bookDAO method to update the book in the database
            }
        });

        column.setOnEditStart(event -> {
            if (!btnEdit.isSelected()) {
                TableColumn.CellEditEvent<Books, T> cellEditEvent = event;
                TableView<Books> tableView = cellEditEvent.getTableView();
                tableView.edit(tableView.getSelectionModel().getSelectedIndex(), column);
            }
        });

        column.setOnEditCancel(event -> {
            TableCell<Books, T> cell = event.getTablePosition().getTableColumn().getCellFactory().call(column);
            if (cell != null) {
                cell.cancelEdit();
            }
        });
    }

    private StringConverter<LocalDate> getDateConverter() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
    }

    private Date getDateFromDatePicker(DatePicker datePicker) {
        LocalDate localDate = datePicker.getValue();
        if (localDate != null) {
            return java.sql.Date.valueOf(localDate);
        } else {
            return null;
        }
    }

    private void editableCols(){
        colTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        colCountry.setCellFactory(TextFieldTableCell.forTableColumn());
        colAuthors.setCellFactory(TextFieldTableCell.forTableColumn());
        colPublisher.setCellFactory(TextFieldTableCell.forTableColumn());
        colCategory.setCellFactory(TextFieldTableCell.forTableColumn());

        setupEditableColumn(colReissue, new IntegerStringConverter(), Books::setReissue);
        setupEditableColumn(colQuantity, new IntegerStringConverter(), Books::setQuantity);

     // EDIT RELEASE DATE
        colPublicationDate.setCellFactory(column -> {
            return new TableCell<>() {
                private final DatePicker datePicker = new DatePicker();

                {
                    datePicker.setConverter(getDateConverter());
                    datePicker.setOnAction(event -> {
                        commitEdit(getDateFromDatePicker(datePicker));
                    });

                    // Show/hide the DatePicker based on the ToggleButton's state
                    btnEdit.selectedProperty().addListener((obs, oldVal, newVal) -> {
                        if (!newVal) {
                            cancelEdit();
                        }
                        updateItem(getItem(), false);
                    });
                }

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if (btnEdit.isSelected()) {
                            datePicker.setValue(item.toLocalDate());
                            setGraphic(datePicker);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        } else {
                            setText(item.toString()); // Or any desired format for displaying the date
                            setGraphic(null);
                            setContentDisplay(ContentDisplay.TEXT_ONLY);
                        }
                    }
                }

                @Override
                public void startEdit() {
                    super.startEdit();
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }

                @Override
                public void cancelEdit() {
                    super.cancelEdit();
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            };
        });

        colPublicationDate.setOnEditCommit(event -> {
            if (btnEdit.isSelected()) {
                TableCell<Books, Date> cell = event.getTablePosition().getTableColumn().getCellFactory().call(colPublicationDate);
                if (cell != null) {
                    cell.commitEdit(event.getNewValue());
                }

                Books book = event.getRowValue();
                Date date = event.getNewValue();
                Instant instant = date.toInstant();
                java.util.Date utilDate = java.util.Date.from(instant);

                // Convert the date to the desired format "yyyy-MM-dd"
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(utilDate);

                book.setReleaseDate(java.sql.Date.valueOf(formattedDate));

                // Call the bookDAO method to update the book in the database
                bookDAO.updateBook(book);

                // Refresh the table view to reflect the updated data
                booksTableView.refresh();
            }
        });



        colPublicationDate.setOnEditStart(event -> {
            if (!btnEdit.isSelected()) {
                TableColumn.CellEditEvent<Books, Date> cellEditEvent = event;
                TableView<Books> tableView = cellEditEvent.getTableView();
                TableColumn<Books, Date> column = cellEditEvent.getTableColumn();
                tableView.edit(tableView.getSelectionModel().getSelectedIndex(), column);
            }
        });

        // Additional event handler for colPublicationDate
        colPublicationDate.setOnEditCancel(event -> {
            TableCell<Books, Date> cell = event.getTablePosition().getTableColumn().getCellFactory().call(colPublicationDate);
            if (cell != null) {
                cell.cancelEdit();
            }
        });

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
//        	Books book = e.getTableView().getItems().get(e.getTablePosition().getRow());
//        	book.setTitle(e.getNewValue());
//			bookDAO.updateBook(book);
//        });
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

        fieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue;
            String searchOption = comboBox.getValue();
            SearchData(searchText, searchOption);
        });

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
    void btnActionEditBook(ActionEvent event) {
    	if (btnEdit.isSelected()) {
    		booksTableView.setEditable(true);
    	} else {
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