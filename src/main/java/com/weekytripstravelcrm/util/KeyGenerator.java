package com.weekytripstravelcrm.util;

import java.util.HashMap;
import java.util.Map;

public class KeyGenerator {

    // Class is key type and long is the key value
    private static final Map<Class, Long> counters = new HashMap<>();

    //synchronized method used to ensure thread safety.
    public synchronized long getNextPrimaryKey(Class className, Long maxValue) {
        // checks if the class (key) is already present in the counters map.
        // If not, it adds an entry with an initial value of 1000L
        counters.putIfAbsent(className, maxValue);

        // retrieves the current primary key value associated with
        // the given class from the counters map.
        long primaryKey = counters.get(className);

        if (maxValue > primaryKey) {
            primaryKey = maxValue;
        }
        // After retrieving the current primary key value, it increments the value by 1
        // and stores it back in the counters map for the same class.
        counters.put(className, primaryKey++);

        // it returns the generated primary key value
        return primaryKey;
    }

    public static String generateId(String prefix, Long maxValue, Class className, Long startingValue) {
        Long finalValue ;
        String finalGeneratedKey;
        KeyGenerator keyGenerator = new KeyGenerator();

        if (maxValue == null) {
            finalValue = keyGenerator.getNextPrimaryKey(className, startingValue);
        } else {
            finalValue = keyGenerator.getNextPrimaryKey(className, maxValue);
        }
        finalGeneratedKey = prefix + finalValue;
        return finalGeneratedKey;
    }
}
