package biz.lala.SpringBoot.util;

/**
 * 状态码枚举
 * @author et
 */

public enum ResultEnum {

    /**
     * 成功
     */
    SUCCESS(2000, "success"),
    /**
     * 失败
     */
    ERROR(5000, "error"),
    /**
     * 传参出错
     */
    PARAM_ERROR(5001, "params error"),
    /**
     * 必传参数丢失
     */
    PARAM_MISS_ERROR(5002, "param miss"),
    /**
     * 记录不存在
     */
    RECORD_NOT_EXIST(5003, "record is not exist!");

    private final Integer code;

    private final String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}