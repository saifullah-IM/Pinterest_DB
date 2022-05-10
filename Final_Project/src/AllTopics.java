import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AllTopics {
    private Main main;
    private Stage stage;
    AllTopics (Main main) {
        this.main=main;
        stage=main.stage;
    }


    public void show () throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("allTopics.fxml"));
        Parent root = loader.load();

        allTopicsController controller = loader.getController();
        controller.initialize(main,this);

        stage.setTitle(main.getAlias());
        stage.setScene(new Scene(root, 693.0, 534.0));
        stage.setResizable(false);
        stage.show();
    }
}
