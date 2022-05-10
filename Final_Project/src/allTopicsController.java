import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class allTopicsController {

    @FXML
    private AnchorPane allTopicsAnchor;
    @FXML
    private TextField aliasField;
    private AllTopics allTopics;
    private Main main;

    public void initialize (Main main,AllTopics allTopics) throws SQLException {
        this.allTopics=allTopics;
        this.main=main;
        aliasField.setText(main.getAlias());
        aliasField.setEditable(false);

        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select * from topic";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try
        {
            int y=0;
            while (rs.next())
            {
                String topicName= rs.getString("name");
                Text line = new Text();
                line.setText(topicName);
                line.setTranslateX(50);
                line.setTranslateY(120+40*y);
                line.setFont(Font.font ("Verdana", 20));
                line.setOnMousePressed(showTopic);
                line.setUserData(topicName);
                allTopicsAnchor.getChildren().add(line);
                ++y;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    EventHandler<MouseEvent> showTopic =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    System.out.println("showing topic");

                    Object obj = t.getSource();

                    if (obj instanceof Text) {
                        DbAdapter adapter = new DbAdapter();
                        adapter.connect();

                        String topic = ((String) ((Text) obj).getUserData());

                        try {
                            Topic topicPage=new Topic(main,topic);
                            main.windows.push(allTopics);
                            topicPage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            };

    @FXML
    private void backAction() {
        try {
            main.goBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
