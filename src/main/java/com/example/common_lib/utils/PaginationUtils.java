package com.example.common_lib.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.utils  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 4:57 PM
 */

public class PaginationUtils {
    public static Pageable resolvePageable(Integer page, Integer size) {
        if (page == null || page <= 0 || size == null || size <= 0) {
            return Pageable.unpaged();
        }
        return PageRequest.of(page - 1, size);
    }

    public static Pageable resolvePageable(Integer page, Integer size, String sort) {
        if (page == null || page <= 0 || size == null || size <= 0) {
            return Pageable.unpaged();
        }
        if (sort == null || sort.isEmpty()) {
            return PageRequest.of(page - 1, size);
        }
        String[] sortParams = sort.split(",");
        return PageRequest.of(page - 1, size, org.springframework.data.domain.Sort.by(sortParams));
    }

    public static Pageable resolvePageable(Integer page, Integer size, String sort, String direction) {
        if (page == null || page <= 0 || size == null || size <= 0) {
            return Pageable.unpaged();
        }
        if (sort == null || sort.isEmpty()) {
            return PageRequest.of(page - 1, size);
        }
        org.springframework.data.domain.Sort.Direction dir = "desc".equalsIgnoreCase(direction) ?
                org.springframework.data.domain.Sort.Direction.DESC : org.springframework.data.domain.Sort.Direction.ASC;
        return PageRequest.of(page - 1, size, org.springframework.data.domain.Sort.by(dir, sort));
    }

    public static Pageable resolvePageable(Integer page, Integer size, String[] sort) {
        if (page == null || page <= 0 || size == null || size <= 0) {
            return Pageable.unpaged();
        }
        if (sort == null || sort.length == 0) {
            return PageRequest.of(page - 1, size);
        }
        return PageRequest.of(page - 1, size, org.springframework.data.domain.Sort.by(sort));
    }

    public static Pageable resolvePageable(Integer page, Integer size, String[] sort, String direction) {
        if (page == null || page <= 0 || size == null || size <= 0) {
            return Pageable.unpaged();
        }
        if (sort == null || sort.length == 0) {
            return PageRequest.of(page - 1, size);
        }
        org.springframework.data.domain.Sort.Direction dir = "desc".equalsIgnoreCase(direction) ?
                org.springframework.data.domain.Sort.Direction.DESC : org.springframework.data.domain.Sort.Direction.ASC;
        return PageRequest.of(page - 1, size, org.springframework.data.domain.Sort.by(dir, sort));
    }

    public static Pageable resolvePageable(Integer page, Integer size, String sort, String direction, String[] additionalSort) {
        if (page == null || page <= 0 || size == null || size <= 0) {
            return Pageable.unpaged();
        }
        if (sort == null || sort.isEmpty()) {
            return PageRequest.of(page - 1, size);
        }
        org.springframework.data.domain.Sort.Direction dir = "desc".equalsIgnoreCase(direction) ?
                org.springframework.data.domain.Sort.Direction.DESC : org.springframework.data.domain.Sort.Direction.ASC;
        org.springframework.data.domain.Sort sortObj = org.springframework.data.domain.Sort.by(dir, sort);
        if (additionalSort != null && additionalSort.length > 0) {
            for (String s : additionalSort) {
                sortObj = sortObj.and(org.springframework.data.domain.Sort.by(s));
            }
        }
        return PageRequest.of(page - 1, size, sortObj);
    }
}
