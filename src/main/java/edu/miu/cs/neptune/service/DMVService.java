package edu.miu.cs.neptune.service;

import edu.miu.cs.neptune.domain.ProfileVerificationType;
import edu.miu.cs.neptune.domain.User;

public interface DMVService {
    ProfileVerificationType verifyProfile(User user);
}
