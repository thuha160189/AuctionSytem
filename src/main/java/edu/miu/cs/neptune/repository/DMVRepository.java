package edu.miu.cs.neptune.repository;

import edu.miu.cs.neptune.domain.DMV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DMVRepository extends JpaRepository<DMV, Long> {

    @Query(value ="SELECT dmv FROM DMV dmv WHERE dmv.licenseNumber = :licenseNumber AND dmv.email = :email AND dmv.firstName = :firstName AND dmv.lastName = :lastName")
    Optional<DMV> findDMVByLicenseNumberAndEmailAndFirstNameAndLastName(String licenseNumber, String email,
                                                                        String firstName, String lastName);
}
