package library.application.staff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.mysql.DatabaseLayer;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			DatabaseLayer.getConnection();
			
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
	        primaryStage.setTitle("---");
	        
	        Scene scene = new Scene(root, 990, 660);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
			scene.getStylesheets().add(getClass().getResource("cupertino-light.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
    public void stop() {
		try {
			DatabaseLayer.closeConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

	public static void main(String[] args) {
		launch(args);
	}
}
