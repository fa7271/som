package com.encore.admin.common.support;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ResponseCode {

    SUCCESS("SUCCESS", "성공"),
    SUCCESS_CREATE_MEMBER("SUCCESS_CREATE_MEMBER","계정 생성 성공"),
    SUCCESS_CHANGE_MEMBER_PASSWORD("SUCCESS_CREATE_MEMBER","비밀번호 변경 성공"),
    EXISTING_RESOURCE("EXISTING_RESOURCE", "이미 존재하는 이메일 주소입니다."),
    CODE_NOT_CONFIRMED("CODE_NOT_CONFIRMED", "인증번호를 확인해주세요."),
    CODE_EXPIRED("CODE_EXPIRED", "만료된 인증번호입니다."),
    USER_NOT_FOUND("USER_NOT_FOUND", "입력하신 정보와 일치하는 정보가 없습니다. <br /> 확인 후 다시 입력해주세요."),
    INVALID_PW("INVALID_PW", "비밀번호를 확인해주세요."),
    PAYLOAD_INVALID("PAYLOAD_INVALID","입력값을 확인해주세요"),
    INAPPROPRIATE_PARAMETER_VALUE("INAPPROPRIATE_PARAMETER_VALUE", "잘못된 입력값입니다."),
    POST_NOT_FOUND("POST_NOT_FOUND", "게시글이 존재하지 않아 댓글을 저장할 수 없습니다."),
    SUCCESS_CREATE_COMMENT("SUCCESS_CREATE_COMMENT", "댓글을 성공적으로 저장했습니다.");


    private final String code;
    private final String label;

    ResponseCode(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
