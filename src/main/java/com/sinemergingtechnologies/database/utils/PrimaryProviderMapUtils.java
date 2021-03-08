package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.model.PrimaryProviderMap;

import java.util.UUID;

public class PrimaryProviderMapUtils {

    private static int minStringLength = 1;

    public static boolean validPrimaryProviderMap(PrimaryProviderMap primaryProviderMapUnderTest) {
        if (primaryProviderMapUnderTest.getId() instanceof Long == false) return false;
        if (primaryProviderMapUnderTest.getId() < minStringLength) return false;

        if (primaryProviderMapUnderTest.getClient_uuid() instanceof UUID == false) return false;
        if (primaryProviderMapUnderTest.getClient_uuid().toString().length() < minStringLength) return false;

        if (primaryProviderMapUnderTest.getProvider_uuid() instanceof UUID == false) return false;
        if (primaryProviderMapUnderTest.getProvider_uuid().toString().length() < minStringLength) return false;

        if (primaryProviderMapUnderTest.getProvider_id() instanceof Long == false) return false;
        if (primaryProviderMapUnderTest.getProvider_id() < minStringLength) return false;

        return true;
    }
}
