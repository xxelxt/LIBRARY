package library.application.util;

import org.controlsfx.control.Notifications;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Toaster {
	private static final Image ErrorIcon = new Image(Toaster.class.getResourceAsStream("erroricon.png"));
	private static Object owner;
	
	public static void setOwner(Object owner) {
		Toaster.owner = owner;
	}

	public static void showError(String title, String text) {
		ImageView iconView = new ImageView(ErrorIcon);
		iconView.setFitHeight(20);
        iconView.setFitWidth(20);
		
		Notifications.create()
			.owner(owner)
			.title(title)
			.text(text)
			.graphic(iconView)
			.show();
	}

}
