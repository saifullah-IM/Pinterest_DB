import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.Statement;

public class createPinController {
    public Button boardButton;
    public AnchorPane mainPage;
    public TextField titleBox;
    public TextField descriptionBox;
    private Main main;
    private CreatePin createPin;

    public void initialize (Main main,CreatePin createPin) throws Exception {
        this.main=main;
        this.createPin=createPin;
        Image image = new Image(new FileInputStream(createPin.getSource()));

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(236.0);
        imageView.setFitHeight(360.0);
        imageView.setTranslateX(20);
        imageView.setTranslateY(20);
        ///imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        mainPage.getChildren().add(imageView);
    }

    public void selectBoard(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("selectBoard.fxml"));
        Parent root = loader.load();

        selectBoardController controller = loader.getController();
        controller.initialize(main,createPin,this);

        Stage stage=new Stage();

        stage.setTitle("Select a Board");
        stage.setScene(new Scene(root, 468.0, 630.0));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        main.goBack();
    }

    public void go(ActionEvent actionEvent) throws Exception {
        Go gg=new Go(main);
        gg.show();
    }

    public void finish(ActionEvent actionEvent) throws Exception {
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "insert into pin (image_src,title,date_created,description,creator_alias) values('"+createPin.getSource()+"','"+titleBox.getText()+"', current_date ,'"+descriptionBox.getText()+"','"+main.getAlias()+"')";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        stmt.executeUpdate(sql);

        sql = "select pin_id from latest_pin where id=1";
        stmt = adapter.conn.createStatement();
        ResultSet rs=stmt.executeQuery(sql);

        while (rs.next()) {
            int pin_id=rs.getInt("pin_id");
            sql = "insert into board_pin_relationship_record values ("+createPin.getBoard_id()+","+pin_id+")";
            System.out.println(sql);
            stmt = adapter.conn.createStatement();
            stmt.executeUpdate(sql);
            Pin pin = new Pin(main,pin_id);
            pin.show();
        }

    }

    public void selected (String boardName) {
        boardButton.setText(boardName);
    }
}
