package com.www.demo.app.itsmdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.www.demo.app.itsmdemo.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByAccountNumber(String accountNumber);

	Optional<Account> findByUpiId(String upiId);

	Optional<Account> findByNetbankingUserId(String netbankingUserId);

	Optional<Account> findByCardNumber(String cardNumber);
	
	Account findByAccountId(Long id);
	
//	Account findByAccountNumber(String accountNumber);
}
