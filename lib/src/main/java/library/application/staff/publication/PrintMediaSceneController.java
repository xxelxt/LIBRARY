package library.application.staff.publication;

import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import library.application.staff.interfac.SceneFeatureGate;
import library.application.util.DatePickerTableCell;
import library.application.util.IntegerFieldTableCell;
import library.application.util.Toaster;
import library.mysql.dao.PrintMediaDAO;
import library.publication.PrintMedia;
import library.publication.Publication;

public class PrintMediaSceneController implements Initializable, SceneFeatureGate {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /**
     * TABLE
     */

    @FXML
    private TableView<PrintMedia> printMediaTableView;

    @FXML
    private TableColumn<PrintMedia, String> colCountry;

    @FXML
    private TableColumn<PrintMedia, Integer> colID;

    @FXML
    private TableColumn<PrintMedia, Date> colPublicationDate;

    @FXML
    private TableColumn<PrintMedia, String> colPrintType;

    @FXML
    private TableColumn<PrintMedia, Integer> colQuantity;

    @FXML
    private TableColumn<PrintMedia, Integer> colReleaseNumber;

    @FXML
    private TableColumn<PrintMedia, String> colTitle;

    /**
     * SEARCH FUNCTION
     */

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    private ObservableList<String> comboBoxItems = FXCollections.observableArrayList("ID", "Tên ẩn phẩm", "Loại ấn phẩm", "Quốc gia");

    /**
     * DATA
     */

    @FXML
    private VBox paneMain;

    @FXML
    private AnchorPane paneAdd;

    private ObservableList<PrintMedia> data;

    private PrintMediaDAO pmDAO = new PrintMediaDAO();

    public void refresh() throws SQLException {
        data = FXCollections.observableArrayList();

        List<PrintMedia> allPM;

        try {
			allPM = pmDAO.loadAllPrintMedias();
		} catch (SQLException e) {
			allPM = new ArrayList<>();

			Toaster.showError("Không thể tải danh sách ấn phẩm", e.getMessage());
			e.printStackTrace();
		}

	    for (PrintMedia pm : allPM){
	    	data.add(pm);
	    }

	    printMediaTableView.setItems(data);
    }

    public void scrollToLast() {
    	int lastIndex = printMediaTableView.getItems().size() - 1;
    	printMediaTableView.scrollTo(lastIndex);
    }

    private void editCell(){
    	/**
    	 * String cell
    	 */

        colTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        colCountry.setCellFactory(TextFieldTableCell.forTableColumn());
        colPrintType.setCellFactory(TextFieldTableCell.forTableColumn());

        EventHandler<CellEditEvent<PrintMedia, String>> commonHandler = e -> {
        	PrintMedia pm = e.getTableView().getItems().get(e.getTablePosition().getRow());
        	TableColumn<PrintMedia, String> col = e.getTableColumn();
        	if (col == colTitle) { pm.setTitle(e.getNewValue()); }
        	else if (col == colCountry) { pm.setCountry(e.getNewValue()); }
        	else if (col == colPrintType) { pm.setPrintType(e.getNewValue()); }

        	try {
        		pmDAO.updatePrintMedia(pm);
            	Toaster.showSuccess("Chỉnh sửa ấn phẩm thành công", "Dữ liệu đã được cập nhật vào CSDL.");
            	refresh();
			} catch (SQLException e1) {
				Toaster.showError("Lỗi CSDL", e1.getMessage());
				e1.printStackTrace();
			}
        };

        colTitle.setOnEditCommit(commonHandler);
        colCountry.setOnEditCommit(commonHandler);
        colPrintType.setOnEditCommit(commonHandler);

        /**
         * Date cell
         */

        colPublicationDate.setCellFactory(col -> new DatePickerTableCell<>(col));

        colPublicationDate.setOnEditCommit(e -> {
            if (btnEdit.isSelected()) {
                PrintMedia pm = e.getRowValue();
                pm.setReleaseDate(e.getNewValue());

                try {
            		pmDAO.updatePrintMedia(pm);
                	Toaster.showSuccess("Chỉnh sửa ấn phẩm thành công", "Dữ liệu đã được cập nhật vào CSDL.");
                	refresh();
    			} catch (SQLException e1) {
    				Toaster.showError("Lỗi CSDL", e1.getMessage());
    				e1.printStackTrace();
    			}
            }
        });

        /**
         * Integer cell
         */
        
        colReleaseNumber.setCellFactory(col -> new IntegerFieldTableCell<>());
        colQuantity.setCellFactory(col -> new IntegerFieldTableCell<>());

        EventHandler<CellEditEvent<PrintMedia, Integer>> commonIntegerHandler = e -> {
        	PrintMedia pm = e.getRowValue();
        	TableColumn<PrintMedia, Integer> col = e.getTableColumn();
        	if (col == colReleaseNumber) { pm.setReleaseNumber(e.getNewValue()); }
        	else if (col == colQuantity) { pm.setQuantity(e.getNewValue()); }

        	try {
        		pmDAO.updatePrintMedia(pm);
            	Toaster.showSuccess("Chỉnh sửa ấn phẩm thành công", "Dữ liệu đã được cập nhật vào CSDL.");
        		refresh();
			} catch (SQLException e1) {
				Toaster.showError("Lỗi CSDL", e1.getMessage());
				e1.printStackTrace();
			}
        };

        colReleaseNumber.setOnEditCommit(commonIntegerHandler);
        colQuantity.setOnEditCommit(commonIntegerHandler);
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Print media controller initialized");

        try {
        	refresh();
    		editCell();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

        // Bind the ObservableList to the TableView
		printMediaTableView.setItems(data);

        comboBox.setItems(comboBoxItems);
        comboBox.setValue("Tên ấn phẩm");

        // Bind the columns to the corresponding properties in MyDataModel
        colID.setCellValueFactory(new PropertyValueFactory<>("publicationID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        colReleaseNumber.setCellValueFactory(new PropertyValueFactory<>("releaseNumber"));
        colPrintType.setCellValueFactory(new PropertyValueFactory<>("printType"));

        printMediaTableView.setPlaceholder(new Label("Không có thông tin"));
	}

    @FXML
    void inputSearch(KeyEvent event) {
        String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();
        SearchData(searchText, searchOption);
    }

    private void SearchData(String searchText, String searchOption) {
        if (searchText.isEmpty()) {
            printMediaTableView.setItems(data);
        } else {
            // Apply filtering based on the search text and option
            FilteredList<PrintMedia> filteredList = new FilteredList<>(data);

            filteredList.setPredicate(printMedia -> {
                String originalText = "";
                switch (searchOption) {
                    case "ID":
                        originalText = Integer.toString(printMedia.getPublicationID());
                        break;

                    case "Tên ấn phẩm":
                        originalText = printMedia.getTitle();
                        break;

                    case "Loại ấn phẩm":
                        originalText = printMedia.getPrintType();
                        break;

                    case "Quốc gia":
                        originalText = printMedia.getCountry();
                        break;
                }

                if (!originalText.isEmpty()) {
                    if (searchOption.equals("ID")) {
                        return originalText.startsWith(searchText);
                    } else {
                        return originalText.toLowerCase().contains(searchText.toLowerCase());
                    }
                }

                return false;
            });

            printMediaTableView.setItems(filteredList);
        }
    }

    @FXML
    void btnActionAddPrintMedia(ActionEvent event) {
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
    void btnActionDeletePrintMedia(ActionEvent event) throws SQLException {
    	Integer selectedIndex = printMediaTableView.getSelectionModel().getSelectedIndex();
    	Publication selectedRow = printMediaTableView.getSelectionModel().getSelectedItem();

    	if (selectedRow != null) {
    		try {
    			pmDAO.deletePrintMedia(selectedRow.getPublicationID());
    	    	Toaster.showSuccess("Xoá ấn phẩm thành công", "Đã xoá ấn phẩm khỏi CSDL.");
			} catch (SQLException e) {
				Toaster.showError("Lỗi CSDL", e.getMessage());
				e.printStackTrace();
			}

	    	this.refresh();

	        if (selectedIndex >= 0 && selectedIndex < data.size()) {
	        	printMediaTableView.getSelectionModel().select(selectedIndex);
	        }
    	} else {
    		printMediaTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void btnActionEditPrintMedia(ActionEvent event) {
    	if (btnEdit.isSelected()) {
    		printMediaTableView.setEditable(true);
    	} else {
    		printMediaTableView.edit(-1, null);
    		printMediaTableView.setEditable(false);
    	}
    }

    @FXML
    void btnActionEditPrintMediaClerk(ActionEvent event) {
    	if (btnEditClerk.isSelected()) {
    		printMediaTableView.setEditable(true);
    	} else {
    		printMediaTableView.edit(-1, null);
    		printMediaTableView.setEditable(false);
    	}
    }

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private ToggleButton btnEdit;

    @FXML
    private ToggleButton btnEditClerk;

    @FXML
    private HBox hboxFeature;

	@Override
	public void setFeatureFor(Integer user) {
		if (user == LIBRARIAN) {
			hboxFeature.getChildren().remove(btnEditClerk);
		} else {
			hboxFeature.getChildren().remove(btnAdd);
			hboxFeature.getChildren().remove(btnEdit);
			hboxFeature.getChildren().remove(btnDelete);

			if (user == CLERK) {

			    colQuantity.setEditable(true);

			    colID.setEditable(false);
			    colTitle.setEditable(false);
			    colCountry.setEditable(false);
			    colPublicationDate.setEditable(false);
			    colReleaseNumber.setEditable(false);

			} else if (user == STUDENT) {

			}
		}
	}
}
