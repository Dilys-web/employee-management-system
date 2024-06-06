package com.employee.Employee.Management.System.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializePaymentResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Map<String, Object> data;


//
//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class Data{
//
//        @JsonProperty("authorization_url")
//        private String authorizationUrl;
//
//        @JsonProperty("access_code")
//        private String accessCode;
//
//        @JsonProperty("reference")
//        private String reference;
//    }




}