package com.zeero.zeero.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private int errorCode;
    private String message;

    public ErrorDto(TodoAppException exception){
        this.errorCode = exception.getErrorCode();
        this.message = exception.getMessage();
    }

}
