package library.application.staff.staff;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import library.application.util.Toaster;
import library.mysql.dao.StaffDAO;
import library.mysql.dao.UserDAO;
import library.user.Staff;

public class StaffSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /**
     * TABLE
     */

    @FXML
    private TableView<Staff> staffTableView;

    @FXML
    private TableColumn<Staff, String> colAddress;

    @FXML
    private TableColumn<Staff, String> colEmail;

    @FXML
    private TableColumn<Staff, String> colGender;

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

    /**
     * SEARCH FUNCTION
     */

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    private ObservableList<String> items = FXCollections.observableArrayList("Mã nhân viên", "Tên nhân viên", "Chức vụ", "Số điện thoại");

    /**
     * DATA
     */

    @FXML
    private AnchorPane paneAdd;

    @FXML
    private VBox paneMain;

    private ObservableList<Staff> data;

    private StaffDAO staffDAO = new StaffDAO();

    public void refresh() throws SQLException {
        data = FXCollections.observableArrayList();

        List<Staff> allStaff;

        try {
			allStaff = staffDAO.loadAllStaff();
		} catch (SQLException e) {
			allStaff = new ArrayList<>();

			Toaster.showError("Không thể tải danh sách nhân viên", e.getMessage());
			e.printStackTrace();
		}

	    for (Staff staff : allStaff){
	    	data.add(staff);
	    }

	    staffTableView.setItems(data);
    }

    public void scrollToLast() {
    	int lastIndex = staffTableView.getItems().size() - 1;
    	staffTableView.scrollTo(lastIndex);
    }

    private void editCell() {
    	/**
    	 * String & combo box cell
    	 */

        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colPhoneNum.setCellFactory(TextFieldTableCell.forTableColumn());
        colAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        colPassword.setCellFactory(TextFieldTableCell.forTableColumn());

        EventHandler<CellEditEvent<Staff, String>> commonHandler = e -> {
        	Staff stf = e.getTableView().getItems().get(e.getTablePosition().getRow());
        	TableColumn<Staff, String> col = e.getTableColumn();
        	if (col == colName) { stf.setName(e.getNewValue()); }
        	else if (col == colEmail) { stf.setEmail(e.getNewValue()); }
        	else if (col == colPhoneNum) { stf.setPhone(e.getNewValue()); }
        	else if (col == colAddress) { stf.setAddress(e.getNewValue()); }
        	else if (col == colGender) {
                stf.setGender(e.getNewValue().equals("Nữ") ? true : false);
        	}

        	try {
            	if (col == colPassword) {
            		stf.getAccount().setPassword(e.getNewValue());
        			UserDAO userDAO = new UserDAO();
    				userDAO.updatePassword(stf.getAccount());
    				Toaster.showSuccess("Chỉnh sửa mật khẩu thành công", "Dữ liệu đã được cập nhật vào CSDL.");

            		return;
            	}

				staffDAO.updateStaff(stf);
				Toaster.showSuccess("Chỉnh sửa nhân viên thành công", "Dữ liệu đã được cập nhật vào CSDL.");
				refresh();
			} catch (Exception e1) {
				Toaster.showError("Lỗi CSDL", e1.getMessage());
				e1.printStackTrace();
			}
        };

        colName.setOnEditCommit(commonHandler);
        colEmail.setOnEditCommit(commonHandler);
        colPhoneNum.setOnEditCommit(commonHandler);
        colAddress.setOnEditCommit(commonHandler);
        colPassword.setOnEditCommit(commonHandler);

        colGender.setCellFactory(ComboBoxTableCell.forTableColumn("Nam", "Nữ"));
        colGender.setOnEditCommit(commonHandler);
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Staff controller initialized");

		try {
			refresh();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		editCell();

        // Bind the ObservableList to the TableView
        staffTableView.setItems(data);

        fieldSearch.setPromptText("Thông tin tìm kiếm");

        comboBox.setPromptText("Thuộc tính tìm kiếm");
        comboBox.setItems(items);
        comboBox.setValue("Tên nhân viên");

        // Bind the columns to the corresponding properties in MyDataModel
        colStaffID.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genderText"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));

        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        staffTableView.setPlaceholder(new Label("Không có thông tin"));
	}

    @FXML
    void inputSearch(KeyEvent event) {
    	String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();
        SearchData(searchText, searchOption);
    }

    private void SearchData(String searchText, String searchOption) {
        if (searchText.isEmpty()) {
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

                    case "Chức vụ":
                        originalText = staff.getPosition();
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

    @FXML
    void btnActionAddStaff(ActionEvent event) {
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
    void btnActionDeleteStaff(ActionEvent event) throws SQLException {
    	Integer selectedIndex = staffTableView.getSelectionModel().getSelectedIndex();
    	Staff selectedRow = staffTableView.getSelectionModel().getSelectedItem();

    	if (selectedRow != null) {
    		try {
    			staffDAO.deleteStaff(selectedRow.getStaffID());
    	    	Toaster.showSuccess("Xoá nhân viên thành công", "Đã xoá nhân viên khỏi CSDL.");
    		} catch (SQLException e) {
				Toaster.showError("Lỗi CSDL", e.getMessage());
				e.printStackTrace();
			}

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
    		Toaster.showEditStatus("Bắt đầu chỉnh sửa", "Đã bắt đầu chỉnh sửa bảng");
    	} else {
    		staffTableView.edit(-1, null);
    		staffTableView.setEditable(false);
    		Toaster.showEditStatus("Kết thúc chỉnh sửa", "Đã kết thúc chỉnh sửa bảng");
    	}
    }

    @FXML
    private ToggleButton btnEdit;

}
