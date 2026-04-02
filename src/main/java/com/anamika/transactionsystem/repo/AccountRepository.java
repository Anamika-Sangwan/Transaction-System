package com.anamika.transactionsystem.repo;

import com.anamika.transactionsystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
