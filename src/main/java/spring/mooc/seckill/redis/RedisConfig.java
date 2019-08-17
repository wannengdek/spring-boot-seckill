package spring.mooc.seckill.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix="redis")
@Data
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.poolMaxTotal}")
    private int poolMaxTotal;

    @Value("${redis.poolMaxIdle}")
    private int poolMaxIdle;

    @Value("${redis.poolMaxWait}")
    private int poolMaxWait;//秒

}
