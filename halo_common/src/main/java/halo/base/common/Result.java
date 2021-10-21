package halo.base.common;

import halo.base.global.StatusCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Halo
 * @date Created in 2021/07/02 下午 07:37
 * @description 统一结果封装
 */

@Data
public class Result implements Serializable {

    /**
     * 200 表示正常，其他表示异常
     */
    private int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    /**
     * 成功返回
     *
     * @param code 状态码
     * @param msg  消息
     * @param data 数据
     * @return Result 对象
     */
    public static Result success(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result success(Object data) {
        return success(StatusCode.SUCCESS, "操作成功", data);
    }

    public static Result success() {
        return success(StatusCode.SUCCESS, "操作成功", null);
    }

    public static Result fail(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg, Object data) {
        return fail(StatusCode.ERROR, msg, data);
    }

    public static Result fail(String msg) {
        return fail(StatusCode.ERROR, msg, null);
    }

    public static Result fail() {
        return fail(StatusCode.ERROR, "", null);
    }

    /**
     * 设置返回消息
     *
     * @param message 消息
     * @return Result
     */
    public Result message(String message) {
        this.setMsg(message);
        return this;
    }


    /**
     * 设置返回状态码
     *
     * @param code 状态码
     * @return Result
     */
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

}
