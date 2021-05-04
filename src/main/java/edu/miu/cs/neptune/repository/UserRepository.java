package edu.miu.cs.neptune.repository;

import edu.miu.cs.neptune.domain.ProfileVerificationType;
import edu.miu.cs.neptune.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

//  @Query(value = "SELECT u FROM User u WHERE u.profileVerificationType = 1")
    @Query(value = "SELECT u FROM User u")
    List<User> findAllPendingProfile();

//    void updateVerification(ProfileVerificationType profileVerificationType);
}
