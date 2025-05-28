package com.cvt.lrucache.service;

import com.cvt.lrucache.repository.CacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LruCacheServiceTest {

    @Mock
    CacheRepository repository;

    LruCacheService cacheService;

    @BeforeEach
    void setUp() {
        cacheService = new LruCacheService(repository);
    }


    void loadCache() {
        cacheService.loadCache();
    }

    @Test
    void get() {
//        when(repository.getReferenceById())
//        cacheService.get("name");
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void clear() {
    }
}