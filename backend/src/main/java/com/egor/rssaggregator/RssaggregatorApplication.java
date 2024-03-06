package com.egor.rssaggregator;

import com.google.common.cache.CacheBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableCaching
public class RssaggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RssaggregatorApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		//noinspection NullableProblems
		return new ConcurrentMapCacheManager() {
			@Override
            protected Cache createConcurrentMapCache(String name) {
				return new ConcurrentMapCache(
						name,
						CacheBuilder.newBuilder()
								.expireAfterWrite(15, TimeUnit.MINUTES)
								.build().asMap(),
						false);
			}
		};
	}

}
