import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.EventListener;
import java.util.Stack;

public class Main extends Application {
    public Stack<Object> windows;
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        windows = new Stack<>();
        stage=primaryStage;
        showStartMenu();
    }

    public void showStartMenu() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("loginPage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        loginController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Pinterest");
        stage.setScene(new Scene(root, 728.0, 492.0));
        stage.setResizable(false);
        stage.show();
    }


    public void showSignUpMenu() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SignupPage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        signUpController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("SignUp Page");
        stage.setScene(new Scene(root, 719.0, 478.0));
        stage.setResizable(false);
        stage.show();
    }

    public void showLoginMenu() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Loading the controller
        loginpageController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Login Page");
        stage.setScene(new Scene(root, 645.0, 443.0));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void goBack () throws Exception {
        if (windows.empty()) System.out.println("stack is empty");
        Object O=windows.pop();
        if (O instanceof AllTopics) ((AllTopics)O).show();
        else if (O instanceof Board) ((Board)O).show();
        else if (O instanceof BoardsList) ((BoardsList)O).show();
        else if (O instanceof HomePage) ((HomePage)O).show();
        else if (O instanceof NewsFeed) ((NewsFeed)O).show();
        else if (O instanceof Pin) ((Pin)O).show();
        else if (O instanceof Topic) ((Topic)O).show();
        else if (O instanceof User) ((User)O).show();
        else System.out.println("Hatir matha");
    }
}
