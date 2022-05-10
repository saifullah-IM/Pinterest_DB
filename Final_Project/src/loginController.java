import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;

public class loginController {

    @FXML
    private ImageView Back_image;

    @FXML
    private Button SignUPButton;

    @FXML
    private Button SignInButton;
    private Main main;

    @FXML
    public void signInAction(javafx.event.ActionEvent actionEvent) {
        try{
            main.showLoginMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void signUPAction(javafx.event.ActionEvent actionEvent) {
        try{
            main.showSignUpMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        this.main=main;
    }
}

