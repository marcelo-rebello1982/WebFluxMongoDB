package com.api.web.response;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppErrorResponse {

    private Long timestamp;

    private String path;

    private Integer status;

    private String error;

    private String message;

    private String requestId;

}