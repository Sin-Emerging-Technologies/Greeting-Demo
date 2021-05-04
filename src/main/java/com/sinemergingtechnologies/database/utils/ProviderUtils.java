package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.model.Provider;

import java.util.Objects;
import java.util.UUID;

public class ProviderUtils {

    private static int minStringLength = 1;
    private static int minPasswordLength = 4;

    public static boolean validProvider(Provider providerUnderTest) {
        if (providerUnderTest.getId() instanceof Long == false) return false;
        if (providerUnderTest.getId() < minStringLength) return false;

        if (providerUnderTest.getEmail() instanceof String == false) return false;
        if (providerUnderTest.getEmail().length() < minStringLength) return false;
        if (providerUnderTest.getEmail().indexOf("@") < 1) return false;

        if (providerUnderTest.getFirstname() instanceof String == false) return false;
        if (providerUnderTest.getFirstname().length() < minStringLength) return false;

        if (providerUnderTest.getLastname() instanceof String == false) return false;
        if (providerUnderTest.getLastname().length() < minStringLength) return false;

        if (providerUnderTest.getCity() instanceof String == false) return false;
        if (providerUnderTest.getCity().length() < minStringLength) return false;

        if (providerUnderTest.getUs_state() instanceof String == false) return false;
        if (providerUnderTest.getUs_state().length() < minStringLength) return false;

        if (providerUnderTest.getProvider_uuid() instanceof UUID == false) return false;
        if (providerUnderTest.getProvider_uuid().toString().length() < minStringLength) return false;

        if (providerUnderTest.getPassword() instanceof String == false) return false;
        if (providerUnderTest.getConfirm() instanceof String == false) return false;

        if (providerUnderTest.getPassword().length() < minPasswordLength) return false;
        if (!Objects.equals(providerUnderTest.getPassword(), providerUnderTest.getConfirm())) return false;

        return true;
    }
}
