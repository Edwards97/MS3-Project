import java.sql.*;

public class Connector2 {

   public static Connection connect(String fileName){
    Connection conn = null;
    try {

        String url = "jdbc:sqlite:"+fileName+".sqlite";

        // create a connection to the .db file
        conn = DriverManager.getConnection(url);
        System.out.println("Connecting to Database");

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return conn;
   }
}
