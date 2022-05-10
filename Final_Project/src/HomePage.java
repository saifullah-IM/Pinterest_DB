import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePage {
    private Main main;
    private Stage stage;
    HomePage (Main main) {
        this.main=main;
        stage=main.stage;
    }

    public void show() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();

        homePageController controller = loader.getController();
        controller.initialize(main,this);

        stage.setTitle("Home Page");
        stage.setScene(new Scene(root, 693.0, 534.0));
        stage.setResizable(false);
        stage.show();
    }
}
