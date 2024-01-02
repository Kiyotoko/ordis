package org.ordis;

public class OrdisException extends RuntimeException {
    private final int errorCode;

    public OrdisException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
