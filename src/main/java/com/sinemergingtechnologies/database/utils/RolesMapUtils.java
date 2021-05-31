package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.model.RolesMap;

public class RolesMapUtils {

    public static boolean validRolesMap(RolesMap rolesMapUnderTest) {
        if (rolesMapUnderTest.getRoles_map_id() instanceof Long == false) {
            System.out.println("rolesMapUnderTest.getRoles_map_id() instanceof Long == false");
            return false;
        }

        if (rolesMapUnderTest.getUserid() instanceof Long == false) {
            System.out.println("(rolesMapUnderTest.getUserid() instanceof Long == false)");
            return false;
        }
        if (rolesMapUnderTest.getRoleid() * 0 != 0) {
            System.out.println("(rolesMapUnderTest.getRoleid() * 0 != 0)");
            return false;
        }

        return true;
    }
}
