package util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class AESCrypto {

    public AESCrypto(){}
    public static String SALT;

    /**
     * create an AES key
     * <a href="https://www.baeldung.com/java-secure-aes-key">...</a>
     * @return the AES key
     * @throws NoSuchAlgorithmException if key could not be generated
     */
    public static String AESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(192);
        return Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
    }

    /**
     * create salt for increase in encryption strength
     * <a href="https://2023-moodle.dkit.ie/mod/resource/view.php?id=342733">...</a>
     * @return the salt
     */
    public static String salt(){
        // Create an array of 32 bytes (256 bits) to store random bytes
        byte[] saltBytes = new byte[32];
        // The SecureRandom class provides CSPRNG functionality
        SecureRandom random = new SecureRandom();
        // Fill the byte array with random bytes
        random.nextBytes(saltBytes);
        // Encode the random bytes as a Base64 string
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    /**
     * encrypt function
     * <a href="https://www.geeksforgeeks.org/what-is-java-aes-encryption-and-decryption/">...</a>
     * @param strToEncrypt line to encrypt
     * @param SECRET_KEY the secret key
     * @return the encrypted sentence
     */
    public static String encrypt(String strToEncrypt, String SECRET_KEY) {
        try {
            // Create default byte array
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec
                    = new IvParameterSpec(iv);

            // Create SecretKeyFactory object
            SecretKeyFactory factory
                    = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256");

            // Create KeySpec object and assign with
            // constructor
            KeySpec spec = new PBEKeySpec(
                    SECRET_KEY.toCharArray(), SALT.getBytes(),
                    65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(
                    tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(
                    "AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,
                    ivspec);
            // Return encrypted string
            return Base64.getEncoder().encodeToString(
                    cipher.doFinal(strToEncrypt.getBytes(
                            StandardCharsets.UTF_8)));
        }
        catch (Exception e) {
            System.out.println("Error while encrypting: "
                    + e.toString());
        }
        return null;
    }


    /**
     * decrypt function
     * <a href="https://www.geeksforgeeks.org/what-is-java-aes-encryption-and-decryption/">...</a>
     * @param strToDecrypt the line to decrypt
     * @param SECRET_KEY the secret key
     * @return the decrypted line
     * @throws BadPaddingException if secret key is wrong
     */
    public static String decrypt(String strToDecrypt, String SECRET_KEY) throws BadPaddingException {
        try {
            // Default byte array
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0 };
            // Create IvParameterSpec object and assign with
            // constructor
            IvParameterSpec ivspec
                    = new IvParameterSpec(iv);

            // Create SecretKeyFactory Object
            SecretKeyFactory factory
                    = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256");

            // Create KeySpec object and assign with
            // constructor
            KeySpec spec = new PBEKeySpec(
                    SECRET_KEY.toCharArray(), SALT.getBytes(),
                    65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(
                    tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(
                    "AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey,
                    ivspec);
            // Return decrypted string
            return new String(cipher.doFinal(
                    Base64.getDecoder().decode(strToDecrypt)));
        }
        catch(BadPaddingException e){
            throw new BadPaddingException();
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: "
                    + e.toString());
        }
        return null;
    }
}
