package business;

import java.util.Objects;

public class Book{

    private int bookID;
    private String bookName;
    private String author;
    private String description;
    private int quantity;

    public Book() {
    }

    public Book(int bookID, String bookName, String author, String description, int quantity) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.description = description;
        this.quantity = quantity;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getBookID() == book.getBookID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookID(), getBookName(), getAuthor(), getDescription(), getQuantity());
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}