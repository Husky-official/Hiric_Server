/**
 * @author SHUMBUSHO DAVID
 * @description methods for password comparing password
 */
package utils;

import org.mindrot.jbcrypt.BCrypt;

/** The type Comparing password. */
public class ComparingPassword {
  /**
   * Check password boolean.
   *
   * @param password the password
   * @param hashedPassword the hashed password
   * @return the boolean
   */
  public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}