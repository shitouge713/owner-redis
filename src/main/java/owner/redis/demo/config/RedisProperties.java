package owner.redis.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @create: 2022-06-17
 */
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisProperties {

    private String nodes;
    private String password;
    private int maxRediects;

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxRediects() {
        return maxRediects;
    }

    public void setMaxRediects(int maxRediects) {
        this.maxRediects = maxRediects;
    }

    @Override
    public String toString() {
        return "RedisProperties{" +
                "nodes='" + nodes + '\'' +
                ", password='" + password + '\'' +
                ", maxRediects=" + maxRediects +
                '}';
    }
}