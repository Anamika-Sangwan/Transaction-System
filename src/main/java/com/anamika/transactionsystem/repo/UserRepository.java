package com.anamika.transactionsystem.repo;

import com.anamika.transactionsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
