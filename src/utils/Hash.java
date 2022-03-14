/**
 * @author UWENAYO Alain Pacifique
 * @description methods for password hashing and checking
 * @date 2022-03-06
 */
package utils;


import org.mindrot.jbcrypt.BCrypt;

/** The type Hash. */
public class Hash {

  /**
   * Hash password string.
   *
   * @param password the password
   * @return the string
   */
  public static String hashPassword(String password) {
        System.out.println("Hashing password");
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashed);
        return hashed;
    }

}
