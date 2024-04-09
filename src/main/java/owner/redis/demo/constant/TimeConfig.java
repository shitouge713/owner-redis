package owner.redis.demo.constant;


/**
 * 自动取消时间设置常量类
 *
 * @author sxl
 */
public class TimeConfig {

    /**
     * 订单未支付,取消订单时间300s  5min
     */
    public static final Integer UN_PAY_ORDER_CANCEL_TIME = 300;

    /**
     * 订单未支付,取消订单时间300s  5min
     */
    public static final Integer UN_PAY_ORDER_CANCEL_TIME_MINUTE = 5;

    /**
     * 分布式锁默认取消时间100s
     */
    public static final Long DISTRIBUTE_LOCK_CANCEL_TIME = 10L;

    /**
     * 订单未支付,取消订单时间10min
     */
    public static final Integer PAY_ING_ORDER_CANCEL_TIME = 600;

    /**
     * 订单出货超时时间70s
     */
    public static final Integer SHIP_OUT_ORDER_FINISH_TIME = 70;

    /**
     * 到家订单未支付,取消订单时间900s  15min
     */
    public static final Integer DJ_UN_PAY_ORDER_CANCEL_TIME_SECOND = 900;


    /**
     * 本地生活订单未支付,取消订单时间900s  15min
     */
    public static final Integer LOCAL_LIFE_UN_PAY_ORDER_CANCEL_TIME_SECOND = 900;

    /**
     * 到家订单未支付,取消订单时间900s  15min
     */
    public static final Integer DJ_UN_PAY_ORDER_CANCEL_TIME_MINUTE = 15;

    /**
     * 本地生活订单未支付,取消订单时间900s  15min
     */
    public static final Integer LOCAL_LIFE_UN_PAY_ORDER_CANCEL_TIME_MINUTE = 15;

    /**
     * 全局链路数据创建锁失效时间
     */
    public static final Long GLOBAL_LINK_DATA_CREATE_LOCK_CANCEL_TIME = 5L;
}
