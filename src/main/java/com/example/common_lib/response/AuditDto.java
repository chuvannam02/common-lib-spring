package com.example.common_lib.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.response  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 5:03 PM
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuditDto {

    private String createdBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Instant createdDate;

    private String lastModifiedBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Instant lastModifiedDate;
}
