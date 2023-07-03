package library.application.staff.borrow;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import library.application.staff.info.StudentInfoSceneController;
import library.central.Borrow;
import library.mysql.dao.BorrowDAO;
import library.publication.Publication;

public class BorrowHistorySceneController implements Initializable {

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
    private TableColumn<Borrow, Date> colStartDate;

    @FXML
    private TableColumn<Borrow, Date> colDueDate;

    @FXML
    private TableColumn<Borrow, Date> colReturnedDate;

    /**
     * SEARCH FUNCTION
     */

    @FXML
    private TextField fieldSearch;

    /**
     * DATA
     */

    private ObservableList<Borrow> data;

    private BorrowDAO borrowDAO = new BorrowDAO();
    private String thisStudentID = "";

    public void setThisStudentID(String thisStudentID) {
		this.thisStudentID = thisStudentID;
	}

	public void refresh() throws SQLException {
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
        System.out.println("Borrow history controller initialized");

		try {
			refresh();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        // Bind the ObservableList to the TableView
        borrowTableView.setItems(data);

        fieldSearch.setPromptText("Tìm kiếm theo mã mượn");

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

    	if (searchText.isEmpty()) {
            borrowTableView.setItems(data);
        } else {
            FilteredList<Borrow> filteredList = new FilteredList<>(data);

            filteredList.setPredicate(borrow -> {
	            String originalText = "";
	            originalText = Integer.toString(borrow.getBorrowID());

                if (!originalText.isEmpty()) {
                	return originalText.toLowerCase().contains(searchText.toLowerCase());
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
