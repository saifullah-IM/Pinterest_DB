import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.Statement;

public class selectTopicController {
    public AnchorPane mainPage;
    private Main main;
    private createBoardController CBController;

    public void initialize (Main main,createBoardController CBController) throws Exception {
        this.main=main;
        this.CBController=CBController;
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select * from topic";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int y=0;
        while (rs.next()) {
            String topicName= rs.getString("name");
            Text line = new Text();
            line.setText(topicName);
            line.setTranslateX(50);
            line.setTranslateY(50+50*y);
            line.setFont(Font.font ("Verdana", 20));
            line.setOnMousePressed(selected);
            line.setUserData(topicName);
            mainPage.getChildren().add(line);
            ++y;
        }
    }

    EventHandler<MouseEvent> selected =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    System.out.println("topic selected");

                    Object obj = t.getSource();

                    if (obj instanceof Text) {
                        String topicName = ((String) ((Text) obj).getUserData());
                        CBController.selected(topicName);
                    }
                }

            };


}


