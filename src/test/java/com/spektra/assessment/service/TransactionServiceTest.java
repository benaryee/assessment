package com.spektra.assessment.service;

import com.spektra.assessment.exception.TransactionNotFoundException;
import com.spektra.assessment.model.Transaction;
import com.spektra.assessment.model.dto.CreateTransactionDto;
import com.spektra.assessment.model.dto.EditTransactionDto;
import com.spektra.assessment.repository.TransactionRepository;
import com.spektra.assessment.service.impl.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Slf4j
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateTransaction_Success() {
        CreateTransactionDto createTransactionDto = new CreateTransactionDto();
        createTransactionDto.setAmount(100D);
        createTransactionDto.setSenderName("Bernard");
        createTransactionDto.setReceiverName("Jackline");

        log.info("Create transaction: {}", createTransactionDto);
        Transaction transaction = modelMapper.map(createTransactionDto, Transaction.class);

        log.info("Transaction: {}", transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction createdTransaction = transactionService.createTransaction(createTransactionDto);

        log.info("Created transaction: {}", createdTransaction);
        assertNotNull(createdTransaction);
    }


    @Test
    public void testGetAllTransactions_Success() {
        List<Transaction> transactions = new ArrayList<>();
        when(transactionRepository.findAll()).thenReturn(transactions);
        List<Transaction> retrievedTransactions = transactionService.getAllTransactions();

        assertEquals(transactions, retrievedTransactions);
    }

    @Test
    public void testGetTransactionById_Success() {
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        Transaction retrievedTransaction = transactionService.getTransaction(transactionId);

        assertNotNull(retrievedTransaction);
    }

    @Test
    public void testGetTransactionById_NotFoundException() {
        // Arrange
        Long transactionId = 1L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.getTransaction(transactionId));
    }

    @Test
    public void testUpdateTransaction_Success() {
        // Arrange
        Long transactionId = 1L;
        EditTransactionDto editTransactionDto = new EditTransactionDto();
        editTransactionDto.setAmount(100D);

        Transaction existingTransaction = new Transaction();
        existingTransaction.setId(transactionId);
        existingTransaction.setAmount(200D);

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(existingTransaction));
        when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(existingTransaction);

        Transaction updatedTransaction = transactionService.updateTransaction(transactionId, editTransactionDto);

        assertEquals(editTransactionDto.getAmount(), updatedTransaction.getAmount());
    }

    @Test
    public void testUpdateTransaction_NotFoundException() {
        Long transactionId = 1L;
        EditTransactionDto editTransactionDto = new EditTransactionDto();
        editTransactionDto.setSenderName("Nii");

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());
        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.updateTransaction(transactionId, editTransactionDto));
    }

    @Test
    public void testDeleteTransaction_Success() {
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        doNothing().when(transactionRepository).delete(transaction);

        transactionService.deleteTransaction(transactionId);

        verify(transactionRepository).delete(transaction);
    }

    @Test
    public void testDeleteTransaction_NotFoundException() {
        // Arrange
        Long transactionId = 1L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.deleteTransaction(transactionId));
    }


}