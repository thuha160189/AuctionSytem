package edu.miu.cs.neptune.service;

import edu.miu.cs.neptune.domain.UserVerification;

import java.util.Optional;

public interface UserVerificationService {
  UserVerification save(UserVerification userVerification);
  Optional<UserVerification> getByUserId(Long userId);
}
