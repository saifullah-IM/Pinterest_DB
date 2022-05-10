import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Go {
    private Stage stage;
    private Main main;

    Go (Main main) {
        this.main=main;
        stage=new Stage();
    }

    public void show() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("go.fxml"));
        Parent root = loader.load();

        stage.setTitle("Going Back");
        stage.setScene(new Scene(root, 370.0, 203.0));
        stage.setResizable(false);

        goController controller = loader.getController();
        controller.initialize(main,stage);

        stage.show();
    }
}
