package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
	        primaryStage.setTitle("Registration Form FXML Application");
	        
	        Scene scene = new Scene(root, 800, 500);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
