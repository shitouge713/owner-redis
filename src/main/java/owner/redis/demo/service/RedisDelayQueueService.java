package owner.redis.demo.service;

/**
 * 延迟队列执行器
 * Created by sxl on 2022/06/29.
 */
public interface RedisDelayQueueService<T> {

    void executeTask(T t);
}
