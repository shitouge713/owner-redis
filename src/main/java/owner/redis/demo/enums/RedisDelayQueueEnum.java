package owner.redis.demo.enums;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 延迟队列业务枚举
 * Created by sxl on 2022/06/29.
 */
@NoArgsConstructor
@AllArgsConstructor
public enum RedisDelayQueueEnum {


    TEST_DELAYED_TASK("TEST_DELAYED_TASK", "测试延迟队列任务", "testDelayedTaskImpl"),
    ;

    /**
     * 延迟队列 Redis Key
     */
    private String code;

    /**
     * 中文描述
     */
    private String name;

    /**
     * 延迟队列具体业务实现的 Bean
     * 可通过 Spring 的上下文获取
     */
    private String beanId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }
}
