package cn.ningle.library.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ningle
 * @version : R.java, v 0.1 2023/11/18 20:53 ningle
 * 全局统一响应
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable { // 使用泛型 T

    @Serial
    private static final long serialVersionUID = 24235345234123234L;
    // 业务请求是否成功
    private boolean success;
    // 对应的响应
    private Integer code;
    // 数据，使用泛型 T
    private T data;
    // 返回前端的消息
    private String message;

    public static <T> R<T> success(T data, String message) {
        R<T> r = new R<>();
        r.setSuccess(true);
        r.setData(data);
        r.setCode(200);
        r.setMessage(message);
        return r;
    }

    public static <T> R<T> success() {
        R<T> r = new R<>();
        r.setSuccess(true);
        r.setData(null);
        r.setCode(200);
        r.setMessage("ok");
        return r;
    }

    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setSuccess(true);
        r.setData(data);
        r.setCode(200);
        r.setMessage("success");
        return r;
    }

    public static <T> R<T> error(String message) {
        R<T> r = new R<>();
        r.setSuccess(false);
        r.setData(null);
        r.setCode(200);
        r.setMessage(message);
        return r;
    }

    public static <T> R<T> error(T data, String message, int code) {
        R<T> r = new R<>();
        r.setSuccess(false);
        r.setData(data);
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static <T> R<T> error(String message, int code) {
        R<T> r = new R<>();
        r.setSuccess(false);
        r.setData(null);
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static <T> R<T> error() {
        R<T> r = new R<>();
        r.setSuccess(false);
        r.setData(null);
        r.setCode(200);
        r.setMessage("request error");
        return r;
    }
}
