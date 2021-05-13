package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.model.Role;

import java.util.UUID;

public class RoleUtils {

    private static int minStringLength = 1;

    public static boolean validRole(Role roleUnderTest) {
        if (roleUnderTest.getId() instanceof Long == false) {
            System.out.println("(roleUnderTest.getId() instanceof Long == false)");
            return false;
        }
        if (roleUnderTest.getId() < minStringLength) {
            System.out.println("(roleUnderTest.getId() < minStringLength)");
            return false;
        }

        if (roleUnderTest.getRoleTitle() instanceof String == false) {
            System.out.println("(roleUnderTest.getRoleTitle() instanceof String == false)");
            return false;
        }
        if (roleUnderTest.getRoleTitle().length() < minStringLength) {
            System.out.println("(roleUnderTest.getRoleTitle().length() < minStringLength)");
            return false;
        }

        return true;
    }
}
