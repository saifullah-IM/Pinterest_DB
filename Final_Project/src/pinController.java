import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class pinController {

    @FXML
    private Button likeButton;
    @FXML
    private Button creatorButton;

    private Main main;
    private int pin_id;
    private String creator;

    @FXML
    private AnchorPane imagePane;
    @FXML
    private AnchorPane descriptionPane;
    private Pin pin;

    public void initialize (Main main,Pin pin,int pin_id) throws Exception {
        this.main=main;
        this.pin=pin;
        this.pin_id=pin_id;

        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql= "select image_src,creator_alias,description from pin where id=" + pin_id;
        System.out.println(sql);

        String directory="",description="";

        Statement stmt=null;
        try {
            stmt = adapter.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                directory = rs.getString("image_src");
                creator = rs.getString("creator_alias");
                description=rs.getString("description");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Image image = new Image(new FileInputStream(directory));

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(295.0);
        imageView.setFitHeight(450.0);
        imageView.setTranslateX(20);
        imageView.setTranslateY(20);
        ///imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        imagePane.getChildren().add(imageView);

        creatorButton.setText("Creator: "+creator);

        Text line = new Text();
        line.setText(description);
        line.setTranslateX(15);
        line.setTranslateY(85);
        line.setFont(Font.font ("Verdana", 20));
        descriptionPane.getChildren().add(line);

        sql="select is_liked("+pin_id+",'"+main.getAlias()+"')";
        System.out.println(sql);

        try {
            stmt = adapter.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int val=rs.getInt("is_liked");
                if (val==1) {
                    likeButton.setText("Liked");
                }
                else {
                    likeButton.setText("Like");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showLikers () throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("showLikers.fxml"));
        Parent root = loader.load();

        // Loading the controller
        showLikersController controller = loader.getController();
        controller.initialize(main,pin,pin_id);

        Stage stage=new Stage();

        // Set the primary stage
        stage.setTitle("Likers");
        stage.setScene(new Scene(root, 390.0, 612.0));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    private void backAction () {
        try {
            main.goBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCreator () {
        try {
            User userPage=new User(main,creator);
            main.windows.push(pin);
            userPage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void createPin() {
        main.windows.push(pin);
        CreatePin createPin = new CreatePin(main);
        createPin.create();
    }

    @FXML
    public void showComments() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("comment.fxml"));
        Parent root = loader.load();

        // Loading the controller
        commentController controller = loader.getController();
        controller.initialize(pin_id,main);

        Stage stage=new Stage();

        // Set the primary stage
        stage.setTitle("Comments");
        stage.setScene(new Scene(root, 760.0, 565.0));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    private void toggleLike() {
        System.out.println("toggle Like");
        if (likeButton.getText().equals("Like")) {
            DbAdapter adapter = new DbAdapter();
            adapter.connect();

            String sql = "insert into likes values(" + pin_id +",'" + main.getAlias()+"');";
            System.out.println(sql);

            Statement stmt = null;
            try {
                stmt = adapter.conn.createStatement();
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            likeButton.setText("Liked");
        }
        else {
            DbAdapter adapter = new DbAdapter();
            adapter.connect();

            String sql = "delete from likes where pin_id= "+pin_id +" and person_alias='"+main.getAlias()+"'";
            System.out.println(sql);

            Statement stmt = null;
            try {
                stmt = adapter.conn.createStatement();
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            likeButton.setText("Like");
        }
    }

    public void addToBoard(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addToBoard.fxml"));
        Parent root = loader.load();

        addToBoardController controller = loader.getController();
        controller.initialize(main,pin_id);

        Stage stage=new Stage();

        stage.setTitle("Add to Boards");
        stage.setScene(new Scene(root, 468.0, 630.0));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

    public void goAction(ActionEvent actionEvent) throws Exception {
        Go gg = new Go(main);
        main.windows.push(pin);
        gg.show();
    }
}
