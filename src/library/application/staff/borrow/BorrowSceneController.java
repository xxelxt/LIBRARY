package library.application.staff.borrow;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private ComboBox<String> comboBoxReturned;

    @FXML
    private TextField fieldSearch;

    @FXML
    private AnchorPane paneAdd;

    @FXML
    private VBox paneMain;

    private ObservableList<Borrow> data;

    private ObservableList<String> items = FXCollections.observableArrayList("Mã mượn", "Mã sinh viên");
    private ObservableList<String> returnedItems = FXCollections.observableArrayList("Tất cả", "Đã trả", "Chưa trả");

    private BorrowDAO borrowDAO = new BorrowDAO();

    public void refresh() {
        data = FXCollections.observableArrayList();

        List<Borrow> allBorrow = borrowDAO.loadAllBorrow();
		System.out.println(allBorrow);

	    for (Borrow borrow : allBorrow){
	    	data.add(borrow);
	    }

	    borrowTableView.setItems(data);
    }

    public void scrollToLast() {
    	int lastIndex = borrowTableView.getItems().size() - 1;
    	borrowTableView.scrollTo(lastIndex);
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Borrow controller initialized");
        // Add a default row
		refresh();
		// editableCols();

        // Bind the ObservableList to the TableView
        borrowTableView.setItems(data);

        fieldSearch.setPromptText("Thông tin tìm kiếm");

        comboBox.setPromptText("Thuộc tính tìm kiếm");
        comboBox.setItems(items);
        comboBox.setValue("Mã sinh viên");

        comboBoxReturned.setPromptText("Thuộc tính tìm kiếm");
        comboBoxReturned.setItems(returnedItems);
        comboBoxReturned.setValue("Tất cả");

//        fieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
//            String searchText = newValue;
//            String searchOption = comboBox.getValue();
//            SearchData(searchText, searchOption);
//        });

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
        // SearchData(searchText, searchOption);
    }

}
