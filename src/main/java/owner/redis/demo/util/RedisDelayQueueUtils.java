package owner.redis.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis延迟队列工具
 * Created by sxl on 2022/06/29.
 */
@Slf4j
@Component
public class RedisDelayQueueUtils {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 添加延迟队列
     *
     * @param value     队列值
     * @param delay     延迟时间
     * @param timeUnit  时间单位
     * @param queueCode 队列键
     * @param <T>
     */
    public <T> void addDelayQueue(T value, long delay, TimeUnit timeUnit, String queueCode) {
        try {
            RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(queueCode);
            log.info("blockingDeque:{}",blockingDeque);
            RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
            delayedQueue.offer(value, delay, timeUnit);
            log.info("添加延时队列成功，队列键：{}，队列值：{}，延迟时间：{}", queueCode, value, timeUnit.toSeconds(delay) + "秒");
        } catch (Exception e) {
            log.error("fatalError,orderNo:{},queueCode:{},添加到延时队列失败:", value, queueCode, e);
        }
    }

    /**
     * 添加延迟队列
     *
     * @param value     队列值
     * @param delay     延迟时间
     * @param timeUnit  时间单位
     * @param queueCode 队列键
     * @param <T>
     */
    @Async
    public <T> void addDelayQueueAsync(T value, long delay, TimeUnit timeUnit, String queueCode) {
        try {
            RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(queueCode);
            log.info("blockingDeque:{}",blockingDeque);
            RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
            delayedQueue.offer(value, delay, timeUnit);
            log.info("添加延时队列成功，队列键：{}，队列值：{}，延迟时间：{}", queueCode, value, timeUnit.toSeconds(delay) + "秒");
        } catch (Exception e) {
            log.error("fatalError,orderNo:{},queueCode:{},添加到延时队列失败:", value, queueCode, e);
        }
    }

    /**
     * 获取延迟队列
     *
     * @param queueCode
     * @return
     * @throws InterruptedException
     */
    public String getDelayQueue(String queueCode) throws InterruptedException {
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(queueCode);
        redissonClient.getDelayedQueue(blockingDeque);
        String value = blockingDeque.take();
        log.info("queueCode:{},value：{},blockingDeque.size:{}", queueCode, value, blockingDeque.size());
        return value;
    }
}
