package com.www.demo.app.itsmdemo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.www.demo.app.itsmdemo.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	Optional<Transaction> findByTransactionId(String transactionId);
	
	@Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND t.timestamp > :timestamp ORDER BY t.timestamp DESC")
    List<Transaction> findRecentTransactions(@Param("accountId") Long accountId, @Param("timestamp") Date timestamp);
}
