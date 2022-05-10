import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class homePageController {


    @FXML
    private TextField aliasField;
    @FXML
    private AnchorPane myBoardsAnchor;
    @FXML
    private AnchorPane followedBoardsAnchor;
    @FXML
    private AnchorPane topicsAnchor;
    @FXML
    private AnchorPane usersAnchor;

    private HomePage homePage;

    private Main main;

    private void showMyBoards () throws SQLException {
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select id,board_title from board where creator_alias = '" + main.getAlias() + "' ";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try
        {
            int y=0;
            while (rs.next())
            {
                String boardName= rs.getString("board_title");
                int board_id=rs.getInt("id");
                Text line = new Text();
                line.setText(boardName);
                line.setTranslateX(50);
                line.setTranslateY(50+40*y);
                line.setFont(Font.font ("Verdana", 20));
                line.setOnMousePressed(showBoard);
                line.setUserData(board_id);
                myBoardsAnchor.getChildren().add(line);
                ++y;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void showFollowedBoards () throws SQLException {
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select id,board_title from board where id in (select board_id from board_follow_record where follower_alias= '" + main.getAlias() +"')";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try
        {
            int y=0;
            while (rs.next())
            {
                String boardName= rs.getString("board_title");
                int board_id=rs.getInt("id");
                Text line = new Text();
                line.setText(boardName);
                line.setTranslateX(50);
                line.setTranslateY(50+40*y);
                line.setFont(Font.font ("Verdana", 20));
                line.setOnMousePressed(showBoard);
                line.setUserData(board_id);
                followedBoardsAnchor.getChildren().add(line);
                ++y;
            }
        }
        catch (Exception e)
        {
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
                            Board board=new Board(main,board_id);
                            main.windows.push(homePage);
                            board.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

    private void showTopics () throws SQLException {
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select topic_name from topic_follow_record where follower_alias= '" + main.getAlias() + "'";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try
        {
            int y=0;
            while (rs.next())
            {
                String topicName= rs.getString("topic_name");
                Text line = new Text();
                line.setText(topicName);
                line.setTranslateX(50);
                line.setTranslateY(50+40*y);
                line.setFont(Font.font ("Verdana", 20));
                line.setOnMousePressed(showTopic);
                line.setUserData(topicName);
                topicsAnchor.getChildren().add(line);
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
                            Topic topicPage = new Topic(main,topic);
                            main.windows.push(homePage);
                            topicPage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            };

    private void showUsers () throws SQLException {
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select followed_alias from user_follow_record where follower_alias='" + main.getAlias() + "'";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try
        {
            int y=0;
            while (rs.next())
            {
                String followedUser= rs.getString("followed_alias");
                Text line = new Text();
                line.setText(followedUser);
                line.setTranslateX(50);
                line.setTranslateY(50+40*y);
                line.setFont(Font.font ("Verdana", 20));
                line.setOnMousePressed(showUser);
                line.setUserData(followedUser);
                usersAnchor.getChildren().add(line);
                ++y;
            }
        }
        catch (Exception e)
        {
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
                            main.windows.push(homePage);
                            userPage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };


    @FXML
    private void showAllTopics () throws Exception {
        AllTopics allTopics=new AllTopics(main);
        main.windows.push(homePage);
        allTopics.show();
    }
    public void createBoard(ActionEvent actionEvent) throws IOException {
        main.windows.push(homePage);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("createBoard.fxml"));
        Parent root = loader.load();
        Stage stage=main.stage;

        createBoardController controller = loader.getController();
        controller.initialize(main);

        stage.setTitle("Creating Board");
        stage.setScene(new Scene(root, 835.0, 580.0));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void createPin() throws Exception {
        main.windows.push(homePage);
        CreatePin createPin = new CreatePin(main);
        createPin.create();
    }

    @FXML
    private void showNewsFeed() {
        try {
            NewsFeed newsFeed = new NewsFeed(main);
            main.windows.push(homePage);
            newsFeed.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void notificationsAction () {
        try {
            Notifications notifications=new Notifications(main,homePage);
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize (Main main,HomePage homePage) {
        this.homePage=homePage;
        this.main=main;
        aliasField.setText(main.getAlias());
        aliasField.setEditable(false);

        try {
            showMyBoards();
            showFollowedBoards();
            showTopics();
            showUsers();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent actionEvent) throws Exception {
        main.showLoginMenu();
    }
}
