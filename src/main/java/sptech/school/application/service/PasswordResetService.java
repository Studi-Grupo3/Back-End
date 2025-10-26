//package sptech.school.application.service;
//
//import org.springframework.stereotype.Service;
//import sptech.school.adapters.out.persistence.PasswordResetTokenRepository;
//import sptech.school.application.usecase.EmailSender;
//import sptech.school.domain.entity.PasswordResetToken;
//
//import java.time.LocalDateTime;
//
//@Service
//public class PasswordResetService {
//
//    private final PasswordResetTokenRepository tokenRepository;
//    private final EmailSender emailSender;
//
//    public PasswordResetService(PasswordResetTokenRepository tokenRepository, EmailSender emailSender) {
//        this.tokenRepository = tokenRepository;
//        this.emailSender = emailSender;
//    }
//
//    public boolean verifyCode(String email, String code) {
//        return tokenRepository.findByToken(code)
//                .filter(token -> token.getEmail().equals(email))
//                .filter(token -> token.getExpiresAt().isAfter(LocalDateTime.now()))
//                .isPresent();
//    }
//
//}
