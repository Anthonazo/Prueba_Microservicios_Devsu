package com.devsu.hackerearth.backend.account.utils.mappers;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;

@Component
public class AccountMapper {

    public Account accountDtoToEntity(AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }

        Account entity = new Account();
        entity.setNumber(accountDto.getNumber());
        entity.setType(accountDto.getType());
        entity.setInitialAmount(accountDto.getInitialAmount());
        entity.setActive(accountDto.isActive());
        entity.setClientId(accountDto.getClientId());

        return entity;
    }

    public void updateEntity(AccountDto accountDto, Account entity) {
        entity.setNumber(accountDto.getNumber());
        entity.setType(accountDto.getType());
        entity.setInitialAmount(accountDto.getInitialAmount());
        entity.setActive(accountDto.isActive());
        entity.setClientId(accountDto.getClientId());
    }

    public AccountDto accountEntitytoDto(Account account) {
        if (account == null) {
            return null;
        }

        AccountDto accountDto = new AccountDto(
                account.getId(), account.getNumber(),
                account.getType(), account.getInitialAmount(),
                account.isActive(), account.getClientId());

        return accountDto;
    }
}
