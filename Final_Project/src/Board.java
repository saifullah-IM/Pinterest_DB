import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;

public class Board {
    private Main main;
    private Stage stage;
    private int board_id;

    Board (Main main,int board_id) {
        this.main=main;
        this.board_id=board_id;
        stage=main.stage;
    }


    public void show() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("board.fxml"));
        Parent root = loader.load();

        boardController controller = loader.getController();
        controller.initialize(main,this,board_id);

        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql = "select board_title from board where id= "+board_id;
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        String title="";
        while (rs.next()) {
            title=rs.getString("board_title");
        }


        stage.setTitle(title);
        stage.setScene(new Scene(root, 753.0, 535.0));
        stage.setResizable(false);
        stage.show();
    }
}
