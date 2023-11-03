import business.Book;
import business.Genre;
import business.User;
import business.UserBook;

import dao.*;
import exceptions.DuplicateEmailException;
import exceptions.DuplicateUsernameException;
import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final UserDao userDao = new UserDao("library");
    private static final BookDao bookDao = new BookDao("library");
    private static final UserBookDao userBookDao = new UserBookDao("library");
    private static final GenreDao genreDao = new GenreDao("library");
    private static final BookGenreDao bookGenreDao = new BookGenreDao("library");
    private static final Scanner sc = new Scanner(System.in);
    private static boolean logged = false;
    private static int choice = -1;
    private static User user = null;

    public static void main(String[] args) {
        user = userSession();

        while(logged){
            assert user != null;
            if(user.getUserType() == -1){
                System.out.println("this user is disabled");
                System.out.println("requires admin to enable user");
                logged = false;
            }
            else{
                displayMenu(user.getUserType());

                if(user.getUserType() == 0){
                    userInterface();
                }
                else if(user.getUserType() == 1){
                    adminInterface();
                }
            }

        }

    }

    /**
     * displays the menu based on the usertype
     * @param userType the userType
     */
    public static void displayMenu(int userType){
        if(userType == 0){
            System.out.println("select an option: ");
            System.out.println("1. view details of all books in library");
            System.out.println("2. view loans");
            System.out.println("3. view history");
            System.out.println("4. borrow a book");
            System.out.println("5. return a book");
            System.out.println("6. view fees");
            System.out.println("7. pay the fee");
            System.out.println("8. profile");
            System.out.println("9. logout");
        }
        else{
            System.out.println("select an option: ");
            System.out.println("1. view details of all books in library");
            System.out.println("2. view loans");
            System.out.println("3. view history");
            System.out.println("4. borrow a book");
            System.out.println("5. return a book");
            System.out.println("6. view fees");
            System.out.println("7. pay the fee");
            System.out.println("8. profile");
            System.out.println("9. logout");
            System.out.println("10. add a book");
            System.out.println("11. update quantity in a book");
            System.out.println("12. disable member");
            System.out.println("13. enable member");
        }
    }

    /**
     * start the user session for logging or registering
     * @return the user
     */
    public static User userSession(){
        User user = null;
        String username;
        String email;
        String password;
        String address;
        String phone;

        while(!logged){
            System.out.println("select an option (use number): ");
            System.out.println("1. login");
            System.out.println("2. register");
            System.out.println("3. exit");

            choice = validInt();
            sc.nextLine();

            switch (choice){
                case 1:
                    System.out.println("enter email: ");
                    email = sc.nextLine();
                    System.out.println("enter password: ");
                    password = sc.nextLine();

                    try{
                        user = userDao.loginUser(email, password);
                    }
                    catch (InvalidEmailException e){
                        System.out.println(e.getMessage());
                        System.out.println("try a different email");
                    }
                    catch (InvalidPasswordException pe){
                        System.out.println(pe.getMessage());
                        System.out.println("try again");
                    }

                    break;

                case 2:
                    System.out.println("enter username: ");
                    username = sc.nextLine();
                    System.out.println("enter email: ");
                    email = sc.nextLine();
                    System.out.println("enter password: ");
                    password = sc.nextLine();
                    System.out.println("enter address: ");
                    address = sc.nextLine();
                    System.out.println("enter phone: ");
                    phone = sc.nextLine();

                    try{
                        user = userDao.registerUser(username, email, password,
                                address, phone);
                    }
                    catch (DuplicateUsernameException e){
                        System.out.println(e.getMessage());
                        System.out.println("try a different username");
                    }
                    catch (DuplicateEmailException e){
                        System.out.println(e.getMessage());
                        System.out.println("try a different email, or login instead");
                    }

                    break;

                case 3:
                    System.exit(0);
                    break;

                default:
                    System.out.println("invalid option");
                    break;
            }
            if(user != null){
                logged = true;
            }
        }
        return user;
    }

    /**
     * the user interface
     */
    public static void userInterface(){
        choice = validInt();
        sc.nextLine();
        int bookID;
        int success;

        switch (choice){
            case 1:
                List<Book> books = bookDao.getAllBooks();
                List<Genre> genres;
                for(Book b : books){
                    genres = bookGenreDao.getGenreByBookID(b.getBookID());
                    System.out.println("-----------------------");
                    System.out.println("bookID: " + b.getBookID());
                    System.out.println("book name: " + b.getBookName());
                    System.out.println("author: " + b.getAuthor());
                    System.out.println("genres: " + genres);
                    System.out.println("description: " + b.getDescription());
                    System.out.println("quantity: " + b.getQuantity());
                }
                break;

            case 2:
                List<UserBook> userBooks = userBookDao.getAllCurrentBooksByUserID(user.getUserID());
                if(userBooks.isEmpty()){
                    System.out.println("no loans available, borrow one!");
                }
                else{
                    for(UserBook uB : userBooks){
                        System.out.println("-----------------------------------------");
                        System.out.println("userID: " + uB.getUserID().getUserID());
                        System.out.println("bookID: " + uB.getBookID().getBookID());
                        System.out.println("book name: " + uB.getBookID().getBookName());
                        System.out.println("borrowDate: " + uB.getBorrowDate());
                        System.out.println("due date: " + uB.getDueDate());
                        System.out.println("returned date : " + uB.getReturnedDate());
                    }
                }
                break;

            case 3:
                List<UserBook> userBooks1 = userBookDao.getAllBooksByUserID(user.getUserID());
                if(userBooks1.isEmpty()){
                    System.out.println("no history available, borrow one!");
                }
                else{
                    for(UserBook uB : userBooks1){
                        System.out.println("-----------------------------------------");
                        System.out.println("userID: " + uB.getUserID().getUserID());
                        System.out.println("bookID: " + uB.getBookID().getBookID());
                        System.out.println("book name: " + uB.getBookID().getBookName());
                        System.out.println("borrowDate: " + uB.getBorrowDate());
                        System.out.println("due date: " + uB.getDueDate());
                        System.out.println("returned date : " + uB.getReturnedDate());
                    }
                }
                break;

            case 4:
                System.out.println("enter a bookID: ");
                bookID = validInt();
                sc.nextLine();

                if(bookID == -11){
                    success = 0;
                }
                else{
                    success = userBookDao.borrowBook(user.getUserID(), bookID);
                }

                if(success > 0){
                    System.out.println("book borrowed, please return iot within 2 weeks");
                }
                else{
                    System.out.println("the book does not exist, borrow cancelled");
                }
                break;

            case 5:
                System.out.println("enter a bookID: ");
                bookID = validInt();
                sc.nextLine();

                if(bookID == -11){
                    success = 0;
                }
                else{
                    success = userBookDao.returnBook(user.getUserID(), bookID);
                }

                if(success > 0){
                    System.out.println("book returned");
                    userBookDao.checkIfLate(user.getUserID(), bookID);
                }
                else{
                    System.out.println("no such borrow");
                }
                break;

            case 6:
                System.out.println("your fees: " + user.getFees());
                break;

            case 7:
                System.out.println("insert card number");
                String card = sc.nextLine();
                System.out.println("insert cvv");
                String cvv = sc.nextLine();

                if(card.length() == 16 && cvv.length() == 3){
                    System.out.println("fee: " + user.getFees());
                    System.out.println("enter amount to pay: ");
                    int pay = validInt();
                    System.out.println("payment fulfilled");
                    userDao.updateFee(user.getUserID(), pay);
                    user.setFees(user.getFees() - pay);
                }
                else{
                    System.out.println("invalid card details");
                }
                break;

            case 8:
                user.format();
                break;

            case 9:
                logged = false;
                break;

            default:
                System.out.println("invalid command");
                break;
        }
    }

    /**
     * the admin interface
     */
    public static void adminInterface(){
        int bookID;
        int success;
        String bookName;
        String author;
        String desc;
        int quantity;
        int res;
        int userID;
        List<Book> books;

        choice = validInt();

        switch(choice){
            case 1:
                books = bookDao.getAllBooks();
                List<Genre> genres;
                for(Book b : books){
                    genres = bookGenreDao.getGenreByBookID(b.getBookID());
                    System.out.println("-----------------------");
                    System.out.println("bookID: " + b.getBookID());
                    System.out.println("book name: " + b.getBookName());
                    System.out.println("author: " + b.getAuthor());
                    System.out.println("genres: " + genres);
                    System.out.println("description: " + b.getDescription());
                    System.out.println("quantity: " + b.getQuantity());
                }
                break;

            case 2:
                List<UserBook> userBooks = userBookDao.getAllCurrentBooksByUserID(user.getUserID());
                if(userBooks.isEmpty()){
                    System.out.println("no loans available, borrow one!");
                }
                else{
                    for(UserBook uB : userBooks){
                        System.out.println("-----------------------------------------");
                        System.out.println("userID: " + uB.getUserID().getUserID());
                        System.out.println("bookID: " + uB.getBookID().getBookID());
                        System.out.println("book name: " + uB.getBookID().getBookName());
                        System.out.println("borrowDate: " + uB.getBorrowDate());
                        System.out.println("due date: " + uB.getDueDate());
                        System.out.println("returned date : " + uB.getReturnedDate());
                    }
                }
                break;

            case 3:
                List<UserBook> userBooks1 = userBookDao.getAllBooksByUserID(user.getUserID());
                if(userBooks1.isEmpty()){
                    System.out.println("no history available, borrow one!");
                }
                else{
                    for(UserBook uB : userBooks1){
                        System.out.println("-----------------------------------------");
                        System.out.println("userID: " + uB.getUserID().getUserID());
                        System.out.println("bookID: " + uB.getBookID().getBookID());
                        System.out.println("book name: " + uB.getBookID().getBookName());
                        System.out.println("borrowDate: " + uB.getBorrowDate());
                        System.out.println("due date: " + uB.getDueDate());
                        System.out.println("returned date : " + uB.getReturnedDate());
                    }
                }
                break;

            case 4:
                System.out.println("enter a bookID: ");
                bookID = validInt();
                sc.nextLine();
                success = userBookDao.borrowBook(user.getUserID(), bookID);

                if(success > 0){
                    System.out.println("book borrowed, please return iot within 2 weeks");
                }
                else{
                    System.out.println("the book does not exist, borrow cancelled");
                }
                break;

            case 5:
                System.out.println("enter a bookID: ");
                bookID = validInt();
                sc.nextLine();
                success = userBookDao.returnBook(user.getUserID(), bookID);

                if(success > 0){
                    System.out.println("book returned");
                    userBookDao.checkIfLate(user.getUserID(), bookID);
                }
                else{
                    System.out.println("no such borrow");
                }
                break;

            case 6:
                System.out.println("your fees: " + user.getFees());
                break;

            case 7:
                sc.nextLine();
                System.out.println("insert card number");
                String card = sc.nextLine();
                System.out.println("insert cvv");
                String cvv = sc.nextLine();

                if(card.length() == 16 && cvv.length() == 3){
                    System.out.println("fee: " + user.getFees());
                    System.out.println("enter amount to pay: ");
                    int pay = validInt();
                    System.out.println("payment fulfilled");
                    userDao.updateFee(user.getUserID(), pay);
                    user.setFees(user.getFees() - pay);
                }
                else{
                    System.out.println("invalid card details");
                }
                break;

            case 8:
                user.format();
                break;

            case 9:
                logged = false;
                break;

            case 10:
                System.out.println("insert a book name: ");
                bookName = sc.nextLine();
                System.out.println("insert author name: ");
                author = sc.nextLine();
                System.out.println("enter description: ");
                desc = sc.nextLine();
                System.out.println("enter quantity: ");
                quantity = validInt();
                sc.nextLine();

                if(quantity > 0){
                    res = bookDao.addBook(new Book(bookName, author, desc, quantity));
                }
                else{
                    res = -1;
                }

                if(res > 0){
                    System.out.println("book added");
                    books = bookDao.getAllBooks();
                    addGenreToBook(books.get(books.size() - 1).getBookID());
                }
                else{
                    System.out.println("book failed to add");
                }

                break;

            case 11:
                System.out.println("enter a bookID");
                bookID = validInt();
                sc.nextLine();
                System.out.println("enter quantity to update: ");
                quantity = validInt();
                sc.nextLine();

                res = bookDao.updateBookQuantity(bookID, quantity);
                if(res > 0){
                    System.out.println("book quantity changed");
                }
                else{
                    System.out.println("book failed change quantity");
                }
                break;

            case 12:
                System.out.println("enter a userID to disable");
                userID = validInt();
                sc.nextLine();

                res = userDao.updateUserTypeByID(userID, -1);
                if(res > 0){
                    System.out.println("user disabled");
                }
                else{
                    System.out.println("something went wrong, user failed to disable");
                }
                break;

            case 13:
                System.out.println("enter a userID to enable");
                userID = validInt();
                sc.nextLine();

                res = userDao.updateUserTypeByID(userID, 0);
                if(res > 0){
                    System.out.println("user enabled");
                }
                else{
                    System.out.println("something went wrong, user failed to enable");
                }
                break;

            default:
                System.out.println("invalid command");
                break;
        }
    }

    /**
     * add a genre to book, multiple genres added
     * @param bookID the bookID
     */
    public static void addGenreToBook(int bookID){
        boolean done = false;
        List<Genre> genres = genreDao.getAllGenres();

        while(!done){
            for(Genre g : genres){
                System.out.println(g);
            }
            System.out.println("select a genre to add to book (by ID)");
            int genreID = validInt();

            if(genreID <= 0){
                done = true;
            }
            if(genreDao.getGenreByID(genreID) != null){
                if(bookGenreDao.addGenreToBook(bookID, genreID) == 1){
                    System.out.println("genre added");
                }
            }

        }
    }

    /**
     * validates an int, from the input
     * @return the int, if -11, means it's wrong
     */
    public static int validInt(){
        int valid;
        if(sc.hasNextInt()){
            valid = sc.nextInt();
        }
        else{
            valid = -11;
        }
        return valid;
    }
}