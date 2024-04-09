package owner.redis.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @create: 2022-06-17
 */

@Configuration
//@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Autowired
    RedisProperties redisProperties;

    @Value("${spring.redis.jedis.pool.max-active:8}")
    private Integer maxActive;
    @Value("${spring.redis.jedis.pool.max-idle:8}")
    private Integer maxIdle;
    @Value("${spring.redis.jedis.pool.max-wait:-1}")
    private Long maxWait;
    @Value("${spring.redis.jedis.pool.min-idle:0}")
    private Integer minIdle;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPool,
                                                         RedisClusterConfiguration jedisConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory(jedisConfig, jedisPool);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public RedissonClient redissonClient() {
        List<String> clusterNodes = new ArrayList<>();
        String[] nodes = redisProperties.getNodes().split(",");
        for (int i = 0; i < nodes.length; i++) {
            clusterNodes.add("redis://" + nodes[i]);
        }
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
        //心跳检测，定时与redis连接，可以防止一段时间过后，与redis的连接断开
        clusterServersConfig.setPingConnectionInterval(1000);
        //设置密码
        clusterServersConfig.setPassword(redisProperties.getPassword());
        return Redisson.create(config);
    }

    @Bean
    public JedisPoolConfig jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMinIdle(minIdle);
        return jedisPoolConfig;
    }

    @Bean
    public RedisClusterConfiguration jedisConfig() {
        RedisClusterConfiguration config = new RedisClusterConfiguration();
        String[] sub = redisProperties.getNodes().split(",");
        List<RedisNode> nodeList = new ArrayList<>(sub.length);
        String[] tmp;
        for (String s : sub) {
            tmp = s.split(":");
            nodeList.add(new RedisNode(tmp[0], Integer.valueOf(tmp[1])));
        }
        config.setClusterNodes(nodeList);
        config.setMaxRedirects(redisProperties.getMaxRediects());
        Optional.ofNullable(redisProperties.getPassword()).ifPresent(x -> config.setPassword(RedisPassword.of(x)));
        return config;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        //Key必须指定序列化方式，否则默认的有问题
        template.setKeySerializer(new StringRedisSerializer());
        //hashKey也必须指定序列化方式，否则默认的有问题
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
