package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.model.ProviderClientMap;

import java.util.UUID;

public class ProviderClientMapUtils {

    private static int minStringLength = 1;

    public static boolean validProviderClientMap(ProviderClientMap providerClientMapUnderTest) {
        if (providerClientMapUnderTest.getId() instanceof Long == false) return false;
        if (providerClientMapUnderTest.getId() < minStringLength) return false;

        if (providerClientMapUnderTest.getClient_uuid() instanceof UUID == false) return false;
        if (providerClientMapUnderTest.getClient_uuid().toString().length() < minStringLength) return false;

        if (providerClientMapUnderTest.getProvider_uuid() instanceof UUID == false) return false;
        if (providerClientMapUnderTest.getProvider_uuid().toString().length() < minStringLength) return false;

        if (providerClientMapUnderTest.getProvider_id() instanceof Long == false) return false;
        if (providerClientMapUnderTest.getProvider_id() < minStringLength) return false;

        return true;
    }
}
