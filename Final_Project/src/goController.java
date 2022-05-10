import javafx.fxml.FXML;
import javafx.stage.Stage;

public class goController {

    private Main main;
    private Stage stage;

    public void initialize (Main main,Stage stage) {
        this.main=main;
        this.stage=stage;
    }

    @FXML
    private void homeAction() {
        try {
            HomePage homePage = new HomePage(main);
            homePage.show();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showNewsFeed() {
        try {
            NewsFeed newsFeed = new NewsFeed(main);
            newsFeed.show();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
