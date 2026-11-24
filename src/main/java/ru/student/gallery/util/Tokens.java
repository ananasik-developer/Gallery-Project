package ru.student.gallery.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.security.SecureRandom;

public class Tokens {

    public static final String CSRF = "csrfToken";
    public static final String FORM = "formToken";

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final char[] HEX = "0123456789abcdef".toCharArray();

    public static String generate() {
        byte[] bytes = new byte[16];
        RANDOM.nextBytes(bytes);

        char[] result = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xff;
            result[i * 2] = HEX[value >>> 4];
            result[i * 2 + 1] = HEX[value & 0x0f];
        }
        return new String(result);
    }

    public static String getOrCreate(HttpSession session, String name) {
        Object value = session.getAttribute(name);
        if (value == null) {
            value = generate();
            session.setAttribute(name, value);
        }
        return (String) value;
    }

    public static void rotate(HttpSession session, String name) {
        session.setAttribute(name, generate());
    }

    public static boolean accept(HttpServletRequest request, HttpSession session, boolean consumeForm) {
        String expectedCsrf = (String) session.getAttribute(CSRF);
        if (expectedCsrf == null || !expectedCsrf.equals(request.getParameter(CSRF))) {
            return false;
        }

        if (!consumeForm) {
            return true;
        }

        String expectedForm = (String) session.getAttribute(FORM);
        if (expectedForm == null || !expectedForm.equals(request.getParameter(FORM))) {
            return false;
        }

        rotate(session, FORM);
        request.setAttribute(FORM, session.getAttribute(FORM));
        return true;
    }
}
