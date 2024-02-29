package com.encore.admin.service;

import com.encore.admin.config.RedisUtil;
import com.encore.admin.domain.Member;
import com.encore.admin.dto.SignInRequest;
import com.encore.admin.dto.SignUpRequest;
import com.encore.admin.dto.VertifyCodeDtoReq;
import com.encore.admin.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;

import com.encore.admin.common.support.ResponseCode;
import com.encore.admin.common.support.Role;
import com.encore.admin.common.support.SomException;
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

        Member member = Member.builder()
                .role(Role.USER)
                .email(signUpRequest.getEmail())
                .nickname(signUpRequest.getNickname())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .ranking(0L)
                .point(0L)
                .active(false)
                .build();

        Member newMember = repository.save(member);
        String redisMemberKey = String.valueOf(newMember.getId());
        if (redisUtil.existData(redisMemberKey)) {
            redisUtil.deleteData(redisMemberKey);
        }

        sendEmail(signUpRequest.getEmail(), redisMemberKey);


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


        Member findMember = repository.findByEmail(email).orElseThrow(() -> new SomException(ResponseCode.USER_NOT_FOUND));
        String codeFoundByid = redisUtil.getData(findMember.getId().toString());

        if (codeFoundByid == null) {
            throw new SomException(ResponseCode.CODE_EXPIRED);
        }
        if(codeFoundByid.equals(code)){
            findMember.active();
        }
        return true;
    }

    public void sendEmail(String toEmail, String redisMemberKey) throws MessagingException {

        MimeMessage emailForm = createEmailForm(toEmail, redisMemberKey);

        mailSender.send(emailForm);
    }

    private MimeMessage createEmailForm(String email, String redisMemberKey) throws MessagingException {

        UUID uuid = UUID.randomUUID();
        String LINK = "http://localhost:8000/admin/account/verify-code/"+email+"/"+uuid.toString();

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("The Sound Of Mind 가입 인증");
        message.setFrom(configEmail);
        message.setContent(
                "<h1 style=\"color: #5e9ca0;\">The Sound Of Mind 회원가입 인증</h1><br>"
                        + "<p>아래 링크를 클릭하면 인증이 완료됩니다.:</p>"
                        + "<p><strong>" + LINK + "</strong></p>",
                "text/html; charset=utf-8"
        );

        redisUtil.setDataExpire(redisMemberKey, uuid.toString(), 60 * 30L);

        return message;
    }

    private MimeMessage createEmailFormForPassword(String email, String redisMemberKey) throws MessagingException {

        UUID uuid = UUID.randomUUID();
//        String LINK = "http://localhost:8000/admin/account/password/verify-code/"+email+"/"+uuid.toString();

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("The Sound Of Mind 패스워드 변경 인증");
        message.setFrom(configEmail);

        message.setContent(
                "<h1 style=\"color: #5e9ca0;\">The Sound Of Mind 패스워드 변경 인증</h1><br>"
                        + "<p>아래 인증번호를 Som 홈페이지 입력란에 입력해주세요:</p>"
                        + "<p><strong>" + uuid.toString() + "</strong></p>",
                "text/html; charset=utf-8"
        );

        redisUtil.setDataExpire(redisMemberKey, uuid.toString(), 60 * 30L);

        return message;
    }

    public void findPassword(String email) throws MessagingException {

        Member findMember = repository.findByEmail(email).orElseThrow(() -> new SomException(ResponseCode.USER_NOT_FOUND));
        String redisMemberKey = findMember.getId().toString();

        if (redisUtil.existData(redisMemberKey)) {
            redisUtil.deleteData(redisMemberKey);
        }

        MimeMessage emailForm = createEmailFormForPassword(email,redisMemberKey);

        mailSender.send(emailForm);
    }

    public Boolean verifyEmailCodeForPassword(String userId, String code) {

        String codeFoundByUserId = redisUtil.getData(userId);
        if (codeFoundByUserId == null) {
            throw new SomException(ResponseCode.CODE_EXPIRED);
        }
        if(codeFoundByUserId.equals(code)){
            Optional<Member> findMember = repository.findById(Long.valueOf(userId));
            if(findMember.isPresent()) {
                findMember.get().active();
            }
        }
        return true;
    }

    public void sendEmailForPassword(String toEmail, Long userId) throws MessagingException {
        if (redisUtil.existData(userId.toString())) {
            redisUtil.deleteData(userId.toString());
        }
//        MimeMessage emailForm = createEmailForm(toEmail);

//        mailSender.send(emailForm);
    }

    public void vertifyCode(VertifyCodeDtoReq vertifyCodeDtoReq) {
//        1. check signup email
        String email = vertifyCodeDtoReq.getEmail();
        String password = passwordEncoder.encode(vertifyCodeDtoReq.getPassword());
        String code = vertifyCodeDtoReq.getCode();
        Member member = repository.findByEmail(email).orElseThrow(() -> new SomException(ResponseCode.USER_NOT_FOUND));

        System.out.println("code = " + code);
        System.out.println("redisUtil.getData(member.getId().toString()) = " + redisUtil.getData(member.getId().toString()));
//        2. check code
        if (!redisUtil.getData(member.getId().toString()).equals(code)) {
            throw new SomException(ResponseCode.CODE_NOT_CONFIRMED);
        }

        member.changePassword(password);
        redisUtil.deleteData(member.getId().toString());
    }
}