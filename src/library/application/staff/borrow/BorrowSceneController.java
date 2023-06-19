package library.application.staff.borrow;

import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import library.central.Borrow;
import library.mysql.dao.BorrowDAO;
import library.publication.Publication;

public class BorrowSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Borrow> borrowTableView;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private ToggleButton btnEdit;

    @FXML
    private TableColumn<Borrow, Integer> colBorrowQuantity;

    @FXML
    private TableColumn<Borrow, Date> colDueDate;

    @FXML
    private TableColumn<Borrow, Boolean> colFineStatus;

    @FXML
    private TableColumn<Borrow, Integer> colID;

    @FXML
    private TableColumn<Borrow, Integer> colPublicationID;

    @FXML
    private TableColumn<Publication, String> colPublicationTitle;

    @FXML
    private TableColumn<Borrow, Date> colReturnedDate;

    @FXML
    private TableColumn<Borrow, Boolean> colReturnedStatus;

    @FXML
    private TableColumn<Borrow, Date> colStartDate;

    @FXML
    private TableColumn<Borrow, String> colStudentID;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    @FXML
    private AnchorPane paneAdd;

    @FXML
    private VBox paneMain;

    private ObservableList<Borrow> data;

    private ObservableList<String> items = FXCollections.observableArrayList("Mã mượn", "Mã sinh viên", "Tình trạng trả");

    private BorrowDAO borrowDAO = new BorrowDAO();

    public void refresh() {
        data = FXCollections.observableArrayList();

        List<Borrow> allBorrow = borrowDAO.loadAllBorrow();

	    for (Borrow borrow : allBorrow){
	    	data.add(borrow);
	    }

	    borrowTableView.setItems(data);
    }

    public void scrollToLast() {
    	int lastIndex = borrowTableView.getItems().size() - 1;
    	borrowTableView.scrollTo(lastIndex);
    }
    
    private <T> void setupEditableColumn(TableColumn<Borrow, T> column, StringConverter<T> converter, BiConsumer<Borrow, T> updateAction) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(converter));

        column.setOnEditCommit(event -> {
            if (btnEdit.isSelected()) {
                TableCell<Borrow, T> cell = event.getTablePosition().getTableColumn().getCellFactory().call(column);
                if (cell != null) {
                    cell.commitEdit(event.getNewValue());
                }

                Borrow borrow = event.getRowValue();
                updateAction.accept(borrow, event.getNewValue());

                borrowDAO.updateBorrow(borrow);
                this.refresh();            }
        });

        column.setOnEditStart(event -> {
            if (!btnEdit.isSelected()) {
                TableColumn.CellEditEvent<Borrow, T> cellEditEvent = event;
                TableView<Borrow> tableView = cellEditEvent.getTableView();
                tableView.edit(tableView.getSelectionModel().getSelectedIndex(), column);
            }
        });

        column.setOnEditCancel(event -> {
            TableCell<Borrow, T> cell = event.getTablePosition().getTableColumn().getCellFactory().call(column);
            if (cell != null) {
                cell.cancelEdit();
            }
        });
    }
    
    private void editableCols(){
        colStudentID.setCellFactory(TextFieldTableCell.forTableColumn());
        
        setupEditableColumn(colPublicationID, new IntegerStringConverter(), Borrow::setPublicationID);
        setupEditableColumn(colBorrowQuantity, new IntegerStringConverter(), Borrow::setBorrowQuantity);

        EventHandler<CellEditEvent<Borrow, String>> commonHandler = e -> {
        	Borrow borrow = e.getTableView().getItems().get(e.getTablePosition().getRow());
        	TableColumn<Borrow, String> col = e.getTableColumn();
        	if (col == colStudentID) { borrow.setStudentID(e.getNewValue()); }

        	borrowDAO.updateBorrow(borrow);
        };

        colStudentID.setOnEditCommit(commonHandler);
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Borrow controller initialized");
        // Add a default row
		refresh();
		editableCols();

        // Bind the ObservableList to the TableView
        borrowTableView.setItems(data);

        fieldSearch.setPromptText("Thông tin tìm kiếm");

        comboBox.setPromptText("Thuộc tính tìm kiếm");
        comboBox.setItems(items);
        comboBox.setValue("Mã sinh viên");

        fieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue;
            String searchOption = comboBox.getValue();
            SearchData(searchText, searchOption);
        });

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<>("borrowID"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colPublicationID.setCellValueFactory(new PropertyValueFactory<>("publicationID"));
        colPublicationTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colBorrowQuantity.setCellValueFactory(new PropertyValueFactory<>("borrowQuantity"));

        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        colFineStatus.setCellValueFactory(new PropertyValueFactory<>("fineStatus"));
        colReturnedStatus.setCellValueFactory(new PropertyValueFactory<>("returnedStatus"));

        colFineStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "Bị phạt" : "Không bị phạt" );
            }
        });

        colReturnedStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "Đã trả" : "Chưa trả" );
            }
        });
	}

	Date now = new Date(new java.util.Date().getTime());

    @FXML
    void btnActionAddBorrow(ActionEvent event) {
    	paneMain.setVisible(false);
    	paneAdd.setVisible(true);
    }

    @FXML
    void btnActionDeleteBorrow(ActionEvent event) {
    	Integer selectedIndex = borrowTableView.getSelectionModel().getSelectedIndex();
    	Borrow selectedRow = borrowTableView.getSelectionModel().getSelectedItem();

    	if (selectedRow != null) {
	    	borrowDAO.deleteBorrow(selectedRow.getBorrowID());
	    	this.refresh();

	        if (selectedIndex >= 0 && selectedIndex < data.size()) {
	        	borrowTableView.getSelectionModel().select(selectedIndex);
	        }
    	} else {
    		borrowTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void btnActionEditBorrow(ActionEvent event) {
    	if (btnEdit.isSelected()) {
    		borrowTableView.setEditable(true);
    	} else {
    		borrowTableView.setEditable(false);
    	}
    }

    @FXML
    void btnActionReturn(ActionEvent event) {
    	paneMain.setVisible(true);
    	paneAdd.setVisible(false);
    	this.refresh();
    }

    @FXML
    void inputSearch(KeyEvent event) {
    	String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();
        SearchData(searchText, searchOption);
    }
    
    private void SearchData(String searchText, String searchOption) {
        if (searchText.isEmpty()) {
            // If the search text is empty, revert to the original unfiltered list
            borrowTableView.setItems(data);
        } else {
            // Apply filtering based on the search text and option
            FilteredList<Borrow> filteredList = new FilteredList<>(data);

            filteredList.setPredicate(borrow -> {
                String originalText = "";
                switch (searchOption) {
                    case "Mã mượn":
                        originalText = Integer.toString(borrow.getBorrowID());
                        break;

                    case "Mã sinh viên":
                        originalText = borrow.getStudentID();
                        break;

                    case "Tình trạng trả":
                    	originalText = borrow.isReturnedStatus() ? "Đã trả" : "Chưa trả";
                        break;
                }

                if (!originalText.isEmpty()) {
                    if (searchOption.equals("Mã mượn")) {
                        return originalText.startsWith(searchText);
                    } else {
                        return originalText.toLowerCase().contains(searchText.toLowerCase());
                    }
                }

                return false;
            });

            borrowTableView.setItems(filteredList);
        }
    }

}
