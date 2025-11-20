package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    <T> List<T> findAllBy(Class<T> type);

    List<Transaction> findByAccountId(Long accountId);

    @Query("SELECT new com.devsu.hackerearth.backend.account.model.dto.BankStatementDto( " +
    "t.date, c.name, a.number, a.type, a.initialAmount, a.isActive, t.type, t.amount, t.balance) " +
    "FROM Transaction t " +
    "JOIN Account a ON t.accountId = a.id " +
    "JOIN ClientSnapshot c ON a.clientId = c.id " +
    "WHERE c.id = :clientId AND t.date BETWEEN :start AND :end")
    List<BankStatementDto> findAllByClientIdAndDateBetween(
        @Param("clientId") Long clientId,
        @Param("start") Date start,
        @Param("end") Date end
);


}
