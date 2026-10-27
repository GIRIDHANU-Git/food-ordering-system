
package com.foodorder.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean verifyPassword(
            String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }
}
