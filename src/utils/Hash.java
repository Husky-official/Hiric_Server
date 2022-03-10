/**
 * @author UWENAYO Alain Pacifique
 * @description methods for password hashing and checking
 * @date 2022-03-06
 */
package utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * The type Hash.
 */
public class Hash {

    /**
     * Hash password string.
     *
     * @param password the password
     * @return the string
     */
    public static String hashPassword(String password) {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        return checkPassword(password, hashedPassword);
    }

    /**
     * Check password string.
     *
     * @param password       the password
     * @param hashedPassword the hashed password
     * @return the string
     */
    public static String checkPassword(String password, String hashedPassword){
        BCrypt.Result matched = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        if (matched.verified) {
            return hashedPassword;
        }
        return matched.toString();
    }

}
