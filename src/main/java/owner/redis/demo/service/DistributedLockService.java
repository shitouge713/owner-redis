package owner.redis.demo.service;

/**
 * 分布式锁场景
 */
public interface DistributedLockService {

    void distributedLockMethod(String orderNo);

    void distributedLockMethod2(String orderNo);
}
