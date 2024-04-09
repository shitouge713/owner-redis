package owner.redis.demo;

import lombok.Data;
import owner.redis.demo.enums.ReturnStatusEnum;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Objects;

@Data
public class Result<T> implements Serializable {
    /**
     * 返回码的值
     */
    private String code = ReturnStatusEnum.SERVICE_SUCCESS.getValue();
    /**
     * 返回码的描述
     */
    private String msg = ReturnStatusEnum.SERVICE_SUCCESS.getDesc();
    /**
     * 返回的数据
     */
    private T data;
    /**
     * 耗时
     */
    private Long timeCost;

    private Integer count = 0;

    public boolean isSuccess() {
        return Objects.equals(ReturnStatusEnum.SERVICE_SUCCESS.getValue(), this.getCode());
    }

    public static <T> Result<T> success() {
        return restResult(null, ReturnStatusEnum.SERVICE_SUCCESS.getValue(), "成功");
    }

    public static <T> Result<T> success(T data) {
        return restResult(data, ReturnStatusEnum.SERVICE_SUCCESS.getValue(), "成功");
    }

    public static <T> Result<T> success(T data, String msg) {
        return restResult(data, ReturnStatusEnum.SERVICE_SUCCESS.getValue(), msg);
    }

    public static <T> Result<T> failed() {
        return restResult(null, ReturnStatusEnum.ERROR.getValue(), ReturnStatusEnum.ERROR.getDesc());
    }

    public static <T> Result<T> failed(String msg) {
        return restResult(null, ReturnStatusEnum.ERROR.getValue(), msg);
    }

    public static <T> Result<T> failed(T data) {
        return restResult(data, ReturnStatusEnum.ERROR.getValue(), ReturnStatusEnum.ERROR.getDesc());
    }

    public static <T> Result<T> failed(String code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> Result<T> restResult(T data, String code, String msg) {
        return restResult(data, code, msg, null);
    }

    public static <T> Result<T> failed(ReturnStatusEnum returnStatusEnum) {
        return restResult(null, returnStatusEnum.getValue(), returnStatusEnum.getDesc());
    }

    private static <T> Result<T> restResult(T data, String code, String msg, Long timeCost) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setCount(data instanceof AbstractCollection ? ((AbstractCollection) data).size() : -1);
        result.setMsg(msg);
        result.setTimeCost(timeCost);
        return result;
    }
}
