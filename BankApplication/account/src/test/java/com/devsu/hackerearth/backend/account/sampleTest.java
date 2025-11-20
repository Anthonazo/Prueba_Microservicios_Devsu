package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.account.controller.AccountController;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.service.AccountService;

@SpringBootTest
public class sampleTest {

	@Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

	private AccountService accountService = mock(AccountService.class);
	private AccountController accountController = new AccountController(accountService);

	@Test
	void createAccountTest() {
		// Arrange
		AccountDto newAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		AccountDto createdAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		when(accountService.create(newAccount)).thenReturn(createdAccount);

		// Act
		ResponseEntity<AccountDto> response = accountController.create(newAccount);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(createdAccount, response.getBody());
	}

    // TEST F6

	private Long mockClientId = 999L;

    @BeforeEach
    void setup() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void testCreateAccount() {
        Account account = new Account();
        account.setNumber("AC-001");
        account.setType("SAVINGS");
        account.setInitialAmount(500.0);
        account.setActive(true);
        account.setClientId(mockClientId);

        Account savedAccount = accountRepository.save(account);
        assertNotNull(savedAccount.getId());
        assertEquals(mockClientId, savedAccount.getClientId());
    }

    @Test
    void testCreateTransactionForAccount() {
        Account account = new Account();
        account.setNumber("AC-002");
        account.setType("CHECKING");
        account.setInitialAmount(1000.0);
        account.setActive(true);
        account.setClientId(mockClientId);

        Account savedAccount = accountRepository.save(account);

        Transaction tx = new Transaction();
        tx.setAccountId(savedAccount.getId());
        tx.setType("DEPOSIT");
        tx.setAmount(200.0);
        tx.setBalance(savedAccount.getInitialAmount() + 200.0);
        tx.setDate(new Date());

        Transaction savedTx = transactionRepository.save(tx);
        assertNotNull(savedTx.getId());
        assertEquals(savedAccount.getId(), savedTx.getAccountId());
        assertEquals(1200.0, savedTx.getBalance());
    }

}

