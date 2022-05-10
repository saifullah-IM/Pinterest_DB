import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class boardController {


    @FXML
    Button followButton;
    @FXML
    private Button creatorButton;
    @FXML
    private Button topicName;
    @FXML
    private TextField boardName;
    @FXML
    private TextField aliasField;

    private Main main;
    private int board_id;
    private String creator;
    private String topic;
    private String boardTitle;
    private Board board;

    @FXML
    private TilePane pins;

    public void initialize (Main main,Board board,int board_id) throws Exception {
        this.board_id=board_id;
        this.board=board;
        this.main=main;
        try {
            showPins();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql= "select * from board where id ="+board_id;
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            creator = rs.getString("creator_alias");
            topic = rs.getString("board_topic");
            boardTitle = rs.getString("board_title");
        }

        creatorButton.setText("Creator: "+ creator);
        topicName.setText(topic);
        boardName.setText(boardTitle);
        boardName.setEditable(false);
        aliasField.setText(main.getAlias());
        aliasField.setEditable(false);

        sql="select is_board_followed("+board_id+",'"+main.getAlias()+"')";
        rs=stmt.executeQuery(sql);
        while (rs.next()) {
            int val=rs.getInt("is_board_followed");
            if (val==1) {
                followButton.setText("Unfollow");
            }
            else {
                followButton.setText("Follow");
            }
        }
    }

    private VBox createImage (String directory, int pin_id) throws FileNotFoundException {
        System.out.println("creating image");
        System.out.println(directory);


        Image image = new Image(new FileInputStream(directory));


        System.out.println("imaging done");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(118.0);
        imageView.setFitHeight(180.0);
        ///imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setOnMousePressed(showPin);
        imageView.setUserData(pin_id);

        VBox imageBox = new VBox();
        imageBox.getChildren().add(imageView);
        imageBox.setStyle("-fx-border-color: orange");
        return imageBox;
    }

    @FXML
    public void showPins () throws SQLException {
        pins.setPrefColumns(6);
        pins.setPrefRows(3);
        pins.setHgap(10.0);
        pins.setVgap(10.0);

        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select id, image_src from pin where id in (select pin_id from board_pin_relationship_record where board_id= "+board_id+") order by date_created desc";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try {
            int cnt=0;
            pins.getChildren().clear();
            while (rs.next()) {
                String directory=rs.getString("image_src");
                int pin_id=rs.getInt("id");
                System.out.println(directory);
                pins.getChildren().add(createImage(directory,pin_id));
                ++cnt;
                if (cnt==18) break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    EventHandler<MouseEvent> showPin =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    Object obj = t.getSource();

                    if (obj instanceof ImageView) {
                        DbAdapter adapter = new DbAdapter();
                        adapter.connect();

                        int pin_id = ((int) ((ImageView) obj).getUserData());

                        try {
                            Pin pin=new Pin(main,pin_id);
                            main.windows.push(board);
                            pin.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            };

    @FXML
    public void showCreator() {
        try {
            User userPage=new User(main,creator);
            main.windows.push(board);
            userPage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showTopic() {
        try {
            Topic topicPage=new Topic(main,topic);
            main.windows.push(board);
            topicPage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backAction() {
        try {
            main.goBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void createBoard() throws IOException {
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

    public void toggleFollow() {
        if (followButton.getText().equals("Follow")) {
            followButton.setText("Unfollow");
            DbAdapter adapter = new DbAdapter();
            adapter.connect();

            String sql = "insert into board_follow_record values('"+main.getAlias()+"',"+board_id+")";
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

            String sql = "delete from board_follow_record where follower_alias='"+main.getAlias()+"' and board_id="+board_id;
            System.out.println(sql);

            Statement stmt = null;
            try {
                stmt = adapter.conn.createStatement();
                stmt.executeUpdate(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
