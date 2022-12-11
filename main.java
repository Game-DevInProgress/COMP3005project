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
    static Scanner in = new Scanner(System.in);
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
                getBooks();
                break;
            case 2:
                
                break;
            case 3:
                searchBook();
                break;
            default:
                System.out.println("Unknown command. Please try again");
        }
    }

    public static void getBooks() {
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
                int quantity = result.getInt("quantity");

                System.out.println(title + ", " + ISBN + ", " + prices + ", " + "Cost of two books: " + prices * 2 + ", " + pages + ", " + genre + ", " + author + ", " + quantity + "\n");
                
            }

            connection.close();
    
        } catch (SQLException e) {
            System.out.println("Error connecting");
            e.printStackTrace();
        }
    }

    public static void searchBook(){
        try{
            System.out.println("How would you like to search:");
            System.out.println("1.\tTitle");
            System.out.println("2.\tISBN");
            System.out.println("3.\tAuthor");
            System.out.println("4.\tGenre");
            System.out.println("5.\t");
            System.out.print("Choice: ");
            int choice = in.nextInt();

            switch(choice){
                
                case 1:
                    System.out.println("What is the title: ");
                    String t = in.next();
                    
                    try{
                        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                        String sql = "Select * from Books where title='";
                        sql+= t + "'";
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
                            int quantity = result.getInt("quantity");
            
                            System.out.println(title + ", " + ISBN + ", " + prices + ", " + "Cost of two books: " + prices * 2 + ", " + pages + ", " + genre + ", " + author + ", " + quantity + "\n");
                            
                        }
            
                        connection.close();
                
                    } catch (SQLException e) {
                        System.out.println("Error connecting");
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Unknown choice. Please try again");
            }
        
        }catch(Exception e){
            System.out.println("Unknown command. Please try again");
        }
    }
}


