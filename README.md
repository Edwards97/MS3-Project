# Summary  
This repo contains all essential files in order to use the MS3Project app created. The app serves the purpose of taking in a .csv file and transmuting it to sqlite database as well as verifying its contents meet certain criteria. If the entries fail these criteria, they are printed to a different .csv file. After completing this database shift, a log is created to show the total entries received, entries that succefully passed and entries that didn't.
# Running Application  
I can't account for each individual machine because of certain pathing variables, differences in Operating Systems and whether the proper stuff is installed for sqlite, java and github. However, the process I ran this project was pretty straight forward otherwise. In the containing directory, I executed the lines:  
workspace> javac .\MS3Project.java  
workspace> java MS3Project  
This would open up the configured JFrame and allow the user to input a file name. (Database.csv) This file must be in the same working directory before attempting the input. Once the input is submitted the application begins to churn the result to a sqlite database and requires approximately 15 seconds (on my system) to process the given 6k entries. The resulting database, log and bad.csv file are then available in the working directory.  
### Note  
While the application is able to be re-run, I made it so the log file would not be written to multiple times... the sqlite database and bad.csv file will be appended on with duplicate answers.
# Approach/Assumptions  
Nothing too crazy was going to be put into the JFrame input.  
The test file is reasonable for the bounds of SQLite and for the machine running it to process in a timely manner.  
Users will have all other downloads, paths, and knowledge prior to running this application.

# MS3-Project Timeline
### Day 1  
Mostly worked on setting up SQLite environment (watching videos and downloading). Set up first stage of java file to accept .csv file and print contents.  
I've worked around SQL but never with it directly. It's been challenging to learn something entirely new but I figure any exposure is something I can build from to grow.  
### Day 2  
Concentrated on bringing up the JDBC for java and setting up a connection with SQLite. (Got it working too, yay!) Got some given sample code to run and return properly (https://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm) Took too long to fix a simple classpath mistake.  
Altered sample code to also create database.
### Day 3  
Placed jdbc connector in another class to reduce clutter. Made so running also populates databases with table "Columns" listing A through J. Conducted some research about JFrame as it seems to be the common graphical aid to Java applications (https://www.java-made-easy.com/java-jframe.html) and JFrame w/ buttons (https://javatutorial.net/jframe-buttons-listeners-text-fields). Began process of parsing the csv files contents and transposing to a SQLite database.  
### Day 4  
Began functionality and completed set up so application would create a "bad.csv" as well as a "log.txt" to hold failed inputs and the statistics of the input csv file. (Using https://www.tutorialspoint.com/javaexamples/file_write.htm for file writing resource)  
### Day 5  
Put finishing touches on project (Making sure log doesn't double write and that all files are named properly) and pushed final submission to Github.
