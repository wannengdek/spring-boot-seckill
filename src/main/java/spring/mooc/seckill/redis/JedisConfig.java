package spring.mooc.seckill.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {
    private Logger logger = LoggerFactory.getLogger(JedisConfig.class);

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

//    @Value("${redis.jedis.pool.max-active}")
//    private int maxActive;
//
//    @Value("${redis.jedis.pool.max-idle}")
//    private int maxIdle;
//
//    @Value("${redis.jedis.pool.min-idle}")
//    private int minIdle;
//
//    @Value("${redis.jedis.pool.max-wait}")
//    private long maxWaitMillis;

    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool redisPoolFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        jedisPoolConfig.setMaxTotal(maxActive);
//        jedisPoolConfig.setMinIdle(minIdle);

//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null,poolMaxIdle,poolMaxTotal,poolMaxWait);

        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(),
				redisConfig.getTimeout()*1000, null, 0);

        logger.info("JedisPool注入成功");
        logger.info("redis地址：" + host + ":" + port);
        return jedisPool;
    }
}
