package business;

import java.awt.print.Book;
import java.util.Objects;

public class BookGenre{

    private Book bookId;
    private Genre genreID;

    public BookGenre(Book bookId, Genre genreID) {
        this.bookId = bookId;
        this.genreID = genreID;
    }

    public BookGenre() {
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
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
        return Objects.equals(getBookId(), bookGenre.getBookId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getGenreID());
    }

    @Override
    public String toString() {
        return "BookGenre{" +
                "bookId=" + bookId +
                ", genreID=" + genreID +
                '}';
    }
}