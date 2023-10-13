package business;

import java.util.Objects;

public class UserBook{
    private User userID;
    private Book bookID
    private LocalDateTime borrowDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnedDate;

    public UserBook(User userID, Book bookID, LocalDateTime borrowDate, LocalDateTime dueDate, LocalDateTime returnedDate) {
        this.userID = userID;
        this.bookID = bookID;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    public UserBook() {
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Book getBookID() {
        return bookID;
    }

    public void setBookID(Book bookID) {
        this.bookID = bookID;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserBook)) return false;
        UserBook userBook = (UserBook) o;
        return Objects.equals(getUserID(), userBook.getUserID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, bookID, borrowDate, dueDate, returnedDate);
    }

    @Override
    public String toString() {
        return "UserBook{" +
                "userID=" + userID +
                ", bookID=" + bookID +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnedDate=" + returnedDate +
                '}';
    }
}