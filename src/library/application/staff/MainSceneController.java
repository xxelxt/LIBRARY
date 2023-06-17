package library.application.staff;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import library.application.staff.add.AddBookController;
import library.mysql.Database;
import library.publication.Books;
import library.publication.Publication;

public class MainSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane mainPane;
    
    @FXML
    private Tab tab1;

    @FXML
    private Tab tab2;
    
    @FXML
    private Tab tab3;

    @FXML
    private Tab tab4;

    @FXML
    private Tab tab5;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("MainScene initialized");
        // Add a default row
	}

}
