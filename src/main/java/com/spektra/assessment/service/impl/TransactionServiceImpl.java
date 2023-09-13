package com.spektra.assessment.service.impl;

import com.spektra.assessment.exception.InvalidTransactionException;
import com.spektra.assessment.exception.TransactionNotFoundException;
import com.spektra.assessment.model.Transaction;
import com.spektra.assessment.model.dto.CreateTransactionDto;
import com.spektra.assessment.model.dto.EditTransactionDto;
import com.spektra.assessment.repository.TransactionRepository;
import com.spektra.assessment.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final TransactionRepository transactionRepository;
    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if(transaction == null) {
            log.error("Transaction with id {} not found", id);
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }
        return transaction;
    }

    @Override
    public Transaction createTransaction(CreateTransactionDto createTransactionDto) {

        if(createTransactionDto.getAmount() <= 0D) {
            throw new InvalidTransactionException("Amount must be greater than 0");
        }

        Transaction transaction = modelMapper.map(createTransactionDto, Transaction.class);
        transaction = transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    @Transactional
    public Transaction updateTransaction(Long id, EditTransactionDto editTransactionDto) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);

        if(transaction == null) {
            log.error("Transaction with id {} not found", id);
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }

        if (editTransactionDto.getSenderName() != null) {
            transaction.setSenderName(editTransactionDto.getSenderName());
        }

        if (editTransactionDto.getReceiverName() != null) {
            transaction.setReceiverName(editTransactionDto.getReceiverName());
        }

        if (editTransactionDto.getAmount() != 0D) {
            transaction.setAmount(editTransactionDto.getAmount());
        }

        transaction = transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);

        if(transaction == null) {
            log.error("Transaction with id {} not found", id);
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }
        transactionRepository.delete(transaction);
    }
}
