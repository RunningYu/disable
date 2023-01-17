package com.life.square.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * todo JsonInclude 注解忽略 null值 看业务需求添加
 *
 * @author Chunming Liu In 2022/07/28
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Data
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer resultCode = 200;
    private T data;
    private String message = "ok";

    public ResponseResult() {
    }

    public ResponseResult(Integer code) {
        this ();
        this.resultCode = code;
    }

    public ResponseResult(Integer resultCode, T data) {
        this (resultCode);
        this.data = data;
    }

    public ResponseResult(Integer resultCode, String message) {
        this (resultCode);
        this.message = message;
    }

    public ResponseResult(Integer resultCode, String message, T data) {
        this (resultCode, message );
        this.data = data;
    }

    public static <T> ResponseResult<T> success() {
        return new ResponseResult ( 200 );
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult ( 200, data );
    }

    public static <T> ResponseResult<T> success(String message, T data) {
        return new ResponseResult ( 200, message, data );
    }

    public static <T> ResponseResult<T> success(Integer code, String message, T data) {
        return new ResponseResult ( code, message, data );
    }

    public static ResponseResult<String> error() {
        return new ResponseResult ( 500 );
    }

    public static <T> ResponseResult<T> error(String message) {
        return new ResponseResult ( 500, message );
    }

    public static <T> ResponseResult<T> error(Integer code, String message) {
        return new ResponseResult ( code, message );
    }

    public static <T> ResponseResult<T> error(Integer code, String message, T data) {
        return new ResponseResult ( code, message, data );
    }


}
