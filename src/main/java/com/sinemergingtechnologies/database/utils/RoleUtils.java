package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.Enums;
import com.sinemergingtechnologies.database.model.Role;

import java.util.UUID;

public class RoleUtils {

    private static int minStringLength = 1;

    public static boolean validRole(Role roleUnderTest) {
        if (roleUnderTest.getId() * 0 != 0) {
            System.out.println("roleUnderTest.getId() * 0 != 0");
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

        for (int i = 0; i < Enums.RoleTitles.values().length; i++) {
            if (Enums.RoleTitles.values()[i].toString()
                    .equalsIgnoreCase(roleUnderTest.getRoleTitle())) {
                return true;
            }
        }

        return false;
    }
}
