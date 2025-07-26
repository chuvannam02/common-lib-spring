package com.example.common_lib.dto;

import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.dto  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 5:01 PM
 */

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ScheduleIN {
    String period;
    String frequency;
    Long minute;
    String time;
    String day;
}
