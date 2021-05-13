package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.model.ProviderEmail;

import java.util.UUID;

public class ProviderEmailUtils {

    private static int minStringLength = 1;

    public static boolean validProviderEmail(ProviderEmail providerEmailUnderTest) {
        if (providerEmailUnderTest.getId() instanceof Long == false) {
            System.out.println("(providerEmailUnderTest.getId() instanceof Long == false)");
            return false;
        }
        if (providerEmailUnderTest.getId() < minStringLength) {
            System.out.println("(providerEmailUnderTest.getId() < minStringLength)");
            return false;
        }
        if (providerEmailUnderTest.getUuid() instanceof UUID == false) {
            System.out.println("(providerEmailUnderTest.getUuid() instanceof UUID == false)");
            return false;
        }
        if (providerEmailUnderTest.getUuid().toString().length() < minStringLength) {
            System.out.println("(providerEmailUnderTest.getUuid().toString().length() < minStringLength)");
            return false;
        }

        if (providerEmailUnderTest.getProviderEmail() instanceof String == false) {
            System.out.println("(providerEmailUnderTest.getProviderEmail() instanceof String == false)");
            return false;
        }
        if (providerEmailUnderTest.getProviderEmail().length() < minStringLength) {
            System.out.println("(providerEmailUnderTest.getProviderEmail().length() < minStringLength)");
            return false;
        }

        return true;
    }
}
