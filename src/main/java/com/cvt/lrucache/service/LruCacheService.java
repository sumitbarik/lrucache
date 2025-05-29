package com.cvt.lrucache.service;

import com.cvt.lrucache.model.Cache;
import com.cvt.lrucache.repository.CacheRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service class implementing LRU (Least Recently Used) cache functionality.
 * Provides in-memory caching with persistence backup using JPA.
 * Enforces a maximum capacity and value size limitations.
 */
@Service
@Slf4j
public class LruCacheService {
    /** Repository for persistent cache storage */
    private final CacheRepository repository;

    /** Maximum number of entries the cache can hold */
    private static final int MAX_CAPACITY = 100_000;

    private static final Logger logger = LoggerFactory.getLogger(LruCacheService.class);

    /**
     * In-memory LRU cache implementation using LinkedHashMap.
     * Automatically removes least recently used entries when capacity is exceeded.
     * Access-order is maintained (true parameter in constructor).
     */
    private final LinkedHashMap<String, String> cache = new LinkedHashMap<>(MAX_CAPACITY, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            boolean shouldRemove = size() > MAX_CAPACITY;
            if (shouldRemove) {
                logger.debug("Evicting eldest entry with key: {}", eldest.getKey());
                repository.deleteById(eldest.getKey());
            }
            return shouldRemove;
        }
    };

    /**
     * Constructs a new LRU cache service with the specified repository.
     * 
     * @param repository The repository for persistent storage of cache entries
     */
    public LruCacheService(CacheRepository repository) {
        this.repository = repository;
    }

    /**
     * Initializes the in-memory cache by loading all entries from persistent
     * storage.
     * Called automatically after bean construction.
     */
    @PostConstruct
    public void loadCache() {
        logger.info("Loading cache from persistent storage");
        repository.findAll().forEach(entry -> cache.put(entry.getKey(), entry.getValue()));
        logger.info("Cache loaded with {} entries", cache.size());
    }

    /**
     * Retrieves a value from the cache for the specified key.
     * Accessing a key automatically moves it to the most recently used position.
     * 
     * @param key The key to look up
     * @return The cached value, or null if not found
     */
    public String get(String key) {
        String value = cache.get(key);
        if (value != null) {
            logger.debug("Cache hit for key: {}", key);
        } else {
            logger.debug("Cache miss for key: {}", key);
        }
        return value;
    }

    /**
     * Saves a key-value pair to both the in-memory cache and persistent storage.
     * 
     * @param key   The key to store the value under
     * @param value The value to be stored
     * @throws IllegalArgumentException if the value size exceeds 1KB
     */
    public void save(String key, String value) {
        if (value.getBytes(StandardCharsets.UTF_8).length > 1024) {
            logger.warn("Attempted to save value exceeding 1KB limit for key: {}", key);
            throw new IllegalArgumentException("Value exceeds 1KB limit");
        }
        logger.debug("Saving cache entry with key: {}", key);
        cache.put(key, value);
        repository.save(new Cache(key, value));
    }

    /**
     * Deletes an entry from both the in-memory cache and persistent storage.
     * 
     * @param key The key to be removed
     */
    public void delete(String key) {
        logger.info("Deleting cache entry with key: {}", key);
        cache.remove(key);
        repository.deleteById(key);
    }

    /**
     * Clears all entries from both the in-memory cache and persistent storage.
     */
    public void clear() {
        logger.info("Clearing all cache entries");
        cache.clear();
        repository.deleteAll();
    }
}
