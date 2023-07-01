package library.application.staff.borrow;

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
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import library.application.util.DatePickerTableCell;
import library.application.util.VariableManager;
import library.central.Borrow;
import library.mysql.dao.BorrowDAO;
import library.mysql.dao.StudentDAO;
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
    private TableColumn<Borrow, Boolean> colFineStatus;

    @FXML
    private TableColumn<Borrow, Integer> colID;

    @FXML
    private TableColumn<Borrow, Integer> colPublicationID;

    @FXML
    private TableColumn<Publication, String> colPublicationTitle;

    @FXML
    private TableColumn<Borrow, Boolean> colReturnedStatus;

    @FXML
    private TableColumn<Borrow, Date> colStartDate;
    
    @FXML
    private TableColumn<Borrow, Date> colReturnedDate;
    
    @FXML
    private TableColumn<Borrow, Date> colDueDate;


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

    private ObservableList<Borrow> data = FXCollections.observableArrayList();

    private ObservableList<String> items = FXCollections.observableArrayList("Mã mượn", "Mã sinh viên", "Tình trạng trả");

    private BorrowDAO borrowDAO = new BorrowDAO();
    private StudentDAO studentDAO = new StudentDAO();

    public void checkDateAndUpdateFine(Borrow borrow, Date lastModified) {
		LocalDate nowDate = LocalDate.now();
		LocalDate dueDate = borrow.getDueDate().toLocalDate();
		
		if (nowDate.isBefore(dueDate)) {
			return;
		}
		
		long daysDifference = 0;
		
		if (lastModified == null) {
	    	if (borrow.getReturnedDate() == null) {
	    		daysDifference = nowDate.toEpochDay() 
	    						- dueDate.toEpochDay(); 
	    		// Now - Due
	    	} else {
        		daysDifference = borrow.getReturnedDate().toLocalDate().toEpochDay() 
        					- dueDate.toEpochDay();
        		// Return - Due
	    	}	
		} else {
	    	if (borrow.getReturnedDate() == null) {
	    		daysDifference = nowDate.toEpochDay() 
	    						- lastModified.toLocalDate().toEpochDay(); 
	    		// Now - Modified
	    	} else {
	    		LocalDate returnDate = borrow.getReturnedDate().toLocalDate();

    			if (returnDate.isAfter(lastModified.toLocalDate())) {
    				daysDifference = returnDate.toEpochDay()
    						- lastModified.toLocalDate().toEpochDay();
    			}
    			// Return - Modified

	    	}	
		}
				
		try {
    		if (daysDifference > 0){
    			borrow.setFineStatus(true);
				borrowDAO.updateBorrowFineStatus(borrow.getBorrowID());
    			studentDAO.addToStudentFine(borrow.getStudentID(), daysDifference);
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void refresh() {
        data = FXCollections.observableArrayList();

        Date lastModifiedDate = VariableManager.getLastModifiedDate();
        System.out.println("LAST MODIFIED: " + (lastModifiedDate != null ? lastModifiedDate.toString() : "") );
        
        List<Borrow> allBorrow = borrowDAO.loadAllBorrow();
        
	    for (Borrow borrow : allBorrow){
	    	checkDateAndUpdateFine(borrow, lastModifiedDate);
	    	data.add(borrow);
	    }

	    borrowTableView.setItems(data);
		
		VariableManager.saveDate(Date.valueOf(LocalDate.now()));
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
//        colStudentID.setCellFactory(TextFieldTableCell.forTableColumn());

        setupEditableColumn(colPublicationID, new IntegerStringConverter(), Borrow::setPublicationID);
        setupEditableColumn(colBorrowQuantity, new IntegerStringConverter(), Borrow::setBorrowQuantity);

        
        EventHandler<CellEditEvent<Borrow, String>> commonHandler = e -> {
        	Borrow borrow = e.getRowValue();
        	TableColumn<Borrow, String> col = e.getTableColumn();
        	if (col == colStudentID) { borrow.setStudentID(e.getNewValue()); }

        	borrowDAO.updateBorrow(borrow);
        };
        
        EventHandler<CellEditEvent<Borrow, Date>> commonDateHandler = e -> {
        	Borrow borrow = e.getRowValue();
        	TableColumn<Borrow, Date> col = e.getTableColumn();
        	if (col == colStartDate) { borrow.setStartDate(e.getNewValue()); }
        	else if (col == colDueDate) { borrow.setDueDate(e.getNewValue()); }
        	else if (col == colReturnedDate) { borrow.setReturnedDate(e.getNewValue()); borrow.setReturnedStatus(true);}

        	borrowDAO.updateBorrow(borrow);
        	refresh();
        };

//        colStudentID.setOnEditCommit(commonHandler);
        
        colStartDate.setCellFactory(col -> new DatePickerTableCell<Borrow>(col,
        	item -> item.after(Date.valueOf(LocalDate.now()))
        	// Disable after now
        ));
        colDueDate.setCellFactory(col -> new DatePickerTableCell<Borrow>(col,
    		item -> { // Disable before start date
    			Borrow borrow = borrowTableView.getSelectionModel().getSelectedItem();
            	return item.before(borrow.getStartDate()); 
    		}
        ));
        colReturnedDate.setCellFactory(col -> new DatePickerTableCell<Borrow>(col,
    		item -> { // Disable before start date and after today
    			Borrow borrow = borrowTableView.getSelectionModel().getSelectedItem();
            	return item.before(borrow.getStartDate()) || item.after(Date.valueOf(LocalDate.now())); 
    		}
        ));
        
        colStartDate.setOnEditCommit(commonDateHandler);
        colDueDate.setOnEditCommit(commonDateHandler);
        colReturnedDate.setOnEditCommit(commonDateHandler);
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

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<>("borrowID"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colPublicationID.setCellValueFactory(new PropertyValueFactory<>("publicationID"));
        colPublicationTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colBorrowQuantity.setCellValueFactory(new PropertyValueFactory<>("borrowQuantity"));

        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        colFineStatus.setCellValueFactory(new PropertyValueFactory<>("fineStatusText"));
        colReturnedStatus.setCellValueFactory(new PropertyValueFactory<>("returnedStatusText"));
	}

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
    		borrowTableView.edit(-1, null);
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
