package com.cvt.lrucache.controller;

import com.cvt.lrucache.service.LruCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);
    private final LruCacheService cacheService;

    public CacheController(LruCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> get(@PathVariable String key) {
        String value = cacheService.get(key);
        return value != null ? ResponseEntity.ok(value) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestParam String key, @RequestBody String value) {
        logger.info("Save cache");
        cacheService.save(key, value);
        return ResponseEntity.ok("Cached");
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<String> delete(@PathVariable String key) {
        cacheService.delete(key);
        return ResponseEntity.ok("Deleted");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clear() {
        cacheService.clear();
        return ResponseEntity.ok("Cleared");
    }
}
