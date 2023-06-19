package library.application.staff.staff;

import java.net.URL;
import java.sql.Date;
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
import library.mysql.dao.StaffDAO;
import library.user.Staff;

public class StaffSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Staff> staffTableView;

    @FXML
    private TableColumn<Staff, String> colAddress;

    @FXML
    private TableColumn<Staff, String> colEmail;

    @FXML
    private TableColumn<Staff, Boolean> colGender;

    @FXML
    private TableColumn<Staff, String> colName;

    @FXML
    private TableColumn<Staff, String> colPassword;

    @FXML
    private TableColumn<Staff, String> colPhoneNum;

    @FXML
    private TableColumn<Staff, String> colPosition;

    @FXML
    private TableColumn<Staff, Integer> colStaffID;

    @FXML
    private TableColumn<Staff, String> colUsername;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;
    
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private ToggleButton btnEdit;

    @FXML
    private AnchorPane paneAdd;

    @FXML
    private VBox paneMain;
    
    private ObservableList<Staff> data;

    private ObservableList<String> items = FXCollections.observableArrayList("Mã nhân viên", "Tên nhân viên", "Số điện thoại");

    private StaffDAO staffDAO = new StaffDAO();
    
    public void refresh() {
        data = FXCollections.observableArrayList();

        List<Staff> allStaff = staffDAO.loadAllStaff();

	    for (Staff staff : allStaff){
	    	data.add(staff);
	    }

	    staffTableView.setItems(data);
    }
    
    public void scrollToLast() {
    	int lastIndex = staffTableView.getItems().size() - 1;
    	staffTableView.scrollTo(lastIndex);
    }
    
    private void editableCols(){
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colPhoneNum.setCellFactory(TextFieldTableCell.forTableColumn());
        colAddress.setCellFactory(TextFieldTableCell.forTableColumn());

//        colGender.setCellFactory(TextFieldTableCell.forTableColumn());

        EventHandler<CellEditEvent<Staff, String>> commonHandler = e -> {
        	Staff stf = e.getTableView().getItems().get(e.getTablePosition().getRow());
        	TableColumn<Staff, String> col = e.getTableColumn();
        	if (col == colName) { stf.setName(e.getNewValue()); }
        	else if (col == colEmail) { stf.setEmail(e.getNewValue()); }
        	else if (col == colPhoneNum) { stf.setPhone(e.getNewValue()); }
        	else if (col == colAddress) { stf.setAddress(e.getNewValue()); }

        	staffDAO.updateStaff(stf);
        };

        colName.setOnEditCommit(commonHandler);
        colEmail.setOnEditCommit(commonHandler);
        colPhoneNum.setOnEditCommit(commonHandler);
        colAddress.setOnEditCommit(commonHandler);
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Staff controller initialized");
        // Add a default row
		refresh();
		editableCols();

        // Bind the ObservableList to the TableView
        staffTableView.setItems(data);

        fieldSearch.setPromptText("Thông tin tìm kiếm");

        comboBox.setPromptText("Thuộc tính tìm kiếm");
        comboBox.setItems(items);
        comboBox.setValue("Tên nhân viên");

        fieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue;
            String searchOption = comboBox.getValue();
            SearchData(searchText, searchOption);
        });

        // Bind the columns to the corresponding properties in MyDataModel
        colStaffID.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));

        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        colGender.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "Nữ" : "Nam" );
            }
        });
	}

	Date now = new Date(new java.util.Date().getTime());

    @FXML
    void btnActionAddStaff(ActionEvent event) {
    	paneMain.setVisible(false);
    	paneAdd.setVisible(true);
    }

    @FXML
    void btnActionDeleteStaff(ActionEvent event) {
    	Integer selectedIndex = staffTableView.getSelectionModel().getSelectedIndex();
    	Staff selectedRow = staffTableView.getSelectionModel().getSelectedItem();

    	if (selectedRow != null) {
	    	staffDAO.deleteStaff(selectedRow.getStaffID());
	    	this.refresh();

	        if (selectedIndex >= 0 && selectedIndex < data.size()) {
	        	staffTableView.getSelectionModel().select(selectedIndex);
	        }
    	} else {
    		staffTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void btnActionEditStaff(ActionEvent event) {
    	if (btnEdit.isSelected()) {
    		staffTableView.setEditable(true);
    	} else {
    		staffTableView.setEditable(false);
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
            staffTableView.setItems(data);
        } else {
            // Apply filtering based on the search text and option
            FilteredList<Staff> filteredList = new FilteredList<>(data);

            filteredList.setPredicate(staff -> {
                String originalText = "";
                switch (searchOption) {
                    case "Mã nhân viên":
                        originalText = Integer.toString(staff.getStaffID());
                        break;

                    case "Tên nhân viên":
                        originalText = staff.getName();
                        break;

                    case "Số điện thoại":
                        originalText = staff.getPhone();
                        break;
                }

                if (!originalText.isEmpty()) {
                    if (searchOption.equals("Mã nhân viên")) {
                        return originalText.startsWith(searchText);
                    } else {
                        return originalText.toLowerCase().contains(searchText.toLowerCase());
                    }
                }

                return false;
            });

            staffTableView.setItems(filteredList);
        }
    }

}
