package com.korit.board.service;

import com.korit.board.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final JwtProvider jwtProvider;

    public Boolean sendAuthMail() {
        String toEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            helper.setSubject("스프링 부트 사용자 인증 메일 테스트");
            helper.setFrom("kedrd_1@naver.com");
            helper.setTo(toEmail);
            String token = jwtProvider.generateAuthMailToken(toEmail);

            mimeMessage.setText(
                    "<div>" +
                        "<h1>사용자 인증 메일</h1>" +
                        "<p>사용자 인증을 완료하려면 아래의 버튼을 클릭하세요.</p>" +
                        "<a href=\"http://localhost:8080/auth/mail?token=" + token + "\">인증하기</a>" +
                    "</div>", "utf-8", "html"
            );
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
