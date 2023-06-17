package library.application.staff.student;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import library.mysql.dao.BookDAO;
import library.mysql.dao.StudentDAO;
import library.publication.Books;
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
    private TableColumn<Student, String> colPhoneNum;

    @FXML
    private TableColumn<Student, String> colStudentID;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    @FXML
    private AnchorPane paneAdd;

    @FXML
    private VBox paneMain;
    
    private ObservableList<Student> data;
    
    private ObservableList<String> items = FXCollections.observableArrayList("Mã sinh viên", "Tên sinh viên", "Lớp");
    
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
        
        fieldSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String searchText = newValue;
                String searchOption = comboBox.getValue();
                // SearchData(searchText, searchOption);
            }
        });

        // Bind the columns to the corresponding properties in MyDataModel
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("className"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        colPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colFineStatus.setCellValueFactory(new PropertyValueFactory<>("fineStatus"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));
	}
	
	Date now = new Date(new java.util.Date().getTime());

    @FXML
    void btnAddStudent(ActionEvent event) {

    }

    @FXML
    void btnBorrowHistory(ActionEvent event) {

    }

    @FXML
    void btnDeleteStudent(ActionEvent event) {

    }

    @FXML
    void btnEditStudent(ActionEvent event) {

    }

    @FXML
    void btnReturn(ActionEvent event) {

    }

}
