package com.devsu.hackerearth.backend.account.utils.mappers;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

@Component
public class TransactionMapper {

    public Transaction transactionDtoToEntity(TransactionDto transactionDto) {
        if (transactionDto == null) {
            return null;
        }

        Transaction entity = new Transaction();
        entity.setDate(transactionDto.getDate());
        entity.setType(transactionDto.getType());
        entity.setAmount(transactionDto.getAmount());
        entity.setBalance(transactionDto.getBalance());
        entity.setAccountId(transactionDto.getAccountId());

        return entity;
    }

    public TransactionDto transactionEntitytoDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionDto transactionDto = new TransactionDto(
                transaction.getAccountId(), transaction.getDate(),
                transaction.getType(), transaction.getAmount(),
                transaction.getBalance(), transaction.getAccountId());

        return transactionDto;
    }
}
