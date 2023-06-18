package library.application.staff.student;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import library.mysql.dao.StudentDAO;
import library.publication.Publication;
import library.user.Student;

public class StudentSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, String> colAddress;

    @FXML
    private TableColumn<Student, String> colClass;

    @FXML
    private TableColumn<Student, String> colEmail;

    @FXML
    private TableColumn<Student, Double> colFine;

    @FXML
    private TableColumn<Student, Boolean> colFineStatus;

    @FXML
    private TableColumn<Student, Boolean> colGender;

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

    private ObservableList<Student> data;

    private ObservableList<String> items = FXCollections.observableArrayList("Mã sinh viên", "Tên sinh viên", "Lớp", "Số điện thoại");

    private StudentDAO studentDAO = new StudentDAO();

    public void refresh() {
        data = FXCollections.observableArrayList();

        List<Student> allStudents = studentDAO.loadAllStudents();
		System.out.println(allStudents);

	    for (Student student : allStudents){
	    	data.add(student);
	    }

	    studentTableView.setItems(data);
    }

    public void scrollToLast() {
    	int lastIndex = studentTableView.getItems().size() - 1;
    	studentTableView.scrollTo(lastIndex);
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Student controller initialized");
        // Add a default row
		refresh();

        // Bind the ObservableList to the TableView
        studentTableView.setItems(data);

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
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("className"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colFineStatus.setCellValueFactory(new PropertyValueFactory<>("fineStatus"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));
        
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        colGender.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "Nữ" : "Nam" );
            }
        });
        
        colFineStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "Bị phạt" : "Không bị phạt" );
            }
        });
	}

	Date now = new Date(new java.util.Date().getTime());

	@FXML
    void btnActionAddStudent(ActionEvent event) {
		paneMain.setVisible(false);
    	paneAdd.setVisible(true);
    }

    @FXML
    void btnActionDeleteStudent(ActionEvent event) {
    	Integer selectedIndex = studentTableView.getSelectionModel().getSelectedIndex();
    	Student selectedRow = studentTableView.getSelectionModel().getSelectedItem();
    	
    	if (selectedRow != null) {
	    	studentDAO.deleteStudent(selectedRow.getStudentID());
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
            studentTableView.setItems(data);
        } else {
            // Apply filtering based on the search text and option
            FilteredList<Student> filteredList = new FilteredList<>(data);

            filteredList.setPredicate(student -> {
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
    }


    @FXML
    void btnActionReturn(ActionEvent event) {
    	paneMain.setVisible(true);
    	paneAdd.setVisible(false);
    	this.refresh();
    }

}
