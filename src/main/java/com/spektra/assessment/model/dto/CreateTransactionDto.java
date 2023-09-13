package com.spektra.assessment.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class CreateTransactionDto implements Serializable {

    @NotEmpty
    private String senderName;

    @NotEmpty
    private String receiverName;

    @NotEmpty
    private double amount;

}
