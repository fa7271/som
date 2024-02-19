package com.encore.admin.service;

import com.encore.admin.config.RedisUtil;
import com.encore.admin.domain.Member;
import com.encore.admin.dto.SignInRequest;
import com.encore.admin.dto.SignUpRequest;
import com.encore.admin.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;

import com.encore.common.support.ResponseCode;
import com.encore.common.support.Role;
import com.encore.common.support.SomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Transactional
@Service
public class AccountService {

    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String configEmail;

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AccountService(JavaMailSender mailSender, RedisUtil redisUtil, MemberRepository repository, PasswordEncoder passwordEncoder) {
        this.mailSender = mailSender;
        this.redisUtil = redisUtil;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;

    }

    public void signup(SignUpRequest signUpRequest) throws MessagingException {

        Optional<Member> findMember = repository.findByEmail(signUpRequest.getEmail());
        if(findMember.isPresent()) {
            throw new SomException(ResponseCode.EXISTING_RESOURCE);
        }

        log.debug("sign up {}",signUpRequest.getEmail());
        if (redisUtil.existData(signUpRequest.getEmail())) {
            redisUtil.deleteData(signUpRequest.getEmail());
        }

        sendEmail(signUpRequest.getEmail());

        Member member = Member.builder()
                .role(Role.USER)
                .email(signUpRequest.getEmail())
                .nickname(signUpRequest.getNickname())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .ranking(0L)
                .active(false)
                .build();

        repository.save(member);
    }


    public Member signin(SignInRequest signInRequest){


        Member member = repository.findByEmail(signInRequest.getEmail()).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 이메일입니다."));

        log.debug("member email {}",member.getEmail());;
        if(!passwordEncoder.matches(signInRequest.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        return member;
    }

    public Boolean verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        System.out.println(codeFoundByEmail);
        if (codeFoundByEmail == null) {
            throw new SomException(ResponseCode.CODE_EXPIRED);
        }
        if(codeFoundByEmail.equals(code)){
            Optional<Member> findMember = repository.findByEmail(email);
            if(findMember.isPresent()) {
                findMember.get().inactive();
            }
        }
        return true;
    }

    public void sendEmail(String toEmail) throws MessagingException {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }

        MimeMessage emailForm = createEmailForm(toEmail);

        mailSender.send(emailForm);
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {

        UUID uuid = UUID.randomUUID();
        String LINK = "http://localhost:8000/admin/account/verify-code/"+email+"/"+uuid.toString();

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("안녕하세요 인증번호입니다.");
        message.setFrom(configEmail);
        message.setText("아래 링크를 눌러 인증을 완료하세요.");
        message.setText(LINK);

        redisUtil.setDataExpire(email, uuid.toString(), 60 * 30L);

        return message;
    }



}
