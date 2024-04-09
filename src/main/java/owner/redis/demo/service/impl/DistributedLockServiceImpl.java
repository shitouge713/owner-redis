package owner.redis.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owner.redis.demo.constant.RedisConfig;
import owner.redis.demo.enums.ReturnStatusEnum;
import owner.redis.demo.exception.NoWarnException;
import owner.redis.demo.service.DistributedLockService;
import owner.redis.demo.util.RedisUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DistributedLockServiceImpl implements DistributedLockService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void distributedLockMethod(String orderNo) {
        //模拟逻辑执行过程
        try {
            Boolean isLock = redisUtils.getLock(RedisConfig.KEY_ORDER_NO + orderNo, "1", 10L);
            if (!isLock) {
                log.warn("获取分布式锁失败,orderNo:{}", orderNo);
                throw new NoWarnException(ReturnStatusEnum.FAIL_GET_DISTRIBUTED_LOCK);
            }
            log.info(Thread.currentThread().getName() + "，获取到锁");
            Thread.sleep(10000);
            //正常执行结束释放
            redisUtils.releaseLock(RedisConfig.KEY_ORDER_NO + orderNo, "1");
        } catch (InterruptedException e) {
            //非获取锁失败的异常，释放
            redisUtils.releaseLock(RedisConfig.KEY_ORDER_NO + orderNo, "1");
            log.error("Thread.sleep异常,{}", e);
            throw new NoWarnException("Thread.sleep异常");
        }
    }

    @Override
    public void distributedLockMethod2(String orderNo) {
        //模拟逻辑执行过程
        RLock lock = redissonClient.getLock(RedisConfig.REDISSON_KEY_ORDER_NO + orderNo);
        try {
            // 尝试获取锁，最多等待00秒，持有锁的时间为10秒
            //注意：如果等待时间超过了持有锁的时间，
            boolean isLocked = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (!isLocked) {
                log.warn("获取分布式锁失败,orderNo:{}", orderNo);
                throw new NoWarnException(ReturnStatusEnum.FAIL_GET_DISTRIBUTED_LOCK);
            }
            log.info(Thread.currentThread().getName() + "，获取到锁");
            Thread.sleep(2000);
            //正常执行结束释放
            log.info(Thread.currentThread().getName() + "，准备释放锁");
            //如果非当前线程尝试释放锁，会报错
            //lock.unlock();不可直接释放，需要判断下线程是否属于当前线程后，再释放
            if(lock.isLocked()){ // 是否还是锁定状态
                if(lock.isHeldByCurrentThread()){ // 时候是当前执行线程的锁
                    lock.unlock(); // 释放锁
                }
            }
        } catch (InterruptedException e) {
            //非获取锁失败的异常，释放
            if(lock.isLocked()){ // 是否还是锁定状态
                if(lock.isHeldByCurrentThread()){ // 时候是当前执行线程的锁
                    lock.unlock(); // 释放锁
                }
            }
            log.error("Thread.sleep异常,{}", e);
            throw new NoWarnException("Thread.sleep异常");
        }
    }
}
