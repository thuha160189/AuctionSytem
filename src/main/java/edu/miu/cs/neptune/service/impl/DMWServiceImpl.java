package edu.miu.cs.neptune.service.impl;

import edu.miu.cs.neptune.domain.DMV;
import edu.miu.cs.neptune.domain.ProfileVerificationType;
import edu.miu.cs.neptune.domain.User;
import edu.miu.cs.neptune.repository.DMVRepository;
import edu.miu.cs.neptune.service.DMVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DMWServiceImpl implements DMVService {
    @Autowired
    DMVRepository dmvRepository;

    @Override
    public ProfileVerificationType verifyProfile(User user) {
        Optional<DMV> dmvOptional = dmvRepository.findDMVByLicenseNumberAndEmailAndFirstNameAndLastName(user.getLicenseNumber(),
                                        user.getEmail(), user.getFirstName(), user.getLastName());

        if(dmvOptional.isPresent()) {
            return ProfileVerificationType.VERIFIED;
        }

        return ProfileVerificationType.FAILED;
    }
}
