package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.exception.HandleBalanceException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.ClientSnapshot;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.ClientSnapshotRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.utils.mappers.TransactionMapper;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;
    private final ClientSnapshotRepository clientSnapshotRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper,
            AccountRepository accountRepository,
            ClientSnapshotRepository clientSnapshotRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountRepository = accountRepository;
        this.clientSnapshotRepository = clientSnapshotRepository;
    }

    @Override
    public List<TransactionDto> getAll() {
        List<TransactionDto> entities = transactionRepository.findAllBy(TransactionDto.class);
        return entities;
    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction entity = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Valor no encontrado"));
        return transactionMapper.transactionEntitytoDto(entity);
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        Account entityAccount = accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Valor no encontrado"));

        double total = entityAccount.getInitialAmount() + transactionRepository.findByAccountId(entityAccount.getId())
                .stream().mapToDouble(t -> t.getAmount()).sum() + transactionDto.getAmount();

        if (total < 0) {
            throw new HandleBalanceException();
        }

        Transaction entity = transactionMapper.transactionDtoToEntity(transactionDto);
        entity.setBalance(total);
        entity.setDate(new Date());
        transactionRepository.save(entity);
        return transactionMapper.transactionEntitytoDto(entity);
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        List<BankStatementDto> report = transactionRepository.findAllByClientIdAndDateBetween(clientId,
                dateTransactionStart, dateTransactionEnd);
        return report;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        return null;
    }

}
