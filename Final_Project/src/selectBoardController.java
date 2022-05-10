import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class selectBoardController {
    public AnchorPane mainPage;
    private Main main;
    private CreatePin createPin;
    private createPinController CPController;

    public void initialize (Main main,CreatePin createPin,createPinController CPController) throws Exception {
        this.main=main;
        this.createPin=createPin;
        this.CPController=CPController;
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select * from board where creator_alias = '" + main.getAlias() + "' ";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int y=0;
        while (rs.next()) {
            String boardName= rs.getString("board_title");
            int board_id=rs.getInt("id");
            Text line = new Text();
            line.setText(boardName);
            line.setTranslateX(50);
            line.setTranslateY(50+50*y);
            line.setFont(Font.font ("Verdana", 20));
            line.setOnMousePressed(selected);
            line.setUserData(board_id);
            mainPage.getChildren().add(line);
            ++y;
        }
    }

    EventHandler<MouseEvent> selected =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    System.out.println("board selected");

                    Object obj = t.getSource();

                    if (obj instanceof Text) {
                        int board_id = ((int) ((Text) obj).getUserData());
                        createPin.setBoard_id(board_id);
                        DbAdapter adapter = new DbAdapter();
                        adapter.connect();

                        String sql = "select * from board where id = " + board_id;
                        System.out.println(sql);

                        Statement stmt = null;
                        try {
                            stmt = adapter.conn.createStatement();
                            ResultSet rs = stmt.executeQuery(sql);
                            while (rs.next()) {
                                String boardName=rs.getString("board_title");
                                CPController.selected(boardName);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

            };


}


