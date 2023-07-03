package library.application.staff.borrow;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.VBox;
import library.application.util.DatePickerTableCell;
import library.application.util.IntegerFieldTableCell;
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

    /**
     * TABLE
     */

    @FXML
    private TableView<Borrow> borrowTableView;

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
    private TableColumn<Borrow, String> colStudentID;

    @FXML
    private TableColumn<Borrow, Date> colStartDate;

    @FXML
    private TableColumn<Borrow, Date> colDueDate;

    @FXML
    private TableColumn<Borrow, Date> colReturnedDate;

    /**
     * SEARCH FUNCTION
     */

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ComboBox<String> comboBoxFineStatus;

    @FXML
    private TextField fieldSearch;

    private ObservableList<String> items = FXCollections.observableArrayList("Mã mượn", "Mã sinh viên");
    private ObservableList<String> fineItems = FXCollections.observableArrayList("Tất cả", "Không bị phạt", "Bị phạt");

    /**
     * DATA
     */

    @FXML
    private AnchorPane paneAdd;

    @FXML
    private VBox paneMain;

    private ObservableList<Borrow> data = FXCollections.observableArrayList();

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
			e.printStackTrace();
		}
    }

    public void refresh() throws SQLException {
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

    private void editCell(){
    	/**
    	 * String cell
    	 */

    	colStudentID.setCellFactory(TextFieldTableCell.forTableColumn());

        EventHandler<CellEditEvent<Borrow, String>> commonHandler = e -> {
        	Borrow borrow = e.getRowValue();
        	TableColumn<Borrow, String> col = e.getTableColumn();
        	if (col == colStudentID) { borrow.setStudentID(e.getNewValue()); }

        	try {
				borrowDAO.updateBorrow(borrow);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        };

        colStudentID.setOnEditCommit(commonHandler);

        /**
         * Date cell
         */

        EventHandler<CellEditEvent<Borrow, Date>> commonDateHandler = e -> {
        	Borrow borrow = e.getRowValue();
        	TableColumn<Borrow, Date> col = e.getTableColumn();
        	if (col == colStartDate) { borrow.setStartDate(e.getNewValue()); }
        	else if (col == colDueDate) { borrow.setDueDate(e.getNewValue()); }
        	else if (col == colReturnedDate) { borrow.setReturnedDate(e.getNewValue()); borrow.setReturnedStatus(true);}

        	try {
				borrowDAO.updateBorrow(borrow);
				refresh();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        };

        /* Disable after now */
        colStartDate.setCellFactory(col -> new DatePickerTableCell<>(col,
        	item -> item.after(Date.valueOf(LocalDate.now()))

        ));

        /* Disable before start date */
        colDueDate.setCellFactory(col -> new DatePickerTableCell<>(col,
    		item -> {
    			Borrow borrow = borrowTableView.getSelectionModel().getSelectedItem();
            	return item.before(borrow.getStartDate());
    		}
        ));

        /* Disable before start date and after today */
        colReturnedDate.setCellFactory(col -> new DatePickerTableCell<>(col,
    		item -> {
    			Borrow borrow = borrowTableView.getSelectionModel().getSelectedItem();
            	return item.before(borrow.getStartDate()) || item.after(Date.valueOf(LocalDate.now()));
    		}
        ));

        colStartDate.setOnEditCommit(commonDateHandler);
        colDueDate.setOnEditCommit(commonDateHandler);
        colReturnedDate.setOnEditCommit(commonDateHandler);

        /**
         * Integer cell
         */

        EventHandler<CellEditEvent<Borrow, Integer>> commonIntegerHandler = e -> {
        	Borrow borrow = e.getRowValue();
        	TableColumn<Borrow, Integer> col = e.getTableColumn();
        	if (col == colPublicationID) { borrow.setPublicationID(e.getNewValue()); }
        	else if (col == colBorrowQuantity) { borrow.setBorrowQuantity(e.getNewValue()); }

        	try {
				borrowDAO.updateBorrow(borrow);
				refresh();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        };

        colPublicationID.setCellFactory(col -> new IntegerFieldTableCell<>());
        colBorrowQuantity.setCellFactory(col -> new IntegerFieldTableCell<>());

        colPublicationID.setOnEditCommit(commonIntegerHandler);
        colBorrowQuantity.setOnEditCommit(commonIntegerHandler);
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Borrow controller initialized");

        try {
        	refresh();
    		editCell();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

        // Bind the ObservableList to the TableView
        borrowTableView.setItems(data);

        fieldSearch.setPromptText("Thông tin tìm kiếm");

        comboBox.setPromptText("Thuộc tính tìm kiếm");
        comboBox.setItems(items);
        comboBox.setValue("Mã sinh viên");

        comboBoxFineStatus.setPromptText("Tình trạng phạt");
        comboBoxFineStatus.setItems(fineItems);
        comboBoxFineStatus.setValue("Tất cả");

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
    void comboBoxActionSearch(ActionEvent event) {
    	startSearch();
    }

    @FXML
    void inputSearch(KeyEvent event) {
    	startSearch();
    }

    private void startSearch() {
        String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();
        String searchFineStatus = comboBoxFineStatus.getValue();

        SearchData(searchText, searchOption, searchFineStatus);
    }

    private void SearchData(String searchText, String searchOption, String searchFineStatus) {
        FilteredList<Borrow> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(borrow -> {
        	if (!searchFineStatus.equals("Tất cả") && !borrow.getFineStatusText().equals(searchFineStatus)) {
        		return false;
        	}

            String originalText = "";
            switch (searchOption) {
	            case "Mã mượn":
	                originalText = Integer.toString(borrow.getBorrowID());
	                break;

	            case "Mã sinh viên":
	                originalText = borrow.getStudentID();
	                break;

	        }

            if (!originalText.isEmpty()) {
                return originalText.toLowerCase().contains(searchText.toLowerCase());
            }

            return false;
        });

        borrowTableView.setItems(filteredList);
    }

    @FXML
    void btnActionAddBorrow(ActionEvent event) {
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
    void btnActionDeleteBorrow(ActionEvent event) throws SQLException {
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
    private ToggleButton btnEdit;

}
