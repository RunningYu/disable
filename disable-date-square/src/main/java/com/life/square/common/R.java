package com.life.square.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回通用类
 * @param <T>
 */
@Data
public class R<T> {
    /** 编码：1成功，0和其它数字为失败*/
    private Integer resultCode;
    /** 信息返回*/
    private String msg;
    /** 信息返回数据*/
    private T data;
    /** 动态数据*/
    private Map map = new HashMap();
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.resultCode = 1;
        return r;
    }
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.resultCode = 0;
        return r;
    }
    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
