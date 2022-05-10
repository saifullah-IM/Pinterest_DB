import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class loginpageController {

    private Main main;

    @FXML
    private AnchorPane LoginMenu;

    @FXML
    private Label Label1;

    @FXML
    private Label Label2;

    @FXML
    private TextField aliasTextfield;

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

    @FXML
    private PasswordField passField;

    @FXML
    public void backAction(javafx.event.ActionEvent actionEvent) {
        try{
            main.showStartMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void submitAction(javafx.event.ActionEvent actionEvent) throws SQLException {
        String alias = aliasTextfield.getText();
        String password = passField.getText();
        if(hasAccount(alias,password)) {
            System.out.println("Logged in");
            try {
                main.windows.clear();
                main.setAlias(alias);
                NewsFeed newsFeed = new NewsFeed(main);
                newsFeed.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        else
            System.out.println("Try again");
    }

    public boolean hasAccount(String alias, String password) throws SQLException {

        DbAdapter adapter = new DbAdapter();
        int flag=0;
        adapter.connect();

        String sql = "Select * from person;" ;
        //System.out.println(sql);
        Statement stmt = adapter.conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        try {
            while (rs.next())
            {
                //System.out.println("kgsdkfgidgf");
                String Alias= rs.getString("alias");
                String pass= rs.getString("pass");

                if(Alias.equals(alias) && pass.equals(password))
                    flag=1;

            }


        }

        catch (Exception e)
        {
            e.printStackTrace();
        }


        adapter.disconnect();
        //System.out.println(flag);

        return  flag==1;


    }

    public void setMain(Main main) {
        this.main=main;
    }



}
