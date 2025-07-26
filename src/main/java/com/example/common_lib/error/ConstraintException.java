package com.example.common_lib.error;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.error  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 4:56 PM
 */

public class ConstraintException extends RuntimeException {
    public ConstraintException(String message) {
        super(message);
    }

    public ConstraintException(String message, Throwable cause) {
        super(message, cause);
    }
}
