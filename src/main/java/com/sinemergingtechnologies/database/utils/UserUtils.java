package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.model.User;

import java.util.Objects;
import java.util.UUID;

public class UserUtils {

    private static int minStringLength = 1;
    private static int minPasswordLength = 4;

    public static boolean validUser(User userUnderTest) {
        if (userUnderTest.getId() instanceof Long == false) {
            System.out.println("(userUnderTest.getId() instanceof Long == false)");
            return false;
        }
        if (userUnderTest.getId() < minStringLength) {
            System.out.println("(userUnderTest.getId() < minStringLength)");
            return false;
        }

        if (userUnderTest.getEmail() instanceof String == false) {
            System.out.println("(userUnderTest.getEmail() instanceof String == false)");
            return false;
        }
        if (userUnderTest.getEmail().length() < minStringLength) {
            System.out.println("(userUnderTest.getEmail().length() < minStringLength)");
            return false;
        }
        if (userUnderTest.getEmail().indexOf("@") < 1) {
            System.out.println("(userUnderTest.getEmail().indexOf(\"@\") < 1)");
            return false;
        }

        if (userUnderTest.getFirstname() instanceof String == false) {
            System.out.println("(userUnderTest.getFirstname() instanceof String == false)");
            return false;
        }
        if (userUnderTest.getFirstname().length() < minStringLength) {
            System.out.println("(userUnderTest.getFirstname().length() < minStringLength)");
            return false;
        }

        if (userUnderTest.getLastname() instanceof String == false) {
            System.out.println("(userUnderTest.getLastname() instanceof String == false)");
            return false;
        }
        if (userUnderTest.getLastname().length() < minStringLength) {
            System.out.println("(userUnderTest.getLastname().length() < minStringLength)");
            return false;
        }

        if (userUnderTest.getCity() instanceof String == false) {
            System.out.println("(userUnderTest.getCity() instanceof String == false)");
            return false;
        }
        if (userUnderTest.getCity().length() < minStringLength) {
            System.out.println("(userUnderTest.getCity().length() < minStringLength)");
            return false;
        }

        if (userUnderTest.getUs_state() instanceof String == false) {
            System.out.println("(userUnderTest.getUs_state() instanceof String == false)");
            return false;
        }
        if (userUnderTest.getUs_state().length() < minStringLength) {
            System.out.println("(userUnderTest.getUs_state().length() < minStringLength)");
            return false;
        }

        if (userUnderTest.getUuid() instanceof UUID == false) {
            System.out.println("(userUnderTest.getUuid() instanceof UUID == false)");
            return false;
        }
        if (userUnderTest.getUuid().toString().length() < minStringLength) {
            System.out.println("(userUnderTest.getUuid().toString().length() < minStringLength)");
            return false;
        }

        if (userUnderTest.getPassword() instanceof String == false) {
            System.out.println("(userUnderTest.getPassword() instanceof String == false)");
            return false;
        }

        if (userUnderTest.getPassword().length() < minPasswordLength) {
            System.out.println("(userUnderTest.getPassword().length() < minPasswordLength)");
            return false;
        }

        return true;
    }
}
