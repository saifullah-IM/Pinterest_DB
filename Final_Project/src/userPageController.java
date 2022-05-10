import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class userPageController {
    public Button followButton;
    @FXML
    private AnchorPane userPane;
    @FXML
    private TextField aliasField;
    @FXML
    private TextField userField;

    private User userPage;
    private Main main;
    private String userName;

    public void initialize (Main main,User userPage,String userName) throws Exception {
        this.main=main;
        this.userPage=userPage;
        this.userName=userName;
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select * from board where creator_alias = '" + userName + "' ";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try {
            int y=0;
            while (rs.next()) {
                String boardName= rs.getString("board_title");
                int board_id=rs.getInt("id");
                Text line = new Text();
                line.setText(boardName);
                line.setTranslateX(14);
                line.setTranslateY(161+30*y);
                line.setFont(Font.font ("Verdana", 20));
                line.setOnMousePressed(showBoard);
                line.setUserData(board_id);
                userPane.getChildren().add(line);
                ++y;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        aliasField.setText(main.getAlias());
        aliasField.setEditable(false);
        userField.setText(userName);
        userField.setEditable(false);

        sql="select is_user_followed('"+main.getAlias()+"','"+userName+"')";
        rs=stmt.executeQuery(sql);
        while (rs.next()) {
            int val=rs.getInt("is_user_followed");
            if (val==1) {
                followButton.setText("Unfollow");
            }
            else {
                followButton.setText("Follow");
            }
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
                            Board board=new Board(main,board_id);
                            main.windows.push(userPage);
                            board.show();
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

    public void toggleFollow(ActionEvent actionEvent) {
        if (followButton.getText().equals("Follow")) {
            followButton.setText("Unfollow");
            DbAdapter adapter = new DbAdapter();
            adapter.connect();

            String sql = "insert into user_follow_record values('"+main.getAlias()+"','"+userName+"')";
            System.out.println(sql);

            Statement stmt = null;
            try {
                stmt = adapter.conn.createStatement();
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            followButton.setText("Follow");
            DbAdapter adapter = new DbAdapter();
            adapter.connect();

            String sql = "delete from user_follow_record where follower_alias='"+main.getAlias()+"' and followed_alias='"+userName+"'";
            System.out.println(sql);

            Statement stmt = null;
            try {
                stmt = adapter.conn.createStatement();
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
