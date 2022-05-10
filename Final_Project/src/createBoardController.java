import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class createBoardController {
    public TextField titleBox;
    public Button topicButton;
    private Main main;
    private String topicName;

    public void initialize (Main main) {
        this.main=main;
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        main.goBack();
    }

    public void go(ActionEvent actionEvent) throws Exception {
        Go gg = new Go(main);
        gg.show();
    }

    public void finish(ActionEvent actionEvent) throws Exception {
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "insert into board (creator_alias,board_topic,board_title) values('"+main.getAlias()+"','"+topicName +"','"+titleBox.getText()+"')";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        stmt.executeUpdate(sql);

        sql = "select board_id from latest_board where id=1";
        stmt = adapter.conn.createStatement();
        ResultSet rs=stmt.executeQuery(sql);

        while (rs.next()) {
            int board_id=rs.getInt("board_id");
            Board board = new Board(main,board_id);
            board.show();
        }
    }


    public void selectTopic(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("selectTopic.fxml"));
        Parent root = loader.load();

        selectTopicController controller = loader.getController();
        controller.initialize(main,this);

        Stage stage=new Stage();

        stage.setTitle("Select a Board");
        stage.setScene(new Scene(root, 468.0, 630.0));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

    public void selected (String topicName) {
        topicButton.setText(topicName);
        this.topicName=topicName;
    }
}
