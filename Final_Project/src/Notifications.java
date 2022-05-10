import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Notifications {
    private Stage stage;
    private Main main;
    private Object O;

    Notifications (Main main,Object O) {
        this.O=O;
        this.main=main;
        stage=new Stage();
    }

    public void show() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("notifications.fxml"));
        Parent root = loader.load();

        stage.setTitle("Notifications");
        stage.setScene(new Scene(root, 700.0, 700.0));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        notificationController controller = loader.getController();
        controller.initialize(main,O);

        stage.showAndWait();
    }
}
