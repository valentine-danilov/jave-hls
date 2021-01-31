package com.epam.hls.util;

import java.util.Collection;

import static java.util.Objects.isNull;

public class Assertions {

    private Assertions() {
        /*Private constructor for the utility class*/
    }

    public static void assertNotNull(Object object, String message) {
        if (isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> void assertNotEmpty(Collection<T> collection, String message) {
        if (isNull(collection) || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
