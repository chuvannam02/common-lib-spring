package com.example.common_lib.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.response  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 5:02 PM
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"err_code", "message"})
public class BaseResponse {

    @Setter
    @JsonProperty("err_code")
    private String err_code;

    @JsonProperty("message")
    private String message;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public BaseResponse() {
        this.err_code = "0";
        this.message = "SUCCESS";
    }

    public BaseResponse(String err_code, String message) {
        this.err_code = err_code;
        this.message = message;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void setAdditionalProperty(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
