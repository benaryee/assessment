package com.spektra.assessment.controller;

import com.spektra.assessment.exception.ServiceException;
import com.spektra.assessment.model.Transaction;
import com.spektra.assessment.model.domain.ApiResponse;
import com.spektra.assessment.model.dto.CreateTransactionDto;
import com.spektra.assessment.model.dto.EditTransactionDto;
import com.spektra.assessment.model.dto.TransactionDto;
import com.spektra.assessment.service.TransactionService;
import com.spektra.assessment.util.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.spektra.assessment.model.enums.ServiceError.FAILED_TO_CREATE_TRANSACTION;

@RestController
@RequestMapping("/api/transactions")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Get all transactions
     * @param servletRequest
     * @return
     */

    @GetMapping
    public ApiResponse<?> getAllTransactions(HttpServletRequest servletRequest) {
        String sessionId = servletRequest.getSession().getId();
        log.info("[{}] HTTP Request : Get all transactions", sessionId);

        List<Transaction> transactions = transactionService.getAllTransactions();

        if(transactions == null) {
            transactions = new ArrayList<>();
        }

        List<TransactionDto> transactionDtos = transactions
                .stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class)).toList();

        ApiResponse<List<TransactionDto>> apiResponse = ApiUtils.buildApiResponse(transactionDtos);
        log.info("[{}] Http Response : Get all transactions: {}", sessionId, transactionDtos);

        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getTransaction(@PathVariable Long id, HttpServletRequest servletRequest) {
        String sessionId = servletRequest.getSession().getId();
        log.info("[{}] HTTP Request : Get Transaction with id {}", sessionId, id);

        Transaction transaction = transactionService.getTransaction(id);
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        ApiResponse<TransactionDto> apiResponse = ApiUtils.buildApiResponse(transactionDto);

        log.info("[{}] HTTP Response : Get Transaction with id {}: {}", sessionId, id, apiResponse);

        return apiResponse;
    }

    @PostMapping
    public ApiResponse<?> createTransaction(@RequestBody CreateTransactionDto createTransactionDto, HttpServletRequest servletRequest) {
        String sessionId = servletRequest.getSession().getId();
        log.info("[{}] HTTP Request : Create Transaction {}", sessionId, createTransactionDto);

        Transaction transaction = transactionService.createTransaction(createTransactionDto);

        if(transaction == null) {
            throw new ServiceException(FAILED_TO_CREATE_TRANSACTION);
        }

        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        ApiResponse<TransactionDto> apiResponse = ApiUtils.buildApiResponse(transactionDto);
        log.info("[{}] HTTP Response : Create Transaction {}", sessionId, apiResponse);
        return apiResponse;

    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateTransaction(@PathVariable Long id, @RequestBody EditTransactionDto editTransactionDto,
                                            HttpServletRequest servletRequest) {
        String sessionId = servletRequest.getSession().getId();
        log.info("[{}] HTTP Request : Update Transaction with id {}", sessionId, id);

        Transaction transaction = transactionService.updateTransaction(id, editTransactionDto);
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        ApiResponse<TransactionDto> apiResponse = ApiUtils.buildApiResponse(transactionDto);

        log.info("[{}] HTTP Response : Update Transaction with id {}: {}", sessionId, id, apiResponse);

        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteTransaction(@PathVariable Long id, HttpServletRequest servletRequest) {
        String sessionId = servletRequest.getSession().getId();
        log.info("[{}] HTTP Request : Delete Transaction with id {}", sessionId, id);

        transactionService.deleteTransaction(id);
        return ApiUtils.buildApiResponse("Transaction deleted successfully");
    }

}
