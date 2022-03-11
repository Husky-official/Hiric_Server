/**
 * @author SHUMBUSHO DAVID
 * @description methods for password comparing password
 */
package utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
public class ComparingPassword {
    public static String checkPassword(String password, String hashedPassword) {
        BCrypt.Result matched = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        if (matched.verified) {
            return password;
        }
        return matched.toString();
    }
}