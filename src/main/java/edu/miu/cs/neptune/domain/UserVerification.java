package edu.miu.cs.neptune.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class UserVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationId;
    private String verificationCode;
    private LocalDateTime endingTime;
    private Integer failedVerificationCount;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private  UserVerificationType userVerificationType = UserVerificationType.NEED_TO_VERIFY;

    public String getVerificationCode() {
        return verificationCode;
    }

    public UserVerification setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }

    public LocalDateTime getEndingTime() {
        return endingTime;
    }

    public UserVerification setEndingTime(LocalDateTime endingTime) {
        this.endingTime = endingTime;
        return this;
    }

    public Integer getFailedVerificationCount() {
        return failedVerificationCount;
    }

    public UserVerification setFailedVerificationCount(Integer failedVerificationCount) {
        this.failedVerificationCount = failedVerificationCount;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public UserVerification setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public UserVerificationType getUserVerificationType() {
        return userVerificationType;
    }

    public UserVerification setUserVerificationType(UserVerificationType userVerificationType) {
        this.userVerificationType = userVerificationType;
        return this;
    }

    public Long getVerificationId() {
        return verificationId;
    }

    public UserVerification setVerificationId(Long verificationId) {
        this.verificationId = verificationId;
        return this;
    }
}
