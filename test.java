import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test {
    public static void main(String args[]) {
        String jdbcURL = "jdbc:postgresql://localhost:5432/3005project";
        String username = "postgres";
        String password = "nick99285"; //replace "password" with your own master password.
        
        try{
    
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Succesfully connected to postgresql server \n");
            System.out.println("Book title, ISBN, Number of pages, Genre, Book Author \n");
            
            String sql = "Select * from Books";
            
            Statement statement = connection.createStatement();
            
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()){
                String title = result.getString("title");
                int ISBN = result.getInt("ISBN");
                int pages = result.getInt("pages");
                String genre = result.getString("genre");
                String author = result.getString("author");

                System.out.println(title + ", " + ISBN + ", " + pages + ", " + genre + ", " + author + "\n");
                
            }

            connection.close();
    
        } catch (SQLException e) {
            System.out.println("Error connecting");
            e.printStackTrace();
        }
           
    }
}
