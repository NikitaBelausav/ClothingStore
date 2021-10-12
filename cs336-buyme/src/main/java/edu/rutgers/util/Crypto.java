package edu.rutgers.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * A utility class with an encryption function that encrypts a string to AES-256-CBC.
 * Included is a function to also decrypt, but mainly used for testing.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public class Crypto {
    private static final String SECRET_KEY;

    // On first run, the program should find the "secret.key" file 
    // and write its contents to SECRET_KEY. Otherwise uses a default secret.
    // TODO: Does not work, don't really know how tomcat WAR looks for files.
    static {
        String secret = "ScmjFwgoWAEg7hOYt91smqwtAtcCe279";
        File secretFile = new File("secret.key");
            
        if (secretFile.exists()) {
            System.out.println("Using secret");
            try (Scanner s = new Scanner(secretFile)) {
                secret = s.nextLine();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        SECRET_KEY = secret;
    }

    /**
     * Encrypt data using the AES-256-CBC standard.
     * 
     * @param data the data to encrypt
     * @param salt the salt to mix into the hash
     * @return     the salted hash
     */
    public static String encrypt(String data, String salt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
 
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
 
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder()
                .encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        
        return null;
    }

    /**
     * Decrypt a hash encrypted in the AES-256-CBC standard.
     * 
     * @param hash the hash to decrypt
     * @param salt the salt used in encryption
     * @return     the decrypted data
     */
    public static String decrypt(String hash, String salt) {
        try {
          byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
          IvParameterSpec ivspec = new IvParameterSpec(iv);
        
          SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
          KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt.getBytes(), 65536, 256);
          SecretKey tmp = factory.generateSecret(spec);
          SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        
          Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
          cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
          return new String(cipher.doFinal(Base64.getDecoder().decode(hash)));
        } catch (Exception e) {
          System.out.println("Error while decrypting: " + e.toString());
        }

        return null;
    }

    public static void main(String[] args) {
        // Test crypto
        String salt = Long.toHexString(Calendar.getInstance().getTimeInMillis());
        String hash = encrypt("password", salt);

        System.out.println(salt);

        System.out.println(decrypt(hash, salt));
    }
}
