package internetshop.util.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            byte[] passwordInBytes = messageDigest.digest(password.getBytes());
            for (byte b: passwordInBytes) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword.toString();
    }

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static boolean isValid(String userPassword, byte[] userSalt, String passwordToCheck) {
        StringBuilder hashedPasswordToCheck = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(userSalt);
            byte[] passwordToCheckInBytes = messageDigest.digest(passwordToCheck.getBytes());
            for (byte b: passwordToCheckInBytes) {
                hashedPasswordToCheck.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return userPassword.equals(hashedPasswordToCheck.toString());
    }
}
