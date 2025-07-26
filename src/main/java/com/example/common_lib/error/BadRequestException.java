package com.example.common_lib.error;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.error  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 4:56 PM
 */

public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }
}
