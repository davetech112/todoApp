package com.zeero.zeero.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponse<T> {
    private String message;
    private boolean hasError;
    private int responseCode;
    private T data;

    public GenericResponse(T data){
        this.message = "success";
        this.responseCode = 200;
        this.data = data;
    }
}
