package com.ows.leaderboard.config;

import java.time.Duration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

@EnableCaching
@Configuration
public class RedisDataConfig {
    private final GenericJacksonJsonRedisSerializer jsonSerializer = GenericJacksonJsonRedisSerializer.builder().enableDefaultTyping(
        BasicPolymorphicTypeValidator.builder().allowIfSubType("com.ows.").allowIfSubType("java.util.").build()
    ).build();
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // Use StringSerializer for the Main Key ("leaderboard") and the ZSet Member Keys ("player123")
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // Use GenericToStringSerializer or GenericJackson2JsonRedisSerializer for the Values/Scores

        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);

        return redisTemplate;
    }
    @Bean RedisCacheManager cacheManager(RedisConnectionFactory cf) {
        var config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30)).serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));
        return RedisCacheManager.builder(cf).cacheDefaults(config).build();
    }
}
