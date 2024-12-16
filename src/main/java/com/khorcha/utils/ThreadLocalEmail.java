package com.khorcha.utils;

public class ThreadLocalEmail {

    private static final ThreadLocal<String> USER_EMAIL = new ThreadLocal<>();

    private ThreadLocalEmail() {

    }

    public static void setEmail(String email) {
        USER_EMAIL.set(email);
    }

    public static String getEmail() {
        return USER_EMAIL.get();
    }

    public static void clear() {
        USER_EMAIL.remove();
    }
}
