package com.cvt.lrucache.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private LocalDateTime time;
    private String message;
    private String type;
}
