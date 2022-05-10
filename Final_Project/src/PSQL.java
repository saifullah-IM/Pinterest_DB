import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PSQL {
    public static ResultSet insert(String queryLine)
    {

        Statement stmt = null;
        Connection c = null;
        try {
            //System.out.println(queryLine);
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/"+"Pinterest_Project",
                            "postgres", "123");

            //System.out.println("hi");


            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");
            stmt = c.createStatement();

            //System.out.println(queryLine);

            ResultSet rs = null;
            stmt.executeUpdate( queryLine );
            //stmt.executeUpdate(queryLine);
            c.commit();

            c.close();

            return rs;
            /*
            while ( rs.next() ) {
                String name = rs.getString("name");
                System.out.print(name);
                String location = rs.getString("location_of_hq");
                System.out.println(location);
            }
            */
            //rs.close();



        } catch (Exception e) {
            return null;
        }
    }



    public static ResultSet search(String queryLine)
    {
        Statement stmt = null;
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/"+"Pinterest_Project",
                            "postgres", "123");


            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();

            System.out.println(queryLine);

            ResultSet rs = null;
            rs = stmt.executeQuery( queryLine );
            //stmt.executeUpdate(queryLine);
            c.commit();

            return rs;
            /*
            while ( rs.next() ) {
                String name = rs.getString("name");
                System.out.print(name);
                String location = rs.getString("location_of_hq");
                System.out.println(location);
            }
            */
            //rs.close();



        } catch (Exception e) {
            return null;
        }
    }
    }

