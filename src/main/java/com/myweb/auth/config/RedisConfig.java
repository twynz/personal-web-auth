//todo maybe put token to redis is a better idea
//package com.myweb.auth.config;
//
//import com.myweb.auth.properties.RedisClientProperties;
//import com.myweb.auth.service.RedisCacheService;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.AdviceMode;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.JedisPoolConfig;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.interceptor.CacheErrorHandler;
//import org.springframework.cache.support.NoOpCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//@EnableCaching(mode = AdviceMode.ASPECTJ)
//public class RedisConfig extends CachingConfigurerSupport {
//
//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.redis")
//    public RedisClientProperties getRedisClientProperties() {
//        return new RedisClientProperties();
//    }
//
//    @Bean
//    @ConditionalOnProperty(prefix = "spring.redis", name = "enable-cache")
//    public JedisPoolConfig getJedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        RedisClientProperties.Pool pool = getRedisClientProperties().getPool();
//        if (pool != null) {
//            jedisPoolConfig.setMaxWaitMillis(pool.getMaxWait());
//            jedisPoolConfig.setMaxTotal(pool.getMaxActive());
//            jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
//            jedisPoolConfig.setMinIdle(pool.getMinIdle());
//            jedisPoolConfig.setTestOnBorrow(pool.isTestOnBorrow());
//            jedisPoolConfig.setTestOnReturn(pool.isTestOnReturn());
//            jedisPoolConfig.setTestWhileIdle(pool.isTestWhileIdle());
//            jedisPoolConfig.setNumTestsPerEvictionRun(pool.getNumTestsPerEvictionRun());
//            jedisPoolConfig.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRunsMillis());
//        }
//        return jedisPoolConfig;
//    }
//
//    @Bean
//    @ConditionalOnProperty(prefix = "spring.redis", name = "enable-cache")
//    public JedisConnectionFactory getJedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
//        jedisConnectionFactory.setHostName(getRedisClientProperties().getHost());
//        jedisConnectionFactory.setPort(getRedisClientProperties().getPort());
//        jedisConnectionFactory.setPassword(getRedisClientProperties().getPassword());
//        jedisConnectionFactory.setUsePool(true);
//        jedisConnectionFactory.setDatabase(getRedisClientProperties().getDatabase());
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    @ConditionalOnProperty(prefix = "spring.redis", name = "enable-cache")
//    public RedisTemplate redisTemplate(RedisConnectionFactory cf) {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(cf);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        return redisTemplate;
//    }
//
//    @Bean
//    @ConditionalOnProperty(prefix = "spring.redis", name = "enable-cache", havingValue = "true")
//    public CacheManager getRedisCacheManager(RedisTemplate redisTemplate) {
//        CacheManager cacheManager;
//        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
//        redisCacheManager.setUsePrefix(true);
//        redisCacheManager.setExpires(getRedisClientProperties().getExpiration());
//        cacheManager = redisCacheManager;
//        return cacheManager;
//    }
//
//    @Bean
//    @ConditionalOnProperty(prefix = "spring.redis", name = "enable-cache", havingValue = "false")
//    public CacheManager getNoOpCacheManager() {
//        return new NoOpCacheManager();
//    }
//
//    @Bean
//    @ConditionalOnProperty(prefix = "spring.redis", name = "enable-cache")
//    public RedisCacheService redisCacheService(CacheManager cacheManager, RedisTemplate redisTemplate,
//                                               RedisClientProperties redisClientProperties) {
//        return new RedisCacheService(cacheManager, redisTemplate, redisClientProperties);
//    }
//
//    @Override
//    public CacheErrorHandler errorHandler() {
//        return new CacheErrorHandler() {
//            @Override
//            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
//            }
//
//            @Override
//            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
//            }
//
//            @Override
//            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
//            }
//
//            @Override
//            public void handleCacheClearError(RuntimeException exception, Cache cache) {
//            }
//        };
//    }
//}
//
//
