package com.life.square.advise.exception;

/**
 *
 * @author Chunming Liu In 2022/08/14
 */
public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super();
    }

    public TokenNotFoundException(String message) {
        super(message);
    }

    public TokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
