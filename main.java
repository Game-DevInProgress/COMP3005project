//import the sql here
import java.util.Scanner;  // Import the Scanner class
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main{
    static String jdbcURL = "jdbc:postgresql://localhost:5432/BookStore";
    static String username = "postgres";
    static String password = "minecraft1221"; //replace "password" with your own master password.
    public static void main(String[] args) {
        User user = new User();
        System.out.println("Welcome To LookInnaBook");
        System.out.println("logged in as:\t" + user.name);
        try{
            displayMenu();
            Scanner in = new Scanner(System.in);
            int choice = in.nextInt();
            handleInput(choice);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }

    public static void displayMenu(){
        System.out.println("What would you like to do:");
        System.out.println("1. View books");
        System.out.println("2. View cart");
        System.out.println("3. Search for Book");
    }
    public static void handleInput(int c){
        switch(c){
            case 1:
            
            try{
        
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                String sql = "Select * from Books";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                System.out.println("Book title, ISBN, Number of pages, Genre, Book Author \n");

                while(result.next()){
                    String title = result.getString("title");
                    int ISBN = result.getInt("ISBN");
                    float prices = result.getFloat("price");
                    int pages = result.getInt("pages");
                    String genre = result.getString("genre");
                    String author = result.getString("author");
    
                    System.out.println(title + ", " + ISBN + ", " + prices + ", " + "Cost of two books: " + prices * 2 + ", " + pages + ", " + genre + ", " + author + "\n");
                    
                }
    
                connection.close();
        
            } catch (SQLException e) {
                System.out.println("Error connecting");
                e.printStackTrace();
            }
                break;
            default:
                System.out.println("Unknown command. Please try again");
        }
    }
}


