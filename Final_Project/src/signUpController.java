import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.sql.*;

public class signUpController {

    @FXML
    private TextField PromptBox;

    @FXML
    private Label Label1;

    @FXML
    private Label Label2;

    @FXML
    private Label Label3;

    @FXML
    private Label Label4;

    @FXML
    private Label Label5;

    @FXML
    private TextField FirstNameBox;

    @FXML
    private TextField LastNameBox;

    @FXML
    private TextField AliasBox;

    @FXML
    private TextField emailBOx;

    @FXML
    private Button SubmitButton;

    @FXML
    private PasswordField PasswordBox;

    @FXML
    private Button BackButton;
    private Main main;

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
        String fname = FirstNameBox.getText();
        String lname = LastNameBox.getText();
        String full_name= fname + " " + lname;
        String alias= AliasBox.getText();
        String pass= PasswordBox.getText();
        String email = emailBOx.getText();

        if(fname.equals("") || lname.equals("") || alias.equals("") || pass.equals("") || email.equals(""))
        {
            System.out.println("1");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid");
            alert.setHeaderText(null);
            alert.setContentText("Field(s) cannot be empty.");

            alert.showAndWait();
        }

        else if(usedAlias(alias))
        {
            //System.out.println("2");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alais Error");
            alert.setHeaderText(null);
            alert.setContentText("Alias Already Used!");

            alert.showAndWait();
        }

        else if(usedEmail(email))
        {
            //System.out.println("3hATI");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email Error");
            alert.setHeaderText(null);
            alert.setContentText("Email Already Used!");

            alert.showAndWait();
        }

        else if(invalidPassword(pass))
        {
            //System.out.println("5");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Password");
            alert.setHeaderText(null);
            alert.setContentText("Password Length must be Between 8 and 16 characters");

            alert.showAndWait();
        }

        else
        {
            DbAdapter adapter = new DbAdapter();
            adapter.connect();

            try
            {
                String sql= "insert into person values('" + alias + "','"
                        + pass + "','" + full_name + "','" + email +"')";
                Statement stmt=  adapter.conn.createStatement();

                stmt.executeUpdate(sql);

                adapter.disconnect();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



            //System.out.println("6");
            /*PSQL.insert("insert into person values('" + alias + "','"
                    + pass + "','" + full_name + "','" + email +"')");*/
        }



    }

    public void setMain(Main main) {
        this.main=main;
    }

    public boolean usedEmail(String email) throws SQLException {
        DbAdapter adapter = new DbAdapter();
        int flag=0;
        adapter.connect();

        String sql = "Select * from person where email = '" + email + "';" ;
        //System.out.println(sql);
        Statement stmt = adapter.conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        try {
            while(rs.next())
            {
                String s=rs.getString("email");

                System.out.println(s);

                if(email.equals(s))
                    flag = 1;
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

    public boolean usedAlias(String alias)  throws SQLException
    {
        DbAdapter adapter = new DbAdapter();
        int flag=0;
        adapter.connect();

        String sql = "Select * from person where alias = '" + alias + "';" ;
        //System.out.println(sql);
        Statement stmt = adapter.conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        try {
            while(rs.next())
            {
                String s=rs.getString("alias");

                System.out.println(s);

                if(alias.equals(s))
                    flag = 1;
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

    public boolean invalidPassword(String pass)
    {
        //System.out.println("3");
        //System.out.println(pass);
        int passLength= pass.length();

        if(passLength<8 || passLength>16)
            return true;
        else
            return false;
    }





}

