package com.spektra.assessment.model.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Date;

@Data
public class TransactionDto {

    private Long id;
    private String senderName;
    private String receiverName;
    private double amount;
    private Date transactionDate;
}
