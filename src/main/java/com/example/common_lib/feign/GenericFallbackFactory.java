package com.example.common_lib.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.feign  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 5:06 PM
 */

@Slf4j
public class GenericFallbackFactory<T> implements FallbackFactory<T> {

    private final Class<T> type;

    public GenericFallbackFactory(Class<T> type) {
        this.type = type;
    }

    @Override
    public T create(Throwable cause) {
        return (T) Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class[]{type},
                (proxy, method, args) -> {
                    log.warn("Fallback for method: {} in {}", method.getName(), type.getSimpleName(), cause);

                    Class<?> returnType = method.getReturnType();
                    if (returnType.equals(String.class)) return "Fallback triggered";
                    if (returnType.equals(Void.TYPE)) return null;
                    return null; // default for other types
                }
        );
    }
}
