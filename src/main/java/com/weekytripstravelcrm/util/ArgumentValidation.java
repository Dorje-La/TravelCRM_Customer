package com.weekytripstravelcrm.util;

import com.weekytripstravelcrm.exception.InvalidArgumentException;

public class ArgumentValidation {
    public static void notNull(Object obj, String str) {
        try {
            if(obj == null || obj.equals("")) {
                throw new InvalidArgumentException(str);
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid argument request, "+e.getMessage());
        }
    }
}
