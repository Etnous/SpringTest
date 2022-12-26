package biz.lala.SpringBoot.util;

import lombok.Data;

/**
 * 公共返回类
 * @author et
 */
@Data
public class Result<T> {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 提示信息
     */
    private final String msg;

    /**
     * 具体的内容
     */
    private T data;

    /**
     * 成功时调用, 没有data内容
     * @return
     */
    public static <String> Result<String> success() {
        return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), null);
    }

    /**
     * 成功时候的调用
     *
     * @param <T> 返回实体的类型
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * 根据返回的状态枚举, 构建返回结果
     *
     * @param resultEnum {@link ResultEnum} 返回状态枚举
     * @return Result
     */
    public static <Object> Result<Object> build(ResultEnum resultEnum) {
        return new Result<>(resultEnum);
    }

    /**
     * 根据自定义状态码{@code code}和自定义提示信息{@code msg}构建返回结果
     *
     * @param code 自定义状态码
     * @param msg 自定义提示信息
     * @return Result
     */
    public static <Object> Result<Object> build(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    /**
     * 根据自定义状态码{@code code}, 自定义提示信息{@code msg}以及返回实体{@code T}构建返回结果
     *
     * @param code 自定义状态码
     * @param msg 自定义提示信息
     * @param <T> 返回实体的类型
     * @return Result
     */
    public static <T> Result<T> build(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    /**
     * 出错时调用, 自定义提示信息{@code msg}
     *
     * @param msg 自定义提示信息
     * @param <T> 返回实体的类型
     * @return Result
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(msg);
    }

    /**
     * 出错时调用, 根据返回实体{@code T}构建返回结果
     *
     * @param data 返回实体
     * @param <T> 返回实体的类型
     * @return Result
     */
    public static <T> Result<T> error(T data) {
        return new Result<>(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), data);
    }

    private Result(T data) {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
        this.data = data;
    }

    private Result(String msg) {
        this.code = ResultEnum.ERROR.getCode();
        this.data = null;
        this.msg = msg;
    }

    private Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}