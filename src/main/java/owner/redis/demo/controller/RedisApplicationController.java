package owner.redis.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import owner.redis.demo.Result;
import owner.redis.demo.request.DistributedLockRequest;
import owner.redis.demo.service.DistributedLockService;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "redis应用", consumes = "application/json")
@RequestMapping("/redis/application")
public class RedisApplicationController {

    @Autowired
    private DistributedLockService distributedLockService;

    @ApiOperation(value = "应用场景-分布式锁-RedisTemplate实现")
    @GetMapping("/distributedLock")
    public Result<Boolean> distributedLock(@Valid DistributedLockRequest request) {
        distributedLockService.distributedLockMethod(request.getOrderNo());
        return Result.success();
    }

    @ApiOperation(value = "应用场景-分布式锁-Redisson实现")
    @GetMapping("/distributedLock2")
    public Result<Boolean> distributedLock2(@Valid DistributedLockRequest request) {
        distributedLockService.distributedLockMethod2(request.getOrderNo());
        return Result.success();
    }
}
