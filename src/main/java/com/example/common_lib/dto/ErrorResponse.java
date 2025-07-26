package com.example.common_lib.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.dto  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 4:47 PM
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private Instant timestamp;
    private String message;
    private String path;
    private int status;
    private String error;
}
