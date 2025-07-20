package com.ksd.blog.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private int code;   // 0 成功
    private String msg;
    private T data;

    public static <T> Result<T> ok(T data)   { return new Result<>(0,"success",data); }
    public static <T> Result<T> fail(String msg){return new Result<>(1,msg,null);}
    public static <T> Result<T> ok() {
        return ok(null);
    }
}