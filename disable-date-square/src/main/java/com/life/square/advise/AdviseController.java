package com.life.square.advise;

import com.life.square.advise.exception.TokenNotFoundException;
import com.life.square.common.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

/**
 * todo catch controller 异常
 *
 * @author Chunming Liu In 2022/07/28
 */
@RestControllerAdvice
public class AdviseController {

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseResult<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseResult.error(e.getMessage());
    }

    @ExceptionHandler(value = RestClientException.class)
    public ResponseResult<String> handleRestClientException(RestClientException e) {
        return ResponseResult.error(e.getMessage());
    }

    @ExceptionHandler(value = TokenNotFoundException.class)
    public ResponseResult<String> handleTokenNotFoundException(TokenNotFoundException e) {
        return ResponseResult.error(401, e.getMessage());
    }
}
