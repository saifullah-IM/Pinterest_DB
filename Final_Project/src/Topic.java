import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Topic {
    private Stage stage;
    private Main main;
    private String topicName;

    Topic (Main main,String topicName) {
        this.main=main;
        this.topicName=topicName;
        stage=main.stage;
    }


    public void show() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("topic.fxml"));
        Parent root = loader.load();

        topicController controller = loader.getController();
        controller.initialize(main,this,topicName);

        stage.setTitle(topicName);
        stage.setScene(new Scene(root, 753.0, 535.0));
        stage.setResizable(false);
        stage.show();
    }
}
