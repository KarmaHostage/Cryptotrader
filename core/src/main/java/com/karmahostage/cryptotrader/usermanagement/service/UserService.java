package com.karmahostage.cryptotrader.usermanagement.service;

import com.karmahostage.cryptotrader.email.service.MailService;
import com.karmahostage.cryptotrader.infrastructure.exception.CryptoTraderException;
import com.karmahostage.cryptotrader.usermanagement.domain.CryptotraderUser;
import com.karmahostage.cryptotrader.usermanagement.domain.Role;
import com.karmahostage.cryptotrader.usermanagement.domain.RoleEnum;
import com.karmahostage.cryptotrader.usermanagement.repository.RoleRepository;
import com.karmahostage.cryptotrader.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MailService mailService;

    @Value("${com.karmahostage.cryptotrader.fullbaseurl}")
    private String fullbaseUrl;


    private PasswordEncoder standardPasswordEncoder;
    private PasswordEncoder activationEncoder;

    public UserService() {
    }

    @PostConstruct
    public void init() {
        this.standardPasswordEncoder = new BCryptPasswordEncoder();
        this.activationEncoder = new StandardPasswordEncoder();
    }

    @Transactional
    public void enable(Long userId) {
        userRepository.findOne(userId)
                .ifPresent(user -> userRepository.save(user.setEnabled(true)));
    }

    @Transactional
    public void disable(Long userId) {
        userRepository.findOne(userId)
                .ifPresent(user -> userRepository.save(user.setEnabled(false)));
    }

    public Optional<CryptotraderUser> findOne(long id) {
        return userRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<CryptotraderUser> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean isValidPassword(String password, CryptotraderUser user) {
        Optional<CryptotraderUser> one = userRepository.findOne(user.getId());
        return one.isPresent() && standardPasswordEncoder.matches(password, one.get().getPassword());
    }

    @Transactional
    public CryptotraderUser registerByInvitation(String email) {
        Optional<CryptotraderUser> alreadyInDatabase = userRepository.findByEmail(email);
        if (alreadyInDatabase.isPresent()) {
            throw new CryptoTraderException("That user is already registered. Unable to register by invitation");
        } else {
            String activationCode = createSecureCode();
            return userRepository.save(
                    new CryptotraderUser()
                            .setAuthorities(getDefaultAuthorities())
                            .setEmail(email)
                            .setPassword(null)
                            .setActivationCode(activationCode)
                            .setUsername(email)
                            .setEnabled(false)
            );
        }
    }

    @Transactional
    public void register(String email, String password) {
        Optional<CryptotraderUser> alreadyInDatabase = userRepository.findByEmail(email);
        if (alreadyInDatabase.isPresent()) {
            StringBuilder bodyBuilder = new StringBuilder("Hi!\n\n");
            bodyBuilder.append("It would appear you tried to register this email, but you were already in our system.\n");
            if (alreadyInDatabase.get().isEnabled()) {
                bodyBuilder.append("Please login at ");
                bodyBuilder.append(fullbaseUrl + "/login");
            } else {
                bodyBuilder.append("However, it would appear your account is not yet activated.\n");
                bodyBuilder.append("Please visit ");
                bodyBuilder.append(fullbaseUrl + "/activation/" + alreadyInDatabase.get().getActivationCode());
            }
            bodyBuilder.append(" to continue.");
            bodyBuilder.append("\n\nKind Regards\nThe Karmahostage Team");
            mailService
                    .createMail()
                    .to(alreadyInDatabase.get().getEmail())
                    .subject("Karmahostage | Account Registration")
                    .body(bodyBuilder.toString())
                    .send();
        } else {
            String activationCode = createSecureCode();
            CryptotraderUser newUser = userRepository.save(
                    new CryptotraderUser()
                            .setAuthorities(getDefaultAuthorities())
                            .setEmail(email)
                            .setPassword(standardPasswordEncoder.encode(password))
                            .setActivationCode(activationCode)
                            .setUsername(email)
                            .setEnabled(false)
            );
            StringBuilder bodyBuilder = new StringBuilder("Hi!\n\n" +
                    "Thank you for registering an account on Karmahostage.\n" +
                    "Please navigate to ");
            bodyBuilder.append(fullbaseUrl + "/activation/" + newUser.getActivationCode());
            bodyBuilder.append(" to activate your account.\n" +
                    "You'll be able to log in once your account has been activated.");
            bodyBuilder.append("\n\nKind Regards\nThe Karmahostage Team");
            mailService
                    .createMail()
                    .to(newUser.getEmail())
                    .subject("Karmahostage | Account Registration")
                    .body(bodyBuilder.toString())
                    .send();
        }
    }

    private String createSecureCode() {
        return encode(String.valueOf(System.currentTimeMillis()) + getRandomlyGeneratedUUID()).substring(0, 39);
    }

    @Transactional
    public void activate(String activationCode) {
        Optional<CryptotraderUser> byActivationCode = userRepository.findByActivationCode(activationCode);
        if (byActivationCode.isPresent()) {
            CryptotraderUser save = userRepository.save(
                    byActivationCode.get()
                            .setEnabled(true)
                            .setActivationCode(null)
            );
        }
    }

    @Transactional(readOnly = true)
    public Optional<CryptotraderUser> findByActivationCode(String activationCode) {
        return userRepository.findByActivationCode(activationCode);
    }

    @Transactional
    public void forgotPassword(String email) {
        Optional<CryptotraderUser> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            CryptotraderUser cryptotraderUser = byEmail.get();
            String passwordResetCode = createSecureCode();
            cryptotraderUser.setPasswordResetCode(passwordResetCode);
            userRepository.save(cryptotraderUser);
            StringBuilder bodyBuilder = new StringBuilder("Hi!\n\n")
                    .append("You (or someone else) just requested a password reset.\n")
                    .append("Please visit ")
                    .append(fullbaseUrl)
                    .append("/reset-password/")
                    .append(passwordResetCode)
                    .append(" to reset your password.\n\n")
                    .append("If this request was not made on your behalf, you can just ignore this email.\n\n")
                    .append("\n\nKind Regards\nThe Karmahostage Team");
            mailService.createMail()
                    .body(bodyBuilder.toString())
                    .subject("Karmahostage's CryptoTrader | Password Reset")
                    .to(byEmail.get().getEmail())
                    .send();
        }
    }

    private Set<Role> getDefaultAuthorities() {
        Set<Role> defaultRoles = new HashSet<>();
        defaultRoles.add(roleRepository.findOne(RoleEnum.USER_ROLE.getId()).get());
        return defaultRoles;
    }


    private String encode(String toEncode) {
        return activationEncoder.encode(toEncode);
    }

    private String getRandomlyGeneratedUUID() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public void changePassword(CryptotraderUser cryptotraderUser, String oldPassword, String password) {
        Optional<CryptotraderUser> one = userRepository.findOne(cryptotraderUser.getId());
        if (one.isPresent()) {
            CryptotraderUser fetchedUser = one.get();
            if (standardPasswordEncoder.matches(oldPassword, fetchedUser.getPassword())) {
                fetchedUser.setPassword(standardPasswordEncoder.encode(password));
                userRepository.save(fetchedUser);
            } else {
                throw new CryptoTraderException("Your current password was incorrect");
            }
        }
    }

    @Transactional
    public void resetPassword(String passwordResetCode,
                              String password) {
        Optional<CryptotraderUser> byPasswordResetCode = userRepository.findByPasswordResetCode(passwordResetCode);
        if (byPasswordResetCode.isPresent()) {
            CryptotraderUser cryptotraderUser = byPasswordResetCode.get();
            cryptotraderUser
                    .setPassword(standardPasswordEncoder.encode(password))
                    .setPasswordResetCode(null);
            userRepository.save(cryptotraderUser);
        }
    }

    public Optional<CryptotraderUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void setPasswordForActivationCode(String activationCode, String email, String password) {
        Optional<CryptotraderUser> byActivationCode = userRepository.findByActivationCode(activationCode);
        if (byActivationCode.isPresent()) {
            if (!byActivationCode.get().getEmail().equals(email)) {
                throw new CryptoTraderException("That email is not associated with this activation code");
            } else {
                userRepository.save(
                        byActivationCode.get()
                                .setPassword(standardPasswordEncoder.encode(password))
                );
            }
        }
    }
}
