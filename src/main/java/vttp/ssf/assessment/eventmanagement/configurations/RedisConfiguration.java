package vttp.ssf.assessment.eventmanagement.configurations;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp.ssf.assessment.eventmanagement.models.Event;

@Configuration
public class RedisConfiguration {
    private Logger logger = Logger.getLogger(RedisConfiguration.class.getName());
    
    // Inject the properties in application.properties into the configuration
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.username}")
    private String redisUser;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    
    // can have multiple of these to handle different types
    @Bean
    public RedisTemplate<String,String> createRedisConnection() {
        
        RedisTemplate<String,String> template =  new RedisTemplate<>();
        template.setConnectionFactory(jedisConnFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        
        return template;
    }

    @Bean
    public RedisTemplate<String,Event> RedisEventTemplate() {
        
        RedisTemplate<String,Event> template =  new RedisTemplate<>();
        template.setConnectionFactory(jedisConnFactory());
        template.setKeySerializer(new StringRedisSerializer());
        // template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // template.setHashValueSerializer(new StringRedisSerializer());
        
        return template;
    }

    public JedisConnectionFactory jedisConnFactory() {

        // Create redis config
        RedisStandaloneConfiguration config =  new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setDatabase(redisDatabase);
        // set username and password if user is not NOT_SET
        if (!"NOT_SET".equals(redisUser.trim())) {
            config.setUsername(redisUser);
            config.setPassword(redisPassword);
        }

        logger.log(Level.INFO, "Using Redis database %d".formatted(redisDatabase));
        logger.log(Level.INFO, "Using Redis password is set: %b".formatted(redisPassword != "NOT_SET"));

        JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();

        JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        return jedisFac;
    }
}
