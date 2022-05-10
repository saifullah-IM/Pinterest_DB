import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class User {
    private Main main;
    private Stage stage;
    private String userName;

    User (Main main,String userName) {
        this.main=main;
        this.userName=userName;
        stage=main.stage;
    }


    public void show() throws Exception
    {
        if (userName.equals(main.getAlias())) {
            HomePage homePage=new HomePage(main);
            homePage.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("user.fxml"));
        Parent root = loader.load();

        userPageController controller = loader.getController();
        controller.initialize(main,this,userName);

        stage.setTitle(userName);
        stage.setScene(new Scene(root, 693.0, 534.0));
        stage.setResizable(false);
        stage.show();
    }
}
