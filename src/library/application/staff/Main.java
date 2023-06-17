package library.application.staff;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import library.mysql.DatabaseLayer;

public class Main extends Application {

	private Stage primaryStage;

	public void maximize() {
		primaryStage.setMaximized(true);
	}

	public void setWindowSize() {
		primaryStage.setWidth(1200);
		primaryStage.setHeight(800);
		primaryStage.centerOnScreen();
	}

	public void restart() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
			Parent root = loader.load();
			MainSceneController controller = loader.getController();
			controller.setMain(this);

	        primaryStage.setTitle("---");

	        Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("primer-light.css").toExternalForm());
			primaryStage.setScene(scene);

			primaryStage.setMaximized(false);
			primaryStage.setWidth(335);
			primaryStage.setHeight(500);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			DatabaseLayer.getConnection();
			this.primaryStage = primaryStage;
			this.restart();
			
	        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
	        primaryStage.getIcons().add(icon);

		} catch (Exception e) {
			// TODO Auto-generated catch block
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
