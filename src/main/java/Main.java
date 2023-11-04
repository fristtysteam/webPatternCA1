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
    private static boolean session = true;
    private static int choice = -1;
    private static User user = null;

    public static void main(String[] args) {

        while(session){
            if(!logged){
                user = userSession();
            }

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

    }

    /**
     * displays the menu based on the usertype
     * @param userType the userType
     */
    public static void displayMenu(int userType){
        if(userType == 0){
            System.out.println("Select An Option: ");
            System.out.println("1. View Details Of All Books In Library");
            System.out.println("2. View Loans");
            System.out.println("3. View History");
            System.out.println("4. Borrow A Book");
            System.out.println("5. Return A Book");
            System.out.println("6. View Fees");
            System.out.println("7. Pay The Fee");
            System.out.println("8. Profile");
            System.out.println("9. Logout");
        }
        else{
            System.out.println("Select An Option: ");
            System.out.println("1. View Details Of All Books In Library");
            System.out.println("2. View Loans");
            System.out.println("3. View History");
            System.out.println("4. Borrow A Book");
            System.out.println("5. Return A Book");
            System.out.println("6. View Fees");
            System.out.println("7. Pay The Fee");
            System.out.println("8. Profile");
            System.out.println("9. Logout");
            System.out.println("10. Add A Book");
            System.out.println("11. Update Quantity In A Book");
            System.out.println("12. Disable Member");
            System.out.println("13. Enable Member");
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
            System.out.println("Select An Option (Use Number): ");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            choice = validInt();
            sc.nextLine();

            switch (choice){
                case 1:
                    System.out.println("Enter Email: ");
                    email = sc.nextLine();
                    System.out.println("Enter Password: ");
                    password = sc.nextLine();

                    try{
                        user = userDao.loginUser(email, password);
                    }
                    catch (InvalidEmailException e){
                        System.out.println(e.getMessage());
                        System.out.println("Try A Different Email");
                    }
                    catch (InvalidPasswordException pe){
                        System.out.println(pe.getMessage());
                        System.out.println("Try Again");
                    }

                    break;

                case 2:
                    System.out.println("Enter Username: ");
                    username = sc.nextLine();
                    System.out.println("Enter Email: ");
                    email = sc.nextLine();
                    System.out.println("Enter Password: ");
                    password = sc.nextLine();
                    System.out.println("Enter Address: ");
                    address = sc.nextLine();
                    System.out.println("Enter Phone: ");
                    phone = sc.nextLine();

                    try{
                        user = userDao.registerUser(username, email, password,
                                address, phone);
                    }
                    catch (DuplicateUsernameException e){
                        System.out.println(e.getMessage());
                        System.out.println("Try A Different Username");
                    }
                    catch (DuplicateEmailException e){
                        System.out.println(e.getMessage());
                        System.out.println("Try A Different Email Or Login Instead");
                    }

                    break;

                case 3:
                    session = false;
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Option");
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
                    System.out.println("BookID: " + b.getBookID());
                    System.out.println("Book Name: " + b.getBookName());
                    System.out.println("Author: " + b.getAuthor());
                    System.out.print("Genres: ");
                    for(Genre g : genres){
                        System.out.print(g.getGenreName() + " ");
                    }
                    System.out.println();
                    System.out.println("Description: " + b.getDescription());
                    System.out.println("Quantity: " + b.getQuantity());
                }
                System.out.println("-----------------------------------------");
                break;

            case 2:
                List<UserBook> userBooks = userBookDao.getAllCurrentBooksByUserID(user.getUserID());
                if(userBooks.isEmpty()){
                    System.out.println("-----------------------------------------");
                    System.out.println("No Loans Available, Borrow One!");
                }
                else{
                    for(UserBook uB : userBooks){
                        System.out.println("-----------------------------------------");
                        System.out.println("UserID: " + uB.getUserID().getUserID());
                        System.out.println("BookID: " + uB.getBookID().getBookID());
                        System.out.println("Book Name: " + uB.getBookID().getBookName());
                        System.out.println("Borrow Date: " + uB.getBorrowDate());
                        System.out.println("Due Date: " + uB.getDueDate());
                        if(uB.getReturnedDate() == null){
                            System.out.println("Returned Date : N/A");
                        }else{
                            System.out.println("Returned Date : " + uB.getReturnedDate());
                        }
                    }
                }
                System.out.println("-----------------------------------------");
                break;

            case 3:
                List<UserBook> userBooks1 = userBookDao.getAllBooksByUserID(user.getUserID());
                if(userBooks1.isEmpty()){
                    System.out.println("-----------------------------------------");
                    System.out.println("no history available, borrow one!");
                }
                else{
                    for(UserBook uB : userBooks1){
                        System.out.println("-----------------------------------------");
                        System.out.println("UserID: " + uB.getUserID().getUserID());
                        System.out.println("BookID: " + uB.getBookID().getBookID());
                        System.out.println("Book Name: " + uB.getBookID().getBookName());
                        System.out.println("Borrow Date: " + uB.getBorrowDate());
                        System.out.println("Due Date: " + uB.getDueDate());
                        if(uB.getReturnedDate() == null){
                            System.out.println("Returned Date : N/A");
                        }else{
                            System.out.println("Returned Date : " + uB.getReturnedDate());
                        }
                        System.out.println("-----------------------------------------");
                    }
                }
                break;

            case 4:
                System.out.println("Enter A bookID: ");
                bookID = validInt();
                sc.nextLine();

                if(bookID == -11){
                    success = 0;
                }
                else{
                    success = userBookDao.borrowBook(user.getUserID(), bookID);
                }
                System.out.println("-----------------------------------------");
                if(success > 0){
                    System.out.println("Book Borrowed, Please Return It Within 2 Weeks From Now");
                }
                else{
                    System.out.println("The Book Does Not Exist, Borrow Cancelled");
                }
                System.out.println("-----------------------------------------");
                break;

            case 5:
                System.out.println("Enter A bookID: ");
                bookID = validInt();
                sc.nextLine();

                if(bookID == -11){
                    success = 0;
                }
                else{
                    success = userBookDao.returnBook(user.getUserID(), bookID);
                }
                System.out.println("-----------------------------------------");
                if(success > 0){
                    System.out.println("Book Returned");
                    userBookDao.checkIfLate(user.getUserID(), bookID);
                }
                else{

                    System.out.println("Book Borrowed Does Not Exist");
                }
                System.out.println("-----------------------------------------");
                break;

            case 6:
                System.out.println("-----------------------------------------");
                System.out.println("Your Fees Total: " + user.getFees());
                System.out.println("-----------------------------------------");
                break;

            case 7:
                System.out.println("Insert Card Number");
                String card = sc.nextLine();
                System.out.println("Insert CVV");
                String cvv = sc.nextLine();

                if(card.length() == 16 && cvv.length() == 3) {
                    System.out.println("Fee: " + user.getFees());
                    System.out.println("Enter Amount To Pay: ");
                    int pay = validInt();
                    int refundAmount = pay - user.getFees();
                    int totalAmount = pay - refundAmount;
                    int leftToPay = user.getFees() - pay;

                    if (user.getFees() < pay) {

                        System.out.println("-----------------------------------------");
                        System.out.println("Pay Amount Is Too High \nAuto Deducted, Total Amount Payed : " + totalAmount + "\nTotal Amount Paid Refunded : " + refundAmount);
                        userDao.updateFee(user.getUserID(), -totalAmount);
                        user.setFees(user.getFees() - totalAmount);

                    } else if (user.getFees() == pay) {
                        System.out.println("-----------------------------------------");
                        System.out.println("Payment Fulfilled");
                        userDao.updateFee(user.getUserID(), -pay);
                        user.setFees(user.getFees() - pay);
                    }
                    else{

                        System.out.println("-----------------------------------------");
                        System.out.println("Total Fee Deducted From " + user.getFees() + " To " + leftToPay);
                        System.out.println("Total Amount Payed This Time : " + pay);
                        System.out.println("Amount Left To Pay : " + leftToPay);
                        userDao.updateFee(user.getUserID(), - pay);
                        user.setFees(leftToPay);
                    }

                } else{
                    System.out.println("-----------------------------------------");
                    System.out.println("Invalid Card Details");
                }
                System.out.println("-----------------------------------------");
                break;
            case 8:
                user.format();
                break;

            case 9:
                logged = false;
                break;

            default:
                System.out.println("Invalid Command");
                break;
        }
    }

    /**
     * the admin interface
     */
    public static void adminInterface(){
        int bookID;
        String bookName;
        String author;
        String desc;
        int quantity;
        int res;
        int userID;
        List<Book> books;

        userInterface();

        switch(choice){
            case 1, 2, 3, 4, 5, 6, 7, 8, 9:
                break;

            case 10:
                sc.nextLine();
                System.out.println("Insert A Book Name: ");
                bookName = sc.nextLine();
                System.out.println("Insert Author Name: ");
                author = sc.nextLine();
                System.out.println("Enter Description: ");
                desc = sc.nextLine();
                System.out.println("Enter Quantity: ");
                quantity = validInt();
                sc.nextLine();
                System.out.println("-----------------------------------------");
                if(quantity > 0){
                    res = bookDao.addBook(new Book(bookName, author, desc, quantity));
                }
                else{
                    res = -1;
                }

                if(res > 0){
                    System.out.println("Book Added");
                    books = bookDao.getAllBooks();
                    addGenreToBook(books.get(books.size() - 1).getBookID());
                }
                else{
                    System.out.println("Book Failed To Add");
                }
                System.out.println("-----------------------------------------");
                break;

            case 11:

                System.out.println("Enter A BookID");
                bookID = validInt();
                sc.nextLine();
                System.out.println("Enter Quantity To Update (- Or +) : ");
                quantity = validInt();
                sc.nextLine();
                System.out.println("-----------------------------------------");
                res = bookDao.updateBookQuantity(bookID, quantity);
                if(res > 0){
                    System.out.println("Book Quantity Changed");
                }
                else{
                    System.out.println("Book Failed Change Quantity");
                }
                System.out.println("-----------------------------------------");
                break;

            case 12:
                System.out.println("Enter A UserID To Disable");
                userID = validInt();
                sc.nextLine();

                res = userDao.updateUserTypeByID(userID, -1);
                System.out.println("-----------------------------------------");
                if(res > 0){
                    System.out.println("User Disabled");
                }
                else{
                    System.out.println("Something Went Wrong, User Failed To Disable");
                }
                System.out.println("-----------------------------------------");
                break;

            case 13:
                System.out.println("Enter A UserID To Enable");
                userID = validInt();
                sc.nextLine();

                res = userDao.updateUserTypeByID(userID, 0);
                System.out.println("-----------------------------------------");
                if(res > 0){
                    System.out.println("User Enabled");
                }
                else{
                    System.out.println("Something Went Wrong, User Failed To Enable");
                }
                System.out.println("-----------------------------------------");
                break;

            default:
                System.out.println("Invalid Command");
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
            System.out.println("Select A Genre To Add To Book (By ID), Enter 0 To Stop Entering Genre");
            int genreID = validInt();

            if(genreID <= 0){
                done = true;
            }
            if(genreDao.getGenreByID(genreID) != null){
                if(bookGenreDao.addGenreToBook(bookID, genreID) == 1){
                    System.out.println("Genre Added");
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