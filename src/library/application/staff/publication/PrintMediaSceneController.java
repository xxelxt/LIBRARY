package library.application.staff.publication;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import library.mysql.dao.PrintMediaDAO;
import library.publication.Books;
import library.publication.PrintMedia;
import library.publication.Publication;

public class PrintMediaSceneController implements Initializable {

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
        
        fieldSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String searchText = newValue;
                String searchOption = comboBox.getValue();
                SearchData(searchText, searchOption);
            }
        });

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<PrintMedia, Integer>("publicationID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<PrintMedia, String>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<PrintMedia, Date>("releaseDate"));
        colCountry.setCellValueFactory(new PropertyValueFactory<PrintMedia, String>("country"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<PrintMedia, Integer>("quantity"));
        
        colReleaseNumber.setCellValueFactory(new PropertyValueFactory<PrintMedia, Integer>("releaseNumber"));
        colPrintType.setCellValueFactory(new PropertyValueFactory<PrintMedia, String>("printType"));
	}
	
	Date now = new Date(new java.util.Date().getTime());
    
    @FXML
    void btnAddPrintMedia(ActionEvent event) {
    	paneMain.setVisible(false);
    	paneAdd.setVisible(true);
    }

    @FXML
    void btnDeletePrintMedia(ActionEvent event) {
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
    void btnReturn(ActionEvent event) {
    	paneMain.setVisible(true);
    	paneAdd.setVisible(false);
    	this.refresh();
    }

    @FXML
    void btnEditPrintMedia(ActionEvent event) {
    	
    }

    private void filterPMbyID(String idText) {
        FilteredList<PrintMedia> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(pm -> {
            if (idText == null || idText.isEmpty()) {
                return true;
            }
            try {
                int id = Integer.parseInt(idText);
                return pm.getPublicationID() == id;
            } catch (NumberFormatException e) {
                return false;
            }
        });

        pmTableView.setItems(filteredList);
    }

    
    private void filterPMbyTitle(String title) {
        FilteredList<PrintMedia> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(pm -> {
            if (title == null || title.isEmpty()) {
                return true;
            }
            String lowerCaseTitle = title.toLowerCase();
            return pm.getTitle().toLowerCase().contains(lowerCaseTitle);
        });

        pmTableView.setItems(filteredList);
    }
    
    private void filterPMbyCountry(String country) {
        FilteredList<PrintMedia> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(pm -> {
            if (country == null || country.isEmpty()) {
                return true;
            }
            String lowerCaseTitle = country.toLowerCase();
            return pm.getCountry().toLowerCase().contains(lowerCaseTitle);
        });

        pmTableView.setItems(filteredList);
    }
    
    private void filterPMbyType(String category) {
        FilteredList<PrintMedia> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(pm -> {
            if (category == null || category.isEmpty()) {
                return true;
            }
            String lowerCaseTitle = category.toLowerCase();
            return pm.getPrintType().toLowerCase().contains(lowerCaseTitle);
        });

        pmTableView.setItems(filteredList);
    }
    
    private void SearchData(String searchText, String searchOption) {
    	switch (searchOption) {
        case "ID":
        	filterPMbyID(searchText);
            break;
        case "Tên ấn phẩm":
            filterPMbyTitle(searchText);
            break;
        case "Loại ấn phẩm":
        	filterPMbyType(searchText);
            break;
        case "Quốc gia":
        	filterPMbyCountry(searchText);
            break;
    	}
    }
}
