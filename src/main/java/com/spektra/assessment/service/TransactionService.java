package com.spektra.assessment.service;

import com.spektra.assessment.model.Transaction;
import com.spektra.assessment.model.dto.CreateTransactionDto;
import com.spektra.assessment.model.dto.EditTransactionDto;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    Transaction getTransaction(Long id);

    Transaction createTransaction(CreateTransactionDto transaction);

    Transaction updateTransaction(Long id, EditTransactionDto editTransactionDto);

    void deleteTransaction(Long id);

}
