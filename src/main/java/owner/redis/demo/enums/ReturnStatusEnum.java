package owner.redis.demo.enums;

import java.util.HashSet;


public enum ReturnStatusEnum {
    SERVICE_SUCCESS("200", "成功"),
    ERROR("500", "服务器异常"),
    SENTINEL_ERROR("500", "服务器限流"),
    SERVER_ERROR("4000", "服务器错误"),
    PARAM_ERROR("3000", "参数错误,%s"),
    FAIL_GET_DISTRIBUTED_LOCK("3001", "获取分布式锁失败");

    private final String value;
    private final String desc;

    private ReturnStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    public String getDesc4Log() {
        return "ERROR_CODE:" + value + "\t" + desc + "\t";
    }

    private static HashSet<String> hashSet;

    static {
        hashSet = new HashSet<String>();
        hashSet.clear();
        for (ReturnStatusEnum returnStatus : ReturnStatusEnum.values()) {
            hashSet.add(returnStatus.getValue());
        }
    }

    public static boolean isDefined(String value) {
        if (hashSet.contains(value)) {
            return true;
        }
        return false;
    }

    public static ReturnStatusEnum get(String value) {
        for (ReturnStatusEnum o : ReturnStatusEnum.values()) {
            if (value == o.getValue()) {
                return o;
            }
        }
        return null;
    }
}
