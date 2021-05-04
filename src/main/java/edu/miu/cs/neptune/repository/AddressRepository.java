package edu.miu.cs.neptune.repository;

import edu.miu.cs.neptune.domain.Address;
import edu.miu.cs.neptune.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
