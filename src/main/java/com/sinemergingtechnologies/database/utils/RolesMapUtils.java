package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.model.RolesMap;

public class RolesMapUtils {

    public static boolean validRolesMap(RolesMap rolesMapUnderTest) {
        if (rolesMapUnderTest.getRoles_map_id() * 0 != 0) {
            System.out.println("rolesMapUnderTest.getId() * 0 != 0");
            return false;
        }

        if (rolesMapUnderTest.getUserid() instanceof Long == false) {
            System.out.println("(rolesMapUnderTest.getRoleTitle() instanceof Long == false)");
            return false;
        }
        if (rolesMapUnderTest.getRoleid() * 0 != 0) {
            System.out.println("(rolesMapUnderTest.getRoleTitle() * 0 != 0)");
            return false;
        }

        return true;
    }
}
