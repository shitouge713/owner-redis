package owner.redis.demo.exception;


import owner.redis.demo.enums.ReturnStatusEnum;

/**
 * 自定义异常：该异常无需报警
 *
 * @author css
 */
public class NoWarnException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code;

    public NoWarnException(String message) {
        super(message);
        this.code = ReturnStatusEnum.SERVER_ERROR.getValue();
    }

    public NoWarnException(String code, String message) {
        super(message);
        this.code = code;
    }

    public NoWarnException(ReturnStatusEnum errorStatusEnum) {
        super(errorStatusEnum.getDesc());
        this.code = errorStatusEnum.getValue();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
