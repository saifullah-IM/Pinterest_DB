import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class commentController {
    @FXML
    private TextField commentBox;
    @FXML
    AnchorPane commentPane;
    int pin_id;
    private Main main;

    public void initialize (int pin_id,Main main) throws Exception {
        this.main=main;
        this.pin_id=pin_id;
        commentPane.getChildren().clear();
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select * from pin_comments where pin_id ="+ pin_id;
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        try {
            int y=0;
            while (rs.next()) {
                String comment= rs.getString("description")+ "---" + rs.getString("person_alias");
                Text line = new Text();
                line.setText(comment);
                line.setTranslateX(50);
                line.setTranslateY(50+60*y);
                line.setFont(Font.font ("Verdana", 20));
                commentPane.getChildren().add(line);

                TextField replyButton = new TextField();
                replyButton.setPrefHeight(38);
                replyButton.setPrefWidth(77);
                replyButton.setStyle("-fx-background-color: #66B2FF");
                replyButton.setFont(Font.font ("Verdana", 16));
                replyButton.setTranslateX(500);
                replyButton.setTranslateY(20+60*y);
                replyButton.setText("Reply");
                replyButton.setEditable(false);
                replyButton.setUserData(rs.getInt("id"));
                replyButton.setOnMousePressed(showReplies);
                commentPane.getChildren().add(replyButton);

                y++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    EventHandler<MouseEvent> showReplies =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    Object obj = t.getSource();

                    if ( obj instanceof TextField ) {
                        int comment_id=((int)((TextField)obj).getUserData());

                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("reply.fxml"));
                            Parent root = loader.load();
                            Stage stage=new Stage();

                            // Loading the controller
                            replyController controller = loader.getController();
                            controller.initialize(comment_id,main);

                            // Set the primary stage
                            stage.setTitle("Replies");
                            stage.setScene(new Scene(root, 760.0, 565.0));
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setResizable(false);
                            stage.showAndWait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            };



    @FXML
    public void addComment() throws Exception {
        String comment=commentBox.getText();
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "insert into pin_comments(pin_id,person_alias,description) values(" + pin_id +",'" + main.getAlias()+"','"+comment+"');";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        stmt.executeUpdate(sql);

        initialize(pin_id,main);
    }
}
