import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewsFeed {
    private Main main;
    private Stage stage;
    NewsFeed (Main main) {
        this.main=main;
        stage=main.stage;
    }
    public void show() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newsFeed.fxml"));
        Parent root = loader.load();

        newsFeedController controller = loader.getController();
        controller.initialize(main,this);

        stage.setTitle("News Feed");
        stage.setScene(new Scene(root, 803.0, 631.0));
        stage.setResizable(false);
        stage.show();
    }
}
