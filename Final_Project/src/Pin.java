import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;

public class Pin {
    private int pin_id;
    private Main main;
    private Stage stage;

    Pin (Main main,int pin_id) {
        this.main=main;
        stage=main.stage;
        this.pin_id=pin_id;
    }

    public void show() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("pin.fxml"));
        Parent root = loader.load();

        pinController controller = loader.getController();
        controller.initialize(main,this,pin_id);

        String title="Pin";

        DbAdapter adapter = new DbAdapter();
        adapter.connect();

        String sql= "select title from pin where id=" + pin_id;
        System.out.println(sql);

        Statement stmt = adapter.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            title = rs.getString("title");
        }

        stage.setTitle(title);
        stage.setScene(new Scene(root, 746.0, 651.0));
        stage.setResizable(false);
        stage.show();
    }
}
