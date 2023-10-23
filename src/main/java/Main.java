import org.mindrot.jbcrypt.BCrypt;

public class Main {
    public static void main(String[] args) {
        String pass = "rippleMMW1$";
        String hash = BCrypt.hashpw(pass, BCrypt.gensalt());
        System.out.println(hash);
        System.out.println(hash.length());
    }
}