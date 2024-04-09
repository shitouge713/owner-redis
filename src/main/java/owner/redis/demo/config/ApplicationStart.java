package owner.redis.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationStart implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments event) {
        log.info("新工单服务启动成功");
    }
}
