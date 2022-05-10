import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.Statement;

public class showLikersController {

    @FXML
    private AnchorPane likersAnchor;
    private Main main;
    private Pin pin;

    public void initialize (Main main,Pin pin,int pin_id) throws Exception {
        this.main=main;
        this.pin=pin;
        likersAnchor.getChildren().clear();
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select person_alias from likes where pin_id ="+ pin_id;
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try {
            int y=0;
            while (rs.next()) {
                String liker= rs.getString("person_alias");
                Text line = new Text();
                line.setText(liker);
                line.setTranslateX(50);
                line.setTranslateY(50+40*y);
                line.setFont(Font.font ("Verdana", 20));
                line.setOnMousePressed(showUser);
                line.setUserData(liker);
                likersAnchor.getChildren().add(line);
                ++y;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    EventHandler<MouseEvent> showUser =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    System.out.println("showing topic");

                    Object obj = t.getSource();

                    if (obj instanceof Text) {
                        DbAdapter adapter = new DbAdapter();
                        adapter.connect();

                        String followedUser = ((String) ((Text) obj).getUserData());

                        try {
                            User userPage=new User(main,followedUser);
                            main.windows.push(pin);
                            userPage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
}
