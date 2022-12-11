public class Book {
    String title;
    int ISBN;
    int pages;
    float price;
    String genre;
    String author;
    int quantity;

    public Book(String t, int code, int numPage, float cost, String g, String auth){
        this.title = t;
        this.ISBN = code;
        this.pages = numPage;
        this.price = cost;
        this.genre = g;
        this.author = auth;
        this.quantity = 10;
    }

}


