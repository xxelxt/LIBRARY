package library.application.staff.publication;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import library.application.staff.interfac.SceneFeatureGate;
import library.mysql.dao.PrintMediaDAO;
import library.publication.Books;
import library.publication.PrintMedia;
import library.publication.Publication;

public class PrintMediaSceneController implements Initializable, SceneFeatureGate {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Books> booksTableView;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TableView<PrintMedia> pmTableView;

    @FXML
    private TableColumn<PrintMedia, String> colCountry;

    @FXML
    private TableColumn<PrintMedia, Integer> colID;

    @FXML
    private TableColumn<PrintMedia, String> colPrintType;

    @FXML
    private TableColumn<PrintMedia, Date> colPublicationDate;

    @FXML
    private TableColumn<PrintMedia, Integer> colQuantity;

    @FXML
    private TableColumn<PrintMedia, Integer> colReleaseNumber;

    @FXML
    private TableColumn<PrintMedia, String> colTitle;

    @FXML
    private TextField fieldSearch;

    @FXML
    private AnchorPane paneAdd;

    @FXML
    private VBox paneMain;

    private ObservableList<PrintMedia> data;

    private ObservableList<String> items = FXCollections.observableArrayList("ID", "Tên ẩn phẩm", "Loại ấn phẩm", "Quốc gia");

    private PrintMediaDAO pmDAO = new PrintMediaDAO();

    public void refresh() {
        data = FXCollections.observableArrayList();

        List<PrintMedia> allPM = pmDAO.loadAllPrintMedias();
		System.out.println(allPM);

	    for (PrintMedia pm : allPM){
	    	data.add(pm);
	    }

	    pmTableView.setItems(data);
    }

    public void scrollToLast() {
    	int lastIndex = pmTableView.getItems().size() - 1;
    	pmTableView.scrollTo(lastIndex);
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Print media controller initialized");
        // Add a default row
		refresh();

        // Bind the ObservableList to the TableView
        pmTableView.setItems(data);

        fieldSearch.setPromptText("Thông tin tìm kiếm");

        comboBox.setPromptText("Thuộc tính tìm kiếm");
        comboBox.setItems(items);
        comboBox.setValue("Tên ấn phẩm");

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<>("publicationID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        colReleaseNumber.setCellValueFactory(new PropertyValueFactory<>("releaseNumber"));
        colPrintType.setCellValueFactory(new PropertyValueFactory<>("printType"));
	}

	Date now = new Date(new java.util.Date().getTime());

	@FXML
    void inputSearch(KeyEvent event) {
        String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();
        SearchData(searchText, searchOption);
    }
	
    @FXML
    void btnActionAddPrintMedia(ActionEvent event) {
    	paneMain.setVisible(false);
    	paneAdd.setVisible(true);
    }

    @FXML
    void btnActionDeletePrintMedia(ActionEvent event) {
    	Integer selectedIndex = pmTableView.getSelectionModel().getSelectedIndex();
    	Publication selectedRow = pmTableView.getSelectionModel().getSelectedItem();

    	if (selectedRow != null) {
	    	pmDAO.deletePrintMedia(selectedRow.getPublicationID());
	    	this.refresh();

	        if (selectedIndex >= 0 && selectedIndex < data.size()) {
	            pmTableView.getSelectionModel().select(selectedIndex);
	        }
    	} else {
        	pmTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void btnActionReturn(ActionEvent event) {
    	paneMain.setVisible(true);
    	paneAdd.setVisible(false);
    	this.refresh();
    }

    @FXML
    void btnActionEditPrintMedia(ActionEvent event) {

    }
    
    private void SearchData(String searchText, String searchOption) {
        FilteredList<PrintMedia> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(pm -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            
        	String originalText = "";
        	switch (searchOption) {
        		case "ID":			originalText = Integer.toString(pm.getPublicationID()); break;
    	        case "Tên ấn phẩm":	originalText = pm.getTitle(); break;
    	        case "Loại ấn phẩm":		originalText = pm.getPrintType(); break;	
    	        case "Quốc gia":	originalText = pm.getCountry(); break;
        	}
        	
        	if (originalText != "") {
                if (searchOption.equals("ID")) {
    	            return originalText.startsWith(searchText);
                } else {
                	return originalText.toLowerCase().contains(searchText.toLowerCase());
                }
        	}
        	return false;
        });

        pmTableView.setItems(filteredList);
    }


    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private HBox hboxFeature;

	@Override
	public void setFeatureFor(Integer user) {
		// TODO Auto-generated method stub
		if (user == CLERK) {
			hboxFeature.getChildren().remove(btnAdd);
			hboxFeature.getChildren().remove(btnEdit);
			hboxFeature.getChildren().remove(btnDelete);
		} else if (user == STUDENT) {
			hboxFeature.getChildren().remove(btnAdd);
			hboxFeature.getChildren().remove(btnEdit);
			hboxFeature.getChildren().remove(btnDelete);
		}
	}
}
