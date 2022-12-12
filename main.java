//imports
import java.util.Scanner;  // Import the Scanner class
import java.util.ArrayList;
import java.util.LinkedList;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main{
    static int orderNumber = 0;//gets incremented each time a order is placed
    static LinkedList<Book> cart = new LinkedList<>();
    static boolean loggedIn = false;
    
    
    
    static String jdbcURL = "jdbc:postgresql://localhost:5432/3005project";//replace 3005project with the name of your database
    static String username = "postgres";
    static String password = "password"; //replace "password" with your own master password from pgAdmin4.
    static Scanner in = new Scanner(System.in);
    static String user = "Guest";
    static char mode = 'g';


    public static void main(String[] args) {
        System.out.println("Welcome To LookInnaBook");
        System.out.println();

        displayMenu();
        handleMenuInput();
        
    }

    //function to get a list of all the books from the Books table in the database
    public static void getBooks() {
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String sql = "Select * from Books";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            parseResult(result);//helper function to parse result data

            connection.close();
    
        } catch (SQLException e) {
            System.out.println("Error connecting");
            e.printStackTrace();
        }
    }

    //Function to search for a book based on a specified parameter
    public static void searchBook(){
        try{
            System.out.println("How would you like to search:");
            System.out.println("1.\tTitle");
            System.out.println("2.\tISBN");
            System.out.println("3.\tAuthor");
            System.out.println("4.\tGenre");
            System.out.print("Choice: ");
            int choice = in.nextInt();

            switch(choice){
                
                case 1://search by title
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
                case 2://search by ISBN
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
                case 3://search by author
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
                case 4://search by genre
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

    //helper function to parse the resultset returned from sql database
    public static void parseResult(ResultSet result){
        ArrayList<Book> books = new ArrayList<>();//all books returned by query
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
            if(mode != 'o'){//the current user is not owner
                if(i==0){
                    System.out.println("NO BOOKS WERE FOUND MATCHING THE QUERY");
                }else if(i ==1){
                    System.out.print("Would you like to add this book to your cart (y or n): ");
                    char add = in.next().charAt(0);
                    if(add == 'y'){
                        Boolean valid = false;
                        while(!valid){
                            System.out.print("How many books do you want: ");
                            int quant = in.nextInt();
                            if(quant > books.get(0).quantity || quant < 0){
                                System.out.println("Values is out of bounds");
                            }
                            else{
                                valid = true;
                                if(cart.contains(books.get(0))){
                                    books.get(0).quantity += quant;
                                }
                                else{
                                    books.get(0).quantity = quant;
                                    cart.add(books.get(0));
                                }                        
                            }
                        }
                        
                    }
                }else{
                    System.out.print("Would you like to add a book from the list into your cart (y or n): ");
                    char add = in.next().charAt(0);
                    if(add == 'y'){
                        System.out.println("Which book number would you like to add (Result #): ");
                        int index = in.nextInt();

                        Boolean valid = false;
                        while(!valid){
                            System.out.print("How many books do you want: ");
                            int quant = in.nextInt();
                            if(quant > books.get(index - 1).quantity || quant < 0){
                                System.out.println("Values is out of bounds");
                            }
                            else{
                                valid = true;
                                if(cart.contains(books.get(index - 1))){
                                    books.get(index - 1).quantity += quant;
                                }
                                else{
                                    books.get(index - 1).quantity = quant;
                                    cart.add(books.get(index-1));
                                }                        
                            }
                        }
                    }
                }
            }
            
        }catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    //helper function to diplay the home menu
    public static void displayMenu(){
        if(mode == 'o'){//display owner menu
            System.out.println("logged in as:\t" + user);
            System.out.println();
            System.out.println("What would you like to do:");
            System.out.println("1. View Report (Did not finish)");
            System.out.println("2. Add Books");
            System.out.println("3. Remove Books");
            System.out.println("4. Log Out");
            System.out.println("5. Leave Store\n");
        }
        else{//user menu
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
    }

    //helper function to nicely display the books
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

    //helper function to handle the input from the main menu
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
                    addBooks();
                    getBooks();
                    break;
                case 3:
                    removeBooks();
                    getBooks();
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
                    orderMoreBooks();
                    break;
                case 2:
                    printCart();
                    if(cart.size() == 0){
                        System.out.println("Your cart is empty");
                    }
                    else{
                        if(loggedIn){
                            System.out.println("Would you like to checkout (y or n) (Not fully implemented)");
                            char flag = in.next().charAt(0);
                            if(flag == 'y' && mode == 'u'){
                                checkout();
                            }
                        }else{
                            System.out.println("You need to login before checking out");
                        }
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
        System.out.print("Card#: ");
        int card = in.nextInt();
        System.out.print("Shipping Address: ");
        in.next();
        String saddress = in.nextLine();
        //temp to clear cart
        while(!cart.isEmpty()){
            cart.pop();
        }
    }
////////END CART FUNCTIONS/////////////////

//tests if user is in the database. If so then log them in
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

    //function for manager to be able to add books to the Books table
    public static void addBooks(){
        System.out.print("Title: ");
        in.nextLine();
        String title = in.nextLine();
        System.out.print("ISBN: ");
        int isbn = in.nextInt();
        System.out.print("Pages: ");
        int pages = in.nextInt();
        System.out.print("Price: ");
        int price = in.nextInt();
        System.out.print("Genre: ");
        in.nextLine();
        String genre = in.nextLine();
        System.out.print("Author Name: ");
        String aName = in.nextLine();
        System.out.print("Quantity: ");
        int quantity = in.nextInt();

        String sql = "INSERT INTO Books(title, ISBN, pages, price, genre, author, quantity) " + "Values(?, ?, ?, ?, ?, ?, ?)";

        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            
            PreparedStatement pst = connection.prepareStatement(sql);
            
            pst.setString(1, title);
            pst.setInt(2, isbn);
            pst.setInt(3, pages);
            pst.setInt(4, price);
            pst.setString(5, genre);
            pst.setString(6, aName);
            pst.setInt(7, quantity);
            pst.executeUpdate();
            connection.close();
    
        } catch (SQLException e) {
            System.out.println("Error connecting");
            e.printStackTrace();
        }
    }

    //function to remove a book from the Books table
    public static void removeBooks(){
        System.out.println("What is the ISBN of the book you would like to remove: ");
        in.nextLine();
        int isbn = in.nextInt();
        String sql = "DELETE FROM Books where isbn = ? ";
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            PreparedStatement pst = connection.prepareStatement(sql);
            
            pst.setInt(1, isbn);
            pst.executeUpdate();
            
            

            connection.close();
    
        } catch (SQLException e) {
            System.out.println("Error connecting");
            e.printStackTrace();
        }
    }

    //Function to order more books when quaantity is low
    public static void orderMoreBooks(){
        int addQuantity = 6;
        int minQuantity = 0;
        String sql = "Update Books " + "SET quantity = ? " + "WHERE quantity <= ?";
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            PreparedStatement pst = connection.prepareStatement(sql);
            
            pst.setInt(1, addQuantity);
            pst.setInt(2, minQuantity);
            pst.executeUpdate();

            getBooks();
        } catch (SQLException e) {
            System.out.println("Error connecting");
            e.printStackTrace();
        }
    }  
}
