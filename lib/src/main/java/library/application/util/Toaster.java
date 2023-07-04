package library.application.util;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Toaster {
	private static final Image ErrorIcon = new Image(Toaster.class.getResourceAsStream("erroricon.png"));
	private static final Image SuccessIcon = new Image(Toaster.class.getResourceAsStream("successicon.png"));
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
			.position(Pos.BOTTOM_RIGHT)
			.hideAfter(Duration.seconds(1.5))
			.show();
	}

	public static void showSuccess(String title, String text) {
		ImageView iconView = new ImageView(SuccessIcon);
		iconView.setFitHeight(20);
        iconView.setFitWidth(20);

		Notifications.create()
			.owner(owner)
			.title(title)
			.text(text)
			.graphic(iconView)
			.position(Pos.BOTTOM_RIGHT)
			.hideAfter(Duration.seconds(1.5))
			.show();
	}
}
