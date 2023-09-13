package com.spektra.assessment.model.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {

    private int code;
    private String message;
    private T data;
    private long duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BaseError error;
}
