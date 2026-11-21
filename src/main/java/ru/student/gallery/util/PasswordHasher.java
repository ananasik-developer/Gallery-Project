package ru.student.gallery.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean matches(String password, String hash) {
        if (password == null || hash == null) return false;
        try {
            return BCrypt.checkpw(password, hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
