package com.example.common_lib.feign;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import java.util.Date;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.feign  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 5:06 PM
 */

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 503) {
            return new RetryableException(
                    response.status(),
                    "503 from server - retrying",
                    response.request().httpMethod(),
                    new Date(),
                    response.request());
        }
        return defaultDecoder.decode(methodKey, response);
    }
}
