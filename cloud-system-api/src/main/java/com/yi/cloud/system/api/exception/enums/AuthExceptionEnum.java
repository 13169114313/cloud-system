package com.yi.cloud.system.api.exception.enums;

import com.yi.cloud.common.exception.IBaseExceptionEnum;

/**
 * 鉴权错误枚举
 */
public enum AuthExceptionEnum implements IBaseExceptionEnum {
    USER_NOT_FOUND(3110, "用户不存在！"),

    INVALID_PWD(3111, "密码错误！");

    private int code;
    private String message;

    AuthExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
