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
import library.application.staff.info.StudentInfoSceneController;
import library.application.util.DatePickerTableCell;
import library.central.Borrow;
import library.mysql.dao.BorrowDAO;
import library.publication.Publication;

public class BorrowHistorySceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Borrow> borrowTableView;

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
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    private ObservableList<Borrow> data;

    private ObservableList<String> items = FXCollections.observableArrayList("Mã mượn", "Tình trạng trả");

    private BorrowDAO borrowDAO = new BorrowDAO();
    private String thisStudentID = "";

    public void setThisStudentID(String thisStudentID) {
		this.thisStudentID = thisStudentID;
	}

	public void refresh() {
        data = FXCollections.observableArrayList();

        List<Borrow> allBorrow = borrowDAO.loadAllBorrow();

	    for (Borrow borrow : allBorrow){
	    	if (borrow.getStudentID().equals(thisStudentID)) {
	    		data.add(borrow);	
	    	}
	    }

	    borrowTableView.setItems(data);
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Borrow History controller initialized");
        // Add a default row
		refresh();

        // Bind the ObservableList to the TableView
        borrowTableView.setItems(data);

        fieldSearch.setPromptText("Thông tin tìm kiếm");

        comboBox.setPromptText("Thuộc tính tìm kiếm");
        comboBox.setItems(items);
        comboBox.setValue("Mã mượn");

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<>("borrowID"));
        colPublicationID.setCellValueFactory(new PropertyValueFactory<>("publicationID"));
        colPublicationTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colBorrowQuantity.setCellValueFactory(new PropertyValueFactory<>("borrowQuantity"));

        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        colFineStatus.setCellValueFactory(new PropertyValueFactory<>("fineStatusText"));
        colReturnedStatus.setCellValueFactory(new PropertyValueFactory<>("returnedStatusText"));
	}

	Date now = new Date(new java.util.Date().getTime());

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
    
    private StudentInfoSceneController parent;
    
    public void setParent(StudentInfoSceneController parent) {
		this.parent = parent;
	}

	@FXML
    void btnActionReturn(ActionEvent event) {
		parent.btnActionReturn();
    }

}
