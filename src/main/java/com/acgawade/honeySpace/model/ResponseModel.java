package com.acgawade.honeySpace.model;

import lombok.Data;

@Data
public class ResponseModel {
    private String status;
    private String message;
    private String exception;
}

