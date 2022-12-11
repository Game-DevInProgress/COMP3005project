//import the sql here
import java.util.Scanner;  // Import the Scanner class
import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQLJDBC {
    public static void main(String args[]) {
       String jdbcURL = "jdbc:postgresql://localhost:5432/3005project";
       String username = "postgresql";
       String password = "password";
       try{

        Connection connection = DriverManager.getConnection(jdbcurl, username, password);
        System.out.println("Succesfully connected to postgresql server");
        connection.close();

       } catch (SQLExeption e) {
        System.out.println("Error connecting");
        e.printStackTrace();
       }
       
    }
}

public class main{
    public static void main(String[] args) {
        User user = new User();
        BookStore store = new BookStore();
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
                
                break
            default:
                System.out.println("Unknown command. Please try again");
        }
    }
}


