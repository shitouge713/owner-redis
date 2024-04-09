package owner.redis.demo.service.impl;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import owner.redis.demo.service.RedisDelayQueueService;

import java.util.concurrent.*;

/**
 * 订单支付超时处理类
 * 2022/07/11
 */
@Service
@Slf4j
public class TestDelayedTaskImpl implements RedisDelayQueueService<String> {

    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("test-delayed-").build();
    //拒绝策略使用不允许丢失，由主线程执行的策略
    ExecutorService executor = new ThreadPoolExecutor(2, 4, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(4), namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void executeTask(String orderNo) {
        executor.execute(() -> {
            System.out.println("执行延时队列任务");
        });
    }
}
