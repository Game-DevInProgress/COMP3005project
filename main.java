//import the sql here
import java.util.Scanner;  // Import the Scanner class
import java.util.ArrayList;
import java.util.LinkedList;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main{
    static int orderNumber = 0;
    static LinkedList<Book> cart = new LinkedList<>();
    static boolean loggedIn = false;
    
    static String jdbcURL = "jdbc:postgresql://localhost:5432/BookStore";
    static String username = "postgres";
    static String password = "minecraft1221"; //replace "password" with your own master password.
    static Scanner in = new Scanner(System.in);
    static String user = "Guest";
    static char mode = 'g';


    public static void main(String[] args) {
        System.out.println("Welcome To LookInnaBook");
        System.out.println();

        displayMenu();
        handleMenuInput();
        
    }

    public static void getBooks() {
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String sql = "Select * from Books";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            parseResult(result);

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
                
                case 1://START CASE 1
                    System.out.println("What is the title: ");
                    in.nextLine();
                    String t = in.nextLine();

                    try{
                        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                        String sql = "Select * from Books where title='";
                        sql+= t + "'";
                        Statement statement = connection.createStatement();
                        ResultSet result = statement.executeQuery(sql);
            
                        parseResult(result);
            
                        connection.close();
                
                    } catch (SQLException e) {
                        System.out.println("Error connecting");
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("What is the ISBN: ");
                    int code = in.nextInt();

                    try{
                        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                        String sql = "Select * from Books where isbn='";
                        sql+= code + "'";
                        Statement statement = connection.createStatement();
                        ResultSet result = statement.executeQuery(sql);
            
                        parseResult(result);
            
                        connection.close();
                
                    } catch (SQLException e) {
                        System.out.println("Error connecting");
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("What is the Author: ");
                    in.nextLine();
                    String a = in.nextLine();

                    try{
                        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                        String sql = "Select * from Books where author='";
                        sql+= a + "'";
                        Statement statement = connection.createStatement();
                        ResultSet result = statement.executeQuery(sql);
                        
                        parseResult(result);
            
                        connection.close();
                
                    } catch (SQLException e) {
                        System.out.println("Error connecting");
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println("What is the Genre: ");
                    in.nextLine();
                    String g = in.nextLine();

                    try{
                        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                        String sql = "Select * from Books where genre='";
                        sql+= g + "'";
                        Statement statement = connection.createStatement();
                        ResultSet result = statement.executeQuery(sql);
            
                        parseResult(result);
            
                        connection.close();
                
                    } catch (SQLException e) {
                        System.out.println("Error connecting");
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Unknown choice. Please try again");
            }
        
        }catch(Exception e){
            System.out.println("Unknown command. Please try again");
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////HELPER FUNCTIONS////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void parseResult(ResultSet result){
        ArrayList<Book> books = new ArrayList<>();
        int i = 0;
        try{
            while(result.next()){
                i++;
                String title = result.getString("title");
                int ISBN = result.getInt("ISBN");
                float price = result.getFloat("price");
                int pages = result.getInt("pages");
                String genre = result.getString("genre");
                String author = result.getString("author");
                int quantity = result.getInt("quantity");
                Book b = new Book(title, ISBN, pages, price, genre, author);
                b.quantity = quantity;
                System.out.println("RESULT #" + i);
                displayBook(b);
                books.add(b);
                
            }

            if(i==0){
                System.out.println("NO BOOKS WERE FOUND MATCHING THE QUERY");
            }else if(i ==1){
                System.out.print("Would you like to add this book to your cart (y or n): ");
                char add = in.next().charAt(0);
                if(add == 'y'){
                    cart.add(books.get(i));
                }
            }else{
                System.out.print("Would you like to add a book from the list into your cart (y or n): ");
                char add = in.next().charAt(0);
                if(add == 'y'){
                    System.out.println("Which book number would you like to add (Result #): ");
                    int index = in.nextInt();
                    cart.add(books.get(index-1));
                }
            }
        }catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static void displayMenu(){
        System.out.println("logged in as:\t" + user);
        System.out.println();

        System.out.println("What would you like to do:");
        System.out.println("1. View books");
        System.out.println("2. View cart");
        System.out.println("3. Search for Book");
        if(!loggedIn){
            System.out.println("4. Sign in");
        } 
        else{
            System.out.println("4. Log Out");
        }
        System.out.println("5. Leave Store\n");
        
    }

    public static void displayBook(String t, int code, int numPage, float cost, String g, String auth, int quantity){
        System.out.println("==========================================================================");
        System.out.println("Title:\t\t" + t);
        System.out.println("ISBN:\t\t" + code);
        System.out.println("# of pages:\t" + numPage);
        System.out.println("Cost:\t\t" + cost);
        System.out.println("Genre:\t\t" + g);
        System.out.println("Author:\t\t" + auth);
        System.out.println("Quantity:\t" + quantity);
        System.out.println("==========================================================================");
        System.out.println();
    }

    public static void displayBook(Book b){
        System.out.println("==========================================================================");
        System.out.println("Title:\t\t" + b.title);
        System.out.println("ISBN:\t\t" + b.ISBN);
        System.out.println("# of pages:\t" + b.pages);
        System.out.println("Cost:\t\t" + b.price);
        System.out.println("Genre:\t\t" + b.genre);
        System.out.println("Author:\t\t" + b.author);
        System.out.println("Quantity:\t" + b.quantity);
        System.out.println("==========================================================================");
        System.out.println();
    }

    
    public static void handleMenuInput(){
        int c;
        if(mode == 'o'){
            while(true){
                try{
                    System.out.print("Choice: ");
                    c = in.nextInt();
        
                    if(c < 1 || c > 5){
                        throw new ArithmeticException("Entry out of bounds. Please try again.");
                    }
                    else{
                        break;
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
            
            
            switch(c){
                case 1:
                    getBooks();
                    break;
                case 2:
                    
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    if(loggedIn){
                        user = "Guest";
                        mode = 'g';
                        loggedIn = false;
                        System.out.println("Successfully logged out");
                    }
                    else{
                        logIn();
                    }
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unknown command. Please try again");
            }
        }else{ //// user or guest
            while(true){
                try{
                    System.out.print("Choice: ");
                    c = in.nextInt();
        
                    if(c < 1 || c > 5){
                        throw new ArithmeticException("Entry out of bounds. Please try again.");
                    }
                    else{
                        break;
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
            
            
            switch(c){
                case 1:
                    getBooks();
                    break;
                case 2:
                    printCart();
                    if(loggedIn){
                        System.out.println("Would you like to checkout (y or n)");
                        char flag = in.next().charAt(0);
                        if(flag == 'y' && mode == 'u'){
                            checkout();
                        }
                    }else{
                        System.out.println("You need to login before checking out");
                    }
                    
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    if(loggedIn){
                        user = "Guest";
                        mode = 'g';
                        loggedIn = false;
                        System.out.println("Successfully logged out");
                    }
                    else{
                        logIn();
                    }
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unknown command. Please try again");
            }
        }
        //System.out.println();
        displayMenu();
        handleMenuInput();
    }

/////////CART FUNCTIONS//////////

    public static void printCart(){
        for(int i = 0; i < cart.size();i++){
            displayBook(cart.get(i));
        }
    }

    public static void checkout(){
        System.out.println("Please enter billing info");
        System.out.println("Card#: ");
        int card = in.nextInt();
        System.out.println("Shipping Address: ");
        in.next();
        String saddress = in.nextLine();
        //temp to clear cart
        while(!cart.isEmpty()){
            cart.pop();
        }
    }
////////END CART FUNCTIONS/////////////////
    public static void logIn(){
        
        boolean gotUser = false;
        while(!gotUser){
            System.out.println();
            System.out.print("USERNAME: ");
            String name = in.next();
            System.out.print("Password: ");
            String pass = in.next();
            try{
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                String sql = "Select username,accounttype from Users where username='"+ name + "' and password='" + pass + "'";
                System.out.println();
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                if(!result.next()){
                    System.out.println("No User Found");
                    System.out.println("Do you want to cancel (y or n): ");
                    char abort = in.next().charAt(0);

                    if(abort == 'y'){
                        System.out.println("Failed to sign in... returning to main menu as guest");
                        break;
                    }
                }
                else{
                    while(result.next()){
                        user = result.getString("username");
                        mode = result.getString("accounttype").charAt(0);
                        
                    }
                    System.out.println("Successfully Logged in as " + user);
                    loggedIn = true;
                    gotUser = true;
                }
    
                connection.close();
        
            } catch (SQLException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

}

///WORK IN PROGRESS FOR PRINTING SERACHED BOOKS////
// ResultSet main = result;
// try{
//     if(!main.next()){
//         System.out.println("NO BOOKS WERE FOUND MATCHING YOUR QUERY");
//     }else{
//         System.out.println();
//         System.out.println("BOOKS MATCHING YOUR SEARCH: ");

//         while(result.next()){
//             String title = result.getString("title");
//             int ISBN = result.getInt("ISBN");
//             float price = result.getFloat("price");
//             int pages = result.getInt("pages");
//             String genre = result.getString("genre");
//             String author = result.getString("author");
//             int quantity = result.getInt("quantity");

//             displayBook(title, ISBN, pages, price, genre, author, quantity);
            
//         }
//     }
    
// }catch(SQLException e){
//     System.out.println(e);
//     e.printStackTrace();
// }