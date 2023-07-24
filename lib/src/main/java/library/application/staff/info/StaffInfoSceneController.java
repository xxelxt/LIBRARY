package library.application.staff.info;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import library.application.util.CloudinaryUtil;
import library.application.util.Toaster;
import library.mysql.dao.StaffDAO;
import library.mysql.dao.UserDAO;
import library.user.Staff;

public class StaffInfoSceneController {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

	private Staff currentStaff;

	public void setCurrentStaff(Staff currentUser) {
		this.currentStaff = currentUser;
        fieldStaffID.setText(Integer.toString(currentStaff.getStaffID()));
        fieldName.setText(currentStaff.getName());
        fieldGender.setText(currentStaff.getGenderText());
        fieldEmail.setText(currentStaff.getEmail());
        fieldPhone.setText(currentStaff.getPhone());
        fieldAddress.setText(currentStaff.getAddress());
        fieldPosition.setText(currentStaff.getPosition());
        fieldUsername.setText(currentStaff.getUsername());
        fieldPassword.setText(currentStaff.getPassword());

        try {
        	UserDAO userDAO = new UserDAO();
        	String imageURL = userDAO.getImageURL(currentStaff.getAccount());

            if (imageURL != null && !imageURL.equals("")) {
                Image image = new Image(imageURL);
                imageView.setImage(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
//        clip.setArcWidth(30);
//        clip.setArcHeight(30);
//        imageView.setClip(clip);
	}

    @FXML
    private TextField fieldAddress;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldGender;

    @FXML
    private TextField fieldName;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextField fieldPasswordAlt;

    @FXML
    private TextField fieldPhone;

    @FXML
    private TextField fieldPosition;

    @FXML
    private TextField fieldStaffID;

    @FXML
    private TextField fieldUsername;

    @FXML
    private ToggleButton btnChangePassword;

    @FXML
    private ToggleButton btnEditInfo;

    @FXML
    private Button btnUploadImage;

    @FXML
    private ImageView imageView;

    @FXML
    void btnActionChangePassword(ActionEvent event) {
    	if (!btnChangePassword.isSelected()) {
    		currentStaff.getAccount().setPassword(fieldPassword.getText());

    		try {
    			UserDAO userDAO = new UserDAO();
				userDAO.updatePassword(currentStaff.getAccount());
				Toaster.showSuccess("Chỉnh sửa mật khẩu thành công", "Dữ liệu đã được cập nhật vào CSDL.");
			} catch (Exception e) {
				Toaster.showError("Lỗi CSDL", e.getMessage());
				e.printStackTrace();
			}
    	}
    }

    @FXML
    void btnActionEditInfo(ActionEvent event) {
    	if (!btnEditInfo.isSelected()) {
    		currentStaff.setName(fieldName.getText());
    		currentStaff.setEmail(fieldEmail.getText());
    		currentStaff.setPhone(fieldPhone.getText());
    		currentStaff.setAddress(fieldAddress.getText());

    		try {
    			StaffDAO staffDAO = new StaffDAO();
				staffDAO.updateStaff(currentStaff);
				Toaster.showSuccess("Chỉnh sửa thành công", "Dữ liệu đã được cập nhật vào CSDL.");
			} catch (Exception e) {
				Toaster.showError("Lỗi CSDL", e.getMessage());
				e.printStackTrace();
			}
    	}
    }

    @FXML
    void btnActionUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn hình ảnh");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Cloudinary cloudinary = CloudinaryUtil.getCloudinaryInstance();

                Map<String, String> uploadResult = cloudinary.uploader().upload(selectedFile, ObjectUtils.emptyMap());
                String imageURL = uploadResult.get("secure_url");

                // Update the image URL in the database using the UserDAO
                try {
                    UserDAO userDAO = new UserDAO();
                    userDAO.updateImageURL(currentStaff.getAccount(), imageURL);
                    Toaster.showSuccess("Đã cập nhật ảnh đại diện", "Tải ảnh thành công lên Cloudinary & CSDL");
                } catch (SQLException e) {
                    Toaster.showError("Lỗi", e.getMessage());
                    e.printStackTrace();
                }

                // Display the uploaded image in the ImageView
                Image image = new Image(imageURL);
                imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void initialize() {
        assert fieldAddress != null : "fx:id=\"fieldAddress\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldEmail != null : "fx:id=\"fieldEmail\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldGender != null : "fx:id=\"fieldGender\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldName != null : "fx:id=\"fieldName\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldPassword != null : "fx:id=\"fieldPassword\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldPhone != null : "fx:id=\"fieldPhone\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldPosition != null : "fx:id=\"fieldPosition\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldStaffID != null : "fx:id=\"fieldStaffID\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";
        assert fieldUsername != null : "fx:id=\"fieldUsername\" was not injected: check your FXML file 'StaffInfoScene.fxml'.";

        BooleanProperty editable = btnEditInfo.selectedProperty();

        fieldName.editableProperty().bind(editable);
        fieldEmail.editableProperty().bind(editable);
        fieldPhone.editableProperty().bind(editable);
        fieldAddress.editableProperty().bind(editable);

        fieldName.disableProperty().bind(editable.not());
        fieldEmail.disableProperty().bind(editable.not());
        fieldPhone.disableProperty().bind(editable.not());
        fieldAddress.disableProperty().bind(editable.not());

        fieldPasswordAlt.visibleProperty().bind(btnChangePassword.selectedProperty());
        fieldPassword.visibleProperty().bind(btnChangePassword.selectedProperty().not());

        // Bind textField to passwordField
        Bindings.bindBidirectional(fieldPassword.textProperty(), fieldPasswordAlt.textProperty());
        fieldPassword.setDisable(true);
    }
}
