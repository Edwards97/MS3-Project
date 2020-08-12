import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import java.util.*;
import java.sql.*;

class QuickStart {

    private static final String COMMA_DELIMITER = ",";
    public static void main(String[] args) {
        System.out.println("Hello, World.");

        Scanner scanner = null;
        try{
            File myObj = new File("Database.csv");
            scanner = new Scanner(myObj);
            scanner.useDelimiter(COMMA_DELIMITER);

            if (myObj.exists()) {
                while(scanner.hasNext())
                {
                        System.out.print(scanner.next()+"   ");

                        //parse and insert into SQLite 
                }
            }
        }
    
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }

        finally
        {
            scanner.close();
        }
    }
}