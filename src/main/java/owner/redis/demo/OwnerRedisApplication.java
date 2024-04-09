package owner.redis.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OwnerRedisApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(OwnerRedisApplication.class, args);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


