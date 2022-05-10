import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BoardsList {
    private Main main;
    private String topic;
    private Stage stage;

    BoardsList (Main main,String topic) {
        this.main=main;
        stage=main.stage;
        this.topic=topic;
    }

    public void show() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("boardsList.fxml"));
        Parent root = loader.load();

        boardsListController controller = loader.getController();
        controller.initialize(main,topic);

        stage.setTitle("Boards in topic " +topic);
        stage.setScene(new Scene(root, 390.0,612));
        stage.setResizable(false);
        stage.show();
    }
}
