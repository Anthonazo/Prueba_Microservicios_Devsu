package com.devsu.hackerearth.backend.account.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.utils.mappers.AccountMapper;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccountDto> getAll() {
        List<AccountDto> entities = accountRepository.findAllBy(AccountDto.class);
        return entities;
    }

    @Override
    public AccountDto getById(Long id) {
        Account entity = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Valor no encontrado"));
        return accountMapper.accountEntitytoDto(entity);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        Account entity = accountRepository.save(accountMapper.accountDtoToEntity(accountDto));
        return accountMapper.accountEntitytoDto(entity);
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        Account entity = accountRepository.findById(accountDto.getId())
                .orElseThrow(() -> new RuntimeException("Valor no existe"));
        accountMapper.updateEntity(accountDto, entity);
        Account update = accountRepository.save(entity);
        return accountMapper.accountEntitytoDto(update);
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account entity = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Valor no encontrado"));
        entity.setActive(partialAccountDto.isActive());
        accountRepository.save(entity);
        return accountMapper.accountEntitytoDto(entity);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

}
