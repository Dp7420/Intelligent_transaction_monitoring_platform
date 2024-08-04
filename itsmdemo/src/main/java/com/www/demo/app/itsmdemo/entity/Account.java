package com.www.demo.app.itsmdemo.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    private String email;
    private String mobile;
    private Double balance;
    private String upiId;
    private String upiPassword;
    private String netbankingUserId;
    private String netbankingPassword;
    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;
    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked = false;

    @Column(nullable = true)
    private Integer failedAttempts;

    @Column(nullable = true)
    private LocalDateTime lockoutTime;

    private int otpFailedAttemptsNetbanking = 0;
    private int otpFailedAttemptsCard = 0;
    private boolean isNetbankingBlocked = false;
    private boolean isCardBlocked = false;
    private Date netbankingBlockTimestamp;
    private Date cardBlockTimestamp;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> transactions;

    // Utility Methods
    public void incrementFailedAttempts() {
        this.failedAttempts = (this.failedAttempts == null ? 1 : this.failedAttempts + 1);
    }

    public void resetFailedAttempts() {
        this.failedAttempts = 0;
    }

    public boolean isLocked() {
        return lockoutTime != null && LocalDateTime.now().isBefore(lockoutTime);
    }

    public void lockAccount() {
        this.lockoutTime = LocalDateTime.now().plusMinutes(10);
    }

    public void unlockAccount() {
        this.lockoutTime = null;
    }

    public void incrementOtpFailedAttemptsNetbanking() {
        this.otpFailedAttemptsNetbanking++;
        if (this.otpFailedAttemptsNetbanking >= 3) {
            this.isNetbankingBlocked = true;
            this.netbankingBlockTimestamp = new Date();
        }
    }

    public void resetOtpFailedAttemptsNetbanking() {
        this.otpFailedAttemptsNetbanking = 0;
        this.isNetbankingBlocked = false;
        this.netbankingBlockTimestamp = null;
    }

    public void incrementOtpFailedAttemptsCard() {
        this.otpFailedAttemptsCard++;
        if (this.otpFailedAttemptsCard >= 3) {
            this.isCardBlocked = true;
            this.cardBlockTimestamp = new Date();
        }
    }

    public void resetOtpFailedAttemptsCard() {
        this.otpFailedAttemptsCard = 0;
        this.isCardBlocked = false;
        this.cardBlockTimestamp = null;
    }

    public boolean isPaymentOptionBlocked(String paymentOption) {
        if (paymentOption.equals("Netbanking") && this.isNetbankingBlocked) {
            long currentTime = new Date().getTime();
            long blockTime = this.netbankingBlockTimestamp != null ? this.netbankingBlockTimestamp.getTime() : 0;
            if ((currentTime - blockTime) < 5 * 60 * 1000) {  // 5 minutes
                return true;
            } else {
                resetOtpFailedAttemptsNetbanking();  // Unblock if time passed
            }
        }
        if (paymentOption.equals("Card") && this.isCardBlocked) {
            long currentTime = new Date().getTime();
            long blockTime = this.cardBlockTimestamp != null ? this.cardBlockTimestamp.getTime() : 0;
            if ((currentTime - blockTime) < 5 * 60 * 1000) {  // 5 minutes
                return true;
            } else {
                resetOtpFailedAttemptsCard();  // Unblock if time passed
            }
        }
        return false;
    }
}
