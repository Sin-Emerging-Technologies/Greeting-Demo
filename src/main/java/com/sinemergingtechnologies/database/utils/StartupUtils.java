package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.Enums;
import com.sinemergingtechnologies.database.model.Role;
import com.sinemergingtechnologies.database.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupUtils {

    @Autowired
    private RoleService roleService;

    public void updateDbWithRoleEnums() {
        System.out.println("Comparing internal enums with database");

        List<Role> rolesInDb = roleService.findAll();
        Object[] rolesInDbStrings = rolesInDb.toArray();

        // for each enum we have
        Enums.RoleTitles[] enumRoleTitles = Enums.RoleTitles.values();

        System.out.println(String.format("rolesInDbStrings %s vs %s enumRoleTitles", rolesInDbStrings.length, enumRoleTitles.length));
        for (int i = 0; i < enumRoleTitles.length; i++) {
            String checkThisRole = enumRoleTitles[i].toString();
            // check if it's in the db
            boolean found = arrayContainsObject(rolesInDbStrings, checkThisRole);

            // if not, create a new Role
            if (!found) {
                Role newRole = new Role(checkThisRole);
                // then service.save(role)
                roleService.save(newRole);
            }
        }
    }

    private boolean arrayContainsObject(Object[] testArray, String target) {
        for(Object x : testArray){
            if(x.equals(target)){
                System.out.println(String.format("target %s == %s x", target, x));
                return true;
            }
        }
        System.out.println(String.format("%s not found", target));
        return false;
    }
}
