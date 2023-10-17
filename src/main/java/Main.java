import business.Book;
import util.AESCrypto;

import javax.crypto.BadPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, BadPaddingException {
        String secret = AESCrypto.AESKey();
        System.out.println(secret.length());
        System.out.println("rippleMMW1");
        AESCrypto.SALT = AESCrypto.salt();
        System.out.println(AESCrypto.SALT.length());
        String pass = AESCrypto.encrypt("rippleMMW1", secret);
        System.out.println(pass);
//        secret = AESCrypto.AESKey();
        System.out.println(AESCrypto.decrypt(pass, secret));


    }
}