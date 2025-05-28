package com.cvt.lrucache.service;

import com.cvt.lrucache.model.Cache;
import com.cvt.lrucache.repository.CacheRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class LruCacheService {
    private final CacheRepository repository;
    private static final int MAX_CAPACITY = 100_000;

    private final LinkedHashMap<String, String> cache = new LinkedHashMap<>(MAX_CAPACITY, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            boolean shouldRemove = size() > MAX_CAPACITY;
            if (shouldRemove) {
                repository.deleteById(eldest.getKey());
            }
            return shouldRemove;
        }
    };

    public LruCacheService(CacheRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void loadCache() {
        repository.findAll().forEach(entry -> cache.put(entry.getKey(), entry.getValue()));
    }

    public String get(String key) {
        return cache.get(key);
    }

    public void save(String key, String value) {
        if (value.getBytes(StandardCharsets.UTF_8).length > 1024) {
            throw new IllegalArgumentException("Value exceeds 1KB limit");
        }
        cache.put(key, value);
        repository.save(new Cache(key, value));
    }

    public void delete(String key) {
        cache.remove(key);
        repository.deleteById(key);
    }

    public void clear() {
        cache.clear();
        repository.deleteAll();
    }
}

