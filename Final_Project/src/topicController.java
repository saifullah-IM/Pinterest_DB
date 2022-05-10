import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class topicController {
    @FXML
    private Button followButton;
    @FXML
    private TextField aliasField;
    @FXML
    private TextField topicNameField;

    private Topic topicPage;
    private Main main;
    private String topic;
    @FXML
    private TilePane pins;

    public void initialize (Main main,Topic topicPage,String topic) throws SQLException {
        this.topic=topic;
        this.main=main;
        this.topicPage=topicPage;
        try {
            showPins();
        } catch (Exception e) {
            e.printStackTrace();
        }

        topicNameField.setText(topic);
        topicNameField.setEditable(false);
        aliasField.setText(main.getAlias());
        aliasField.setEditable(false);

        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql="select is_topic_followed('"+topic+"','"+main.getAlias()+"')";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while (rs.next()) {
            int val=rs.getInt("is_topic_followed");
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

        String sql = "select id, image_src from pin where id in (select pin_id from board_pin_relationship_record where board_id in (select id from board where board_topic='"+topic+"')) order by date_created desc";
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
                            main.windows.push(topicPage);
                            pin.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            };

    @FXML
    public void showBoards() throws Exception {
        BoardsList boardsList=new BoardsList(main,topic);
        main.windows.push(topicPage);
        boardsList.show();
    }


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

            String sql = "insert into topic_follow_record values('"+main.getAlias()+"','"+topic+"')";
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

            String sql = "delete from topic_follow_record where follower_alias='"+main.getAlias()+"' and topic_name='"+topic+"'";
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
