package business;

import java.util.Objects;

public class User{

    private int userID;
    private String userName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private int fees;
    private int userType;

    public User(int userID, String userName, String email, String password,
                String address, String phone, int fees, int userType) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.fees = fees;
        this.userType = userType;
    }

    public User() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userID == user.userID;
    }

    @Override
    public int hashCode() {
        return userID;
    }

    public void format(){
        System.out.println("-------------------------------------");
        System.out.println("userID: " + userID);
        System.out.println("username: " + userName);
        System.out.println("email: " + email);
        System.out.println("password: " + password);
        System.out.println("address: " + address);
        System.out.println("phone: " + phone);
        System.out.println("fees: " + fees);
        System.out.println("-------------------------------------");
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", fees=" + fees + '\'' +
                ", userType=" + userType +
                '}';
    }
}
