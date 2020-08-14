import java.sql.*;

public class Connector {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/";
 
    //  Database credentials
    static final String USER = "Steven";
    static final String PASS = "mypoohbear#1";

   public static Connection connect(){
        Connection conn = null;
        Statement stmt = null;
        try{
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // Execute a query to create database
            System.out.println("Creating Database...");
            stmt = conn.createStatement();
            String sql;
       
            sql = "CREATE DATABASE TEST"; 
            stmt.executeUpdate(sql);
            //create tables in database ie columns
            //sql = "CREATE TABLE COLUMNS";      
           
            //stmt.executeUpdate(sql);
            
      
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
            if(stmt!=null)
              stmt.close();
            }catch(SQLException se2){
            
            }// nothing we can do
            try{
                if(conn!=null)
                conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
     }//end try
     System.out.println("Goodbye!");
     return conn;
   }
}
