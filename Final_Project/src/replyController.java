import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class replyController {
    @FXML
    private TextField replyBox;
    @FXML
    AnchorPane replyAnchor;
    private Main main;
    private int comment_id;

    public void initialize (int comment_id,Main main) throws Exception {
        this.main=main;
        this.comment_id=comment_id;
        replyAnchor.getChildren().clear();
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select person_alias,description from reply where comment_id="+comment_id;
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
                line.setTranslateY(50+30*y);
                line.setFont(Font.font ("Verdana", 20));
                replyAnchor.getChildren().add(line);

                ++y;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addReply() throws Exception {
        String reply=replyBox.getText();
        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "insert into reply values(" + comment_id +",'" + main.getAlias()+"','"+reply+"');";
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        stmt.executeUpdate(sql);

        initialize(comment_id,main);
    }
}
