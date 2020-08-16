import java.io.*;
import java.util.Scanner;
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MS3Project {

   public static void main(String[] args) {
     
      //Create JFrame items for app
      JFrame frame = new JFrame();
      JPanel topPanel = new JPanel();
      JPanel centerPanel = new JPanel();
      JLabel topLabel = new JLabel("Enter file name");
      JTextField file = new JTextField(15);
      JButton button = new JButton("Run");
 
      topPanel.add(topLabel);
      topPanel.add(file);
      centerPanel.add(button);

      //Setting window size, Title and visibility
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600, 200); 
      frame.setTitle("MS3 Project");
      frame.getContentPane().add(BorderLayout.NORTH, topPanel);
      frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
      frame.setVisible(true);

      //All functionality runs on hitting the button
      button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent action){
            //Variable declarations
            String newsql, sql;
            int received = 0, failed = 0, successful = 0, batchCounter = 0;
            Scanner scanner2 = null;
            Statement stmt = null;
            
            //Used to name the new database, log and bad csv file
            String fileName = file.getText();

            //Database creation/connection
            Connection conn = Connector2.connect(fileName);
            try{ //create table for associated columns
               sql = "CREATE TABLE Columns"
               + "( A string, B string, C string, D string, E string, F string, G string, H string, I string, J string);";
               stmt = conn.createStatement();
               stmt.execute(sql);
            }catch (SQLException e){
               e.printStackTrace();
            }
            
            //To properly pass to scanner getting files location
            String filelocation = new File(fileName).getAbsolutePath();
            File toScanner = new File(filelocation);
            String[] column = new String[10];

         //Reading in CSV file
         try{// encoding with UTF-8 seems to help the process speed
            scanner2 = new Scanner(toScanner, "UTF-8");
            scanner2.useDelimiter(","); //comma separated file
            
            //Preparing a statement to insert actual values into the columns table
            newsql = "INSERT INTO Columns" 
               + "(A, B, C, D, E, F, G, H, I, J) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insert = conn.prepareStatement(newsql);
            StringBuilder data = new StringBuilder();

            boolean badResult = false, firstQuote = true, quoteInColumn = false, greater1k = false;
            //line by line parsing
            while(scanner2.hasNextLine()){
               received++;
               String currentLine = scanner2.nextLine();  
               int columnCounter = 1; //computer counting vs. people counting 

               // iterate through each character in the current line
               for(int i = 0; i < currentLine.length(); i++){
                  char c = currentLine.charAt(i);
                  data.append(c);

                  //Don't check anything outside the 10 mark... only 10 columns
                  if(columnCounter > 10){
                     break;
                  }

                  //I watched some YouTube video about parsing data and it recommended checking for quotes while parsing
                  if(c == ',' && !quoteInColumn){
                     if(data.toString().contentEquals(",")){
                        badResult = true;
                        break; 
                     }
                    
                   //array referenced -1 to correct that computer vs. people issue
                    column[columnCounter - 1] = data.substring(0, data.length()-1); //setting array in columnCounter to values                   
                    data = new StringBuilder();
                    columnCounter++;
                    
                  }
                  //setting array in columnCounter to values
                  if (i == currentLine.length()) {
                     column[columnCounter - 1] = data.substring(0, data.length()-1);
                  }

                  //Part of the checking for quotes tutorial
                  if(c == '"'){
                     if(firstQuote){
                        data = new StringBuilder();
                        firstQuote = false;
                     }
                     else{
                        data.deleteCharAt(data.length()-1);
                     }
                     
                     quoteInColumn = !quoteInColumn;
                  }


               }// for ends here

               //last check for out of bounds scope
               if(columnCounter < 10){
                  badResult = true;
               }
               //if something tripped badResult, they didn't match criteria to place in database
               if(badResult == true){
                  failed++;
                 
                  //writing or appending file with results that didn't meat cut
                  File statistics = new File(fileName + "-bad.csv");
                  try{
                     FileWriter fw = new FileWriter(statistics.getAbsoluteFile(), true);
                     BufferedWriter bw = new BufferedWriter(fw); 
                     bw.append(currentLine + "\n");
                     bw.close();
      
                  }catch (IOException e) {
                     e.printStackTrace();
                  }             

               }

               //actual insertion of values into database
               else{
                  insert.setString(1, column[0]);
                  insert.setString(2, column[1]);
                  insert.setString(3, column[2]);
                  insert.setString(4, column[3]);
                  insert.setString(5, column[4]);
                  insert.setString(6, column[5]);
                  insert.setString(7, column[6]);
                  insert.setString(8, column[7]);
                  insert.setString(9, column[8]);
                  insert.setString(10, column[9]);
                  insert.addBatch();

                  //This is the part that takes time...
                  //Every 1000 entries it submits the new entries to  the database,
                  //On my maching this takes approximately 5 seconds every 1000
                  if(batchCounter > 1000) {
                     if(greater1k == false){
                        System.out.println("Processing 1000 entries... One moment");
                        greater1k = true;
                     } 
                     else{
                        //Some flavor text
                        System.out.println("Processing another 1000 entries... One moment");
                     }
                     insert.executeBatch();
                     batchCounter = 0;
                  }
                 successful++;
                 batchCounter++;
               }


               badResult = false;
               columnCounter = 1;
               data = new StringBuilder();
            }//while ends here

            insert.executeBatch();
            boolean exists = false;

            //Creating log file after sqlite.db populated
            File log = new File(fileName + "-.log");
            try {
               //if file already exist... don't append
               if(log.createNewFile()){
                  System.out.println("File created: " + log.getName());
               }
               else{
                  System.out.println("File already exists.");
                  exists = true;
               }

            } catch (IOException e){
               System.out.println("An error occurred.");
               e.printStackTrace();
            }
            
            //Log file prints total, succeful, and failed entry amounts
            if(exists == false){
               try (FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
                  BufferedWriter bw = new BufferedWriter(fw); ){
                  bw.append( "Total number = " + received + "\n" +
                              "Succefully stored = " + successful + "\n" +
                              "Failed entries = " + failed);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
            else{
               System.out.println("log file location taken from previous run");
            }
            System.out.println("finished");

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
 }//end MS3Project