package library.application.util;

import org.controlsfx.control.Notifications;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Toaster {
	private static final ImageView iconView = new ImageView();
	private static final Image ErrorIcon = new Image(Toaster.class.getResourceAsStream("erroricon.png"));
	// private static Image ErrorIcon;
	
	static {
		iconView.setFitHeight(20);
        iconView.setFitWidth(20);
	}
	
	public static void showError(String title, String text) {
		iconView.setImage(ErrorIcon);
		
		Notifications.create()
			.title(title)
			.text(text)
			.graphic(iconView)
			.show();
	}

}
