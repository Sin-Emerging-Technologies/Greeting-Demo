package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.Enums;
import com.sinemergingtechnologies.database.model.Role;

public class RoleUtils {

    private static int minStringLength = 1;

    public static boolean validRole(Role roleUnderTest) {
        if (roleUnderTest.getId() * 0 != 0) {
            System.out.println("roleUnderTest.getId() * 0 != 0");
            return false;
        }

        if (roleUnderTest.getName() instanceof String == false) {
            System.out.println("(roleUnderTest.getName() instanceof String == false)");
            return false;
        }
        if (roleUnderTest.getName().length() < minStringLength) {
            System.out.println("(roleUnderTest.getName().length() < minStringLength)");
            return false;
        }

        for (int i = 0; i < Enums.RoleTitles.values().length; i++) {
            if (Enums.RoleTitles.values()[i].toString()
                    .equalsIgnoreCase(roleUnderTest.getName())) {
                return true;
            }
        }

        return false;
    }
}
