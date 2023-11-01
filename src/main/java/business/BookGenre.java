package business;

import java.awt.print.Book;
import java.util.Objects;

public class BookGenre{

    private int bookID;
    private Genre genreID;

    public BookGenre(int bookID, Genre genreID) {
        this.bookID = bookID;
        this.genreID = genreID;
    }

    public BookGenre() {
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public Genre getGenreID() {
        return genreID;
    }

    public void setGenreID(Genre genreID) {
        this.genreID = genreID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGenre)) return false;
        BookGenre bookGenre = (BookGenre) o;
        return Objects.equals(getBookID(), bookGenre.getBookID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookID(), getGenreID());
    }

    @Override
    public String toString() {
        return "BookGenre{" +
                "bookId=" + bookID +
                ", genreID=" + genreID +
                '}';
    }
}