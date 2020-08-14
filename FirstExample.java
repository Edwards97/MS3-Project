import java.io.*;
import java.util.Scanner;
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FirstExample {

   public static void main(String[] args) {
      Statement stmt = null;
      String sql;
     

      //Database creation/connection
      Connection conn = Connector2.connect();
      try{
         sql = "CREATE TABLE Columns"
         + "( A string, B string, C string, D string, E string, F string, G string, H string, I string, J string);";
         stmt = conn.createStatement();
         stmt.execute(sql);
      }catch (SQLException e){
         e.printStackTrace();
      }


      //Todo analyze JFrame to determine better solutions
      JFrame frame = new JFrame();
      JPanel topPanel = new JPanel();
      JPanel centerPanel = new JPanel();
      JLabel topLabel = new JLabel("Enter file name");
      JTextField file = new JTextField(15);
      JButton button = new JButton("Run");
 
      topPanel.add(topLabel);
      topPanel.add(file);
      centerPanel.add(button);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600, 200); 
      frame.setTitle("MS3 Project");
      frame.getContentPane().add(BorderLayout.NORTH, topPanel);
      frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
      frame.setVisible(true);

      button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent action){
            String newsql;
            int received = 0;
            Scanner scanner2 = null;

            String fileName = file.getText();
            String filelocation = new File(fileName).getAbsolutePath();
            File toScanner = new File(filelocation);
         //Reading in CSV file
         try{
            scanner2 = new Scanner(new File(toScanner, "UTF-8"));
            scanner2.useDelimiter(","); //comma separated file
            
            received++;
               
            newsql = "INSERT INTO Columns" 
               + "(A, B, C, D, E, F, G, H, I, J) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insert = conn.prepareStatement(newsql);

         
         }
         catch(FileNotFoundException e1){
            e1.printStackTrace();
         }
         catch(SQLException e){
            e.printStackTrace();
         }
         finally
         {
            scanner2.close();

         }
         
         System.out.println("");
         System.out.println(received);


      }
      });
      
     

      
      
 }//end main
 }//end FirstExample