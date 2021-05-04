package edu.miu.cs.neptune.repository;

import edu.miu.cs.neptune.domain.User;
import edu.miu.cs.neptune.domain.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {
    Optional<UserVerification> findByUserId(Long userId);
}
