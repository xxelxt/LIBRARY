package library.application.staff.student;

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
import library.application.util.IntegerFieldTableCell;
import library.application.util.Toaster;
import library.mysql.dao.StudentDAO;
import library.mysql.dao.UserDAO;
import library.user.Student;

public class StudentSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /**
     * TABLE
     */

    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, String> colAddress;

    @FXML
    private TableColumn<Student, String> colClass;

    @FXML
    private TableColumn<Student, String> colEmail;

    @FXML
    private TableColumn<Student, Integer> colFine;

    @FXML
    private TableColumn<Student, String> colFineStatus;

    @FXML
    private TableColumn<Student, String> colGender;

    @FXML
    private TableColumn<Student, String> colName;

    @FXML
    private TableColumn<Student, String> colPassword;

    @FXML
    private TableColumn<Student, String> colPhoneNum;

    @FXML
    private TableColumn<Student, String> colStudentID;

    @FXML
    private TableColumn<Student, String> colUsername;

    /**
     * SEARCH FUNCTION
     */

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ComboBox<String> comboBoxFineStatus;

    @FXML
    private TextField fieldSearch;

    private ObservableList<String> items = FXCollections.observableArrayList("Mã sinh viên", "Tên sinh viên", "Lớp", "Số điện thoại");
    private ObservableList<String> fineItems = FXCollections.observableArrayList("Tất cả", "Không bị phạt", "Bị phạt");

    /**
     * DATA
     */

    @FXML
    private VBox paneMain;

    @FXML
    private AnchorPane paneAdd;

    private ObservableList<Student> data;

    private StudentDAO studentDAO = new StudentDAO();

    public void refresh() {
        data = FXCollections.observableArrayList();

        List<Student> allStudents;

		try {
			allStudents = studentDAO.loadAllStudents();
		} catch (SQLException e) {
			allStudents = new ArrayList<>();

			Toaster.showError("Không thể tải danh sách sinh viên", e.getMessage());
			e.printStackTrace();
		}

	    for (Student student : allStudents){
	    	data.add(student);
	    }

	    studentTableView.setItems(data);
    }

    public void scrollToLast() {
    	int lastIndex = studentTableView.getItems().size() - 1;
    	studentTableView.scrollTo(lastIndex);
    }

    private void editCell() {
    	/**
    	 * String & combo box cell
    	 */

        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colPhoneNum.setCellFactory(TextFieldTableCell.forTableColumn());
        colAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        colClass.setCellFactory(TextFieldTableCell.forTableColumn());

        colPassword.setCellFactory(TextFieldTableCell.forTableColumn());

        EventHandler<CellEditEvent<Student, String>> commonHandler = e -> {
        	Student std = e.getRowValue();
        	TableColumn<Student, String> col = e.getTableColumn();
        	if (col == colName) { std.setName(e.getNewValue()); }
        	else if (col == colEmail) { std.setEmail(e.getNewValue()); }
        	else if (col == colPhoneNum) { std.setPhone(e.getNewValue()); }
        	else if (col == colAddress) { std.setAddress(e.getNewValue()); }
        	else if (col == colClass) { std.setClassName(e.getNewValue()); }
        	else if (col == colGender) {
                std.setGender(e.getNewValue().equals("Nữ") ? true : false);
        	}

        	try {
            	if (col == colPassword) {
            		std.getAccount().setPassword(e.getNewValue());
        			UserDAO userDAO = new UserDAO();
    				userDAO.updatePassword(std.getAccount());
    				Toaster.showSuccess("Chỉnh sửa mật khẩu thành công", "Dữ liệu đã được cập nhật vào CSDL.");

            		return;
            	}

				studentDAO.updateStudent(std);
				Toaster.showSuccess("Chỉnh sửa sinh viên thành công", "Dữ liệu đã được cập nhật vào CSDL.");
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
        colClass.setOnEditCommit(commonHandler);
        colPassword.setOnEditCommit(commonHandler);

        colGender.setCellFactory(ComboBoxTableCell.forTableColumn("Nam", "Nữ"));
        colGender.setOnEditCommit(commonHandler);

        /**
         * Fine & Fine status
         */

        colFine.setCellFactory(col -> new IntegerFieldTableCell<>());
        colFine.setOnEditCommit(e -> {
        	Student std = e.getRowValue();
        	Integer fine = e.getNewValue();
        	std.setFine(fine);
        	if (fine == 0) {
        		std.setFineStatus(false);
        	}

        	try {
				studentDAO.updateStudent(std);
				refresh();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        });
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Student controller initialized");

		refresh();
		editCell();

        // Bind the ObservableList to the TableView
        studentTableView.setItems(data);

        fieldSearch.setPromptText("Thông tin tìm kiếm");

        comboBox.setPromptText("Thuộc tính tìm kiếm");
        comboBox.setItems(items);
        comboBox.setValue("Mã sinh viên");

        comboBoxFineStatus.setPromptText("Tình trạng phạt");
        comboBoxFineStatus.setItems(fineItems);
        comboBoxFineStatus.setValue("Tất cả");

        // Bind the columns to the corresponding properties in MyDataModel
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("className"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genderText"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colFineStatus.setCellValueFactory(new PropertyValueFactory<>("fineStatusText"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));

        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        studentTableView.setPlaceholder(new Label("Không có thông tin"));
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
        FilteredList<Student> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(student -> {
        	if (!searchFineStatus.equals("Tất cả") && !student.getFineStatusText().equals(searchFineStatus)) {
        		return false;
        	}

            String originalText = "";
            switch (searchOption) {
                case "Mã sinh viên":
                    originalText = student.getStudentID();
                    break;

                case "Tên sinh viên":
                    originalText = student.getName();
                    break;

                case "Lớp":
                    originalText = student.getClassName();
                    break;

                case "Số điện thoại":
                    originalText = student.getPhone();
                    break;
            }

            if (!originalText.isEmpty()) {
                return originalText.toLowerCase().contains(searchText.toLowerCase());
            }

            return false;
        });

        studentTableView.setItems(filteredList);
    }

	@FXML
    void btnActionAddStudent(ActionEvent event) {
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
    void btnActionDeleteStudent(ActionEvent event) {
    	Integer selectedIndex = studentTableView.getSelectionModel().getSelectedIndex();
    	Student selectedRow = studentTableView.getSelectionModel().getSelectedItem();

    	if (selectedRow != null) {
	    	try {
				studentDAO.deleteStudent(selectedRow.getStudentID());
				Toaster.showSuccess("Xoá sinh viên thành công", "Đã xoá sinh viên khỏi CSDL.");
			} catch (SQLException e) {
				Toaster.showError("SQL ERROR", e.getMessage());
				e.printStackTrace();
			}

	    	this.refresh();

	        if (selectedIndex >= 0 && selectedIndex < data.size()) {
	        	studentTableView.getSelectionModel().select(selectedIndex);
	        }
    	} else {
    		studentTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void btnActionEditStudent(ActionEvent event) {
    	if (btnEdit.isSelected()) {
    		studentTableView.setEditable(true);
    		Toaster.showEditStatus("Bắt đầu chỉnh sửa", "Đã bắt đầu chỉnh sửa bảng");
    	} else {
    		studentTableView.edit(-1, null);
    		studentTableView.setEditable(false);
    		Toaster.showEditStatus("Kết thúc chỉnh sửa", "Đã kết thúc chỉnh sửa bảng");
    	}
    }

    @FXML
    private ToggleButton btnEdit;

}
