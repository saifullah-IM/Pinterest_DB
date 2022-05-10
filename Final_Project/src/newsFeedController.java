import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class newsFeedController {

  /*  @FXML
    private homePage home;

    @FXML
    private newsFeed thisFeed;

    @FXML
    private notifications notice; */

    @FXML
    private TextField alias;

    @FXML
    private TilePane pins;

    @FXML
    private TextField searchField;

    private Main main;
    @FXML
    private NewsFeed newsFeed;

    public void initialize (Main main,NewsFeed newsFeed) throws SQLException {
        this.newsFeed=newsFeed;
        this.main=main;
        alias.setText(main.getAlias());
        alias.setFont(Font.font ("Verdana", 20));
        alias.setEditable(false);
        initPins();
        defaultPins();
    }

    public void defaultPins () throws SQLException {
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select distinct id, image_src,date_created from pin where id in (select pin_id from board_pin_relationship_record where board_id in (select board_id from board_follow_record where follower_alias='"+main.getAlias()+"' ) or board_id in (select id from board where board_topic in (select topic_name from topic_follow_record where follower_alias='"+main.getAlias()+"'))) order by date_created desc";
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
                if (cnt==10) break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPins () {
        pins.setPrefColumns(5);
        pins.setPrefRows(2);
        pins.setHgap(10.0);
        pins.setVgap(10.0);
    }

    private VBox createImage (String directory,int pin_id) throws FileNotFoundException {
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
    public void searchAction () throws SQLException {
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String toSearch = searchField.getText();
        System.out.println(toSearch);

        StringTokenizer stringTokenizer = new StringTokenizer(toSearch,", ");
        int cnt=0;

        String sql = "select distinct id, image_src,date_created from pin where id in (select pin_id from board_pin_relationship_record where board_id in (select board.id from board where board_topic in (";

        while (stringTokenizer.hasMoreTokens())
        {
            String str = stringTokenizer.nextToken();
            if (cnt!=0) sql=sql+",";
            sql=sql+"'"+str+"'";
            ++cnt;
            System.out.println(str);
        }
        sql=sql+"))) order by date_created desc";
        System.out.println(sql);

        if (cnt==0) return;

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try {
            cnt=0;
            pins.getChildren().clear();
            while (rs.next()) {
                String directory=rs.getString("image_src");
                int pin_id=rs.getInt("id");
                System.out.println(directory);
                pins.getChildren().add(createImage(directory,pin_id));
                ++cnt;
                if (cnt==10) break;
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
                            Pin pin = new Pin(main,pin_id);
                            main.windows.push(newsFeed);
                            System.out.println("Stack size = " + main.windows.size());
                            pin.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

    };

    @FXML
    private void homeAction () {
        try {
            HomePage homePage = new HomePage(main);
            main.windows.push(newsFeed);
            homePage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void notificationsAction () {
        try {
            Notifications notifications=new Notifications(main,newsFeed);
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent actionEvent) throws Exception {
        main.showLoginMenu();
    }
}
