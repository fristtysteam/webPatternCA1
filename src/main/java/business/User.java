package business;

import java.util.Objects;

public class User{

    private int userID;
    private String userName;
    private String email;
    private String password;
    private String address;
    private String phone;

    public User(int userID, String userName, String email, String password, String address, String phone) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserID() == user.getUserID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userName, email, password, address, phone);
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
                '}';
    }



}
