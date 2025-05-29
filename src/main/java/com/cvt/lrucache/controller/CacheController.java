package com.cvt.lrucache.controller;

import com.cvt.lrucache.service.LruCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing LRU cache operations.
 * Provides endpoints for basic cache operations like get, save, delete and clear.
 */
@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);
    private final LruCacheService cacheService;

    public CacheController(LruCacheService cacheService) {
        this.cacheService = cacheService;
    }

    /**
     * Retrieves a value from the cache for the given key.
     * @param key The key to look up in the cache
     * @return ResponseEntity containing the cached value if found, or 404 if not found
     */
    @GetMapping("/{key}")
    public ResponseEntity<String> get(@PathVariable String key) {
        logger.debug("Received GET request for key: {}", key);
        String value = cacheService.get(key);
        return value != null ? ResponseEntity.ok(value) : ResponseEntity.notFound().build();
    }

    /**
     * Saves a key-value pair to the cache.
     * @param key The key under which to store the value
     * @param value The value to be stored in the cache
     * @return ResponseEntity with confirmation message
     */
    @PostMapping
    public ResponseEntity<String> save(@RequestParam String key, @RequestBody String value) {
        logger.info("Received POST request to save cache entry. Key: {}", key);
        try {
            cacheService.save(key, value);
            logger.info("Successfully saved cache entry for key: {}", key);
            return ResponseEntity.ok("Cached");
        } catch (IllegalArgumentException e) {
            logger.error("Failed to save cache entry. Key: {}, Error: {}", key, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Deletes a specific entry from the cache.
     * @param key The key to be removed from the cache
     * @return ResponseEntity with confirmation message
     */
    @DeleteMapping("/{key}")
    public ResponseEntity<String> delete(@PathVariable String key) {
        logger.info("Received DELETE request for key: {}", key);
        cacheService.delete(key);
        logger.info("Successfully deleted cache entry for key: {}", key);
        return ResponseEntity.ok("Deleted");
    }

    /**
     * Clears all entries from the cache.
     * @return ResponseEntity with confirmation message
     */
    @DeleteMapping("/clear")
    public ResponseEntity<String> clear() {
        logger.info("Received request to clear entire cache");
        cacheService.clear();
        logger.info("Successfully cleared all cache entries");
        return ResponseEntity.ok("Cleared");
    }
}
