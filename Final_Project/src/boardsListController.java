import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.Statement;

public class boardsListController {

    @FXML
    private AnchorPane boardsAnchor;
    private Main main;

    public void initialize (Main main,String topic) throws Exception {
        this.main=main;
        boardsAnchor.getChildren().clear();
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select * from board where board_topic='" + topic + "'";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try {
            int y=0;
            while (rs.next()) {
                String user= rs.getString("board_title");
                int board_id= rs.getInt("id");
                Text line = new Text();
                line.setText(user);
                line.setTranslateX(50);
                line.setTranslateY(50+40*y);
                line.setFont(Font.font ("Verdana", 20));
                line.setOnMousePressed(showBoard);
                line.setUserData(board_id);
                boardsAnchor.getChildren().add(line);
                ++y;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    EventHandler<MouseEvent> showBoard =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    System.out.println("showing board");

                    Object obj = t.getSource();

                    if (obj instanceof Text) {
                        DbAdapter adapter = new DbAdapter();
                        adapter.connect();

                        int board_id = ((int) ((Text) obj).getUserData());

                        try {
                            Board board = new Board(main,board_id);
                            board.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
}
