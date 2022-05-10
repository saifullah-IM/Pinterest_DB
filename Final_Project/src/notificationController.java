import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.Statement;

public class notificationController {
    @FXML
    private AnchorPane notificationAnchor;
    private Main main;
    private Object O;

    public void initialize (Main main,Object O) throws Exception {
        this.main=main;
        this.O=O;
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select * from notification where notification_to = '" + main.getAlias() + "'";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int y=0;
        while(rs.next()) {
            String notification= rs.getString("notification_text");
            String data=rs.getString("notification_data");
            Text line = new Text();
            line.setText(notification);
            line.setTranslateX(50);
            line.setTranslateY(50+40*y);
            line.setFont(Font.font ("Verdana", 20));
            line.setUserData(data);
            line.setOnMousePressed(handle);
            notificationAnchor.getChildren().add(line);
            ++y;
        }
    }

    EventHandler<MouseEvent> handle =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    System.out.println("going to a notification");

                    Object obj = t.getSource();

                    if (obj instanceof Text) {
                        DbAdapter adapter = new DbAdapter();
                        adapter.connect();

                        String data = ((String) ((Text) obj).getUserData());

                        try {
                            if (data.substring(0,1).equals("p")) {
                                int pin_id=Integer.parseInt(data.substring(1));
                                main.windows.push(O);
                                Pin pin = new Pin(main,pin_id);
                                pin.show();

                            }
                            else if (data.substring(0,1).equals("b")) {
                                int board_id=Integer.parseInt(data.substring(1));
                                main.windows.push(O);
                                Board board=new Board(main,board_id);
                                board.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
}
