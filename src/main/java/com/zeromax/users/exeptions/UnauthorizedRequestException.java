package com.zeromax.users.exeptions;

public class UnauthorizedRequestException extends CustomGeneralException{

    public UnauthorizedRequestException(String message, String code) {
        super(message, code);
    }

    public UnauthorizedRequestException(String message) {
        super(message, "0000");
    }

    public UnauthorizedRequestException() {
        super("Unauthorized request", "0000");
    }
}
