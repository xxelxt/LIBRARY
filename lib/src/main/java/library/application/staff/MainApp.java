package library.application.staff;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import library.application.util.Toaster;
import library.application.util.VariableManager;
import library.mysql.DatabaseLayer;

public class MainApp extends Application {

	private Stage primaryStage;

	public void maximize() {
		primaryStage.setMaximized(true);
	}

	public void setWindowSize() {
		primaryStage.setMaximized(false);
		primaryStage.setWidth(1275);
		primaryStage.setHeight(850);
		primaryStage.centerOnScreen();
	}

	public void restart() {
		try {
			// Font customFont = Font.loadFont(getClass().getResourceAsStream("SVN-Poppins-Regular.ttf"), 18);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
			Parent root = loader.load();
			// root.setStyle("-fx-font-family: '" + customFont.getName() + "'; -fx-font-size: 16px;");

			MainSceneController controller = loader.getController();
			controller.setMain(this, primaryStage);

	        primaryStage.setTitle("THE FINAL MOVEMENT BETA");

	        Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("primer-light.css").toExternalForm());
			primaryStage.setScene(scene);

			primaryStage.setMaximized(false);
			primaryStage.setWidth(335);
			primaryStage.setHeight(600);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		VariableManager.loadDate();

		try {
			DatabaseLayer.getConnection();
			this.primaryStage = primaryStage;
			this.restart();

	        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
	        primaryStage.getIcons().add(icon);

	        Toaster.setOwner(primaryStage);

		} catch (Exception e) {
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
