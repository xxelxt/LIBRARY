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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import library.application.staff.borrow.BorrowHistorySceneController;
import library.application.util.CloudinaryUtil;
import library.application.util.Toaster;
import library.mysql.dao.StudentDAO;
import library.mysql.dao.UserDAO;
import library.user.Student;

public class StudentInfoSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

	private Student currentStudent;

	public void setCurrentStudent(Student currentUser) {
		this.currentStudent = currentUser;
        fieldStudentID.setText(currentStudent.getStudentID());
        fieldName.setText(currentStudent.getName());
        fieldClass.setText(currentStudent.getClassName());
        fieldGender.setText(currentStudent.getGenderText());
        fieldEmail.setText(currentStudent.getEmail());
        fieldPhone.setText(currentStudent.getPhone());
        fieldAddress.setText(currentStudent.getAddress());
        fieldFineStatus.setText(currentStudent.getFineStatusText());
        fieldFine.setText(Integer.toString(currentStudent.getFine()));
        fieldUsername.setText(currentStudent.getUsername());
        fieldPassword.setText(currentStudent.getPassword());

        try {
        	UserDAO userDAO = new UserDAO();
        	String imageURL = userDAO.getImageURL(currentStudent.getAccount());

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
    private TextField fieldClass;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldFine;

    @FXML
    private TextField fieldFineStatus;

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
    private TextField fieldStudentID;

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
    private BorrowHistorySceneController borrowHistorySceneController;

    @FXML
    void btnActionChangePassword(ActionEvent event) {
    	if (!btnChangePassword.isSelected()) {
    		currentStudent.getAccount().setPassword(fieldPassword.getText());

    		try {
    			UserDAO userDAO = new UserDAO();
				userDAO.updatePassword(currentStudent.getAccount());
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
    		currentStudent.setName(fieldName.getText());
    		currentStudent.setEmail(fieldEmail.getText());
    		currentStudent.setPhone(fieldPhone.getText());
    		currentStudent.setAddress(fieldAddress.getText());

    		try {
    			StudentDAO studentDAO = new StudentDAO();
				studentDAO.updateStudent(currentStudent);
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
                    userDAO.updateImageURL(currentStudent.getAccount(), imageURL);
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
        assert fieldAddress != null : "fx:id=\"fieldAddress\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldClass != null : "fx:id=\"fieldClass\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldEmail != null : "fx:id=\"fieldEmail\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldFine != null : "fx:id=\"fieldFine\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldFineStatus != null : "fx:id=\"fieldFineStatus\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldGender != null : "fx:id=\"fieldGender\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldName != null : "fx:id=\"fieldName\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldPassword != null : "fx:id=\"fieldPassword\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldPhone != null : "fx:id=\"fieldPhone\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldStudentID != null : "fx:id=\"fieldStudentID\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";
        assert fieldUsername != null : "fx:id=\"fieldUsername\" was not injected: check your FXML file 'StudentInfoScene.fxml'.";

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

    @FXML
    private AnchorPane paneInfo;

    @FXML
    private AnchorPane paneBorrow;

    @FXML
    void btnActionBorrowHistory(ActionEvent event) throws SQLException {
    	paneInfo.setVisible(false);
    	paneBorrow.setVisible(true);

        borrowHistorySceneController.setThisStudentID(currentStudent.getStudentID());
        borrowHistorySceneController.refresh();
        borrowHistorySceneController.setParent(this);
    }

    public void btnActionReturn() {
    	paneInfo.setVisible(true);
    	paneBorrow.setVisible(false);
    }
}
