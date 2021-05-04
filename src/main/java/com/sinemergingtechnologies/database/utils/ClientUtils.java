package com.sinemergingtechnologies.database.utils;

import com.sinemergingtechnologies.database.model.Client;

import java.util.Objects;
import java.util.UUID;

public class ClientUtils {

    private static int minStringLength = 1;
    private static int minPasswordLength = 4;

    public static boolean validClient(Client clientUnderTest) {
        if (clientUnderTest.getId() instanceof Long == false) return false;
        if (clientUnderTest.getId() < minStringLength) return false;

        if (clientUnderTest.getEmail() instanceof String == false) return false;
        if (clientUnderTest.getEmail().length() < minStringLength) return false;
        if (clientUnderTest.getEmail().indexOf("@") < 1) return false;

        if (clientUnderTest.getFirstname() instanceof String == false) return false;
        if (clientUnderTest.getFirstname().length() < minStringLength) return false;

        if (clientUnderTest.getLastname() instanceof String == false) return false;
        if (clientUnderTest.getLastname().length() < minStringLength) return false;

        if (clientUnderTest.getCity() instanceof String == false) return false;
        if (clientUnderTest.getCity().length() < minStringLength) return false;

        if (clientUnderTest.getUs_state() instanceof String == false) return false;
        if (clientUnderTest.getUs_state().length() < minStringLength) return false;

        if (clientUnderTest.getClient_uuid() instanceof UUID == false) return false;
        if (clientUnderTest.getClient_uuid().toString().length() < minStringLength) return false;

        if (clientUnderTest.getPassword() instanceof String == false) return false;
        if (clientUnderTest.getConfirm() instanceof String == false) return false;

        if (clientUnderTest.getPassword().length() < minPasswordLength) return false;
        if (!Objects.equals(clientUnderTest.getPassword(), clientUnderTest.getConfirm())) return false;

        return true;
    }
}
