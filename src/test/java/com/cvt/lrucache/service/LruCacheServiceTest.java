package com.cvt.lrucache.service;

import com.cvt.lrucache.model.Cache;
import com.cvt.lrucache.repository.CacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LruCacheServiceTest {

    private CacheRepository repository;
    private LruCacheService lruCacheService;

    @BeforeEach
    void setUp() {
        repository = mock(CacheRepository.class);
        lruCacheService = new LruCacheService(repository);
    }

     @Test
     void testLoadCache() {
         when(repository.findAll()).thenReturn(Arrays.asList(
                 new Cache("k1", "v1"),
                 new Cache("k2", "v2")
         ));

         lruCacheService.loadCache();

         assertEquals("v1", lruCacheService.get("k1"));
         assertEquals("v2", lruCacheService.get("k2"));
     }

     @Test
     void testGetAndSave() {
         // Save value
         lruCacheService.save("foo", "bar");

         // Should be in cache
         assertEquals("bar", lruCacheService.get("foo"));
         // Should save in repository
         ArgumentCaptor<Cache> captor = ArgumentCaptor.forClass(Cache.class);
         verify(repository).save(captor.capture());
         assertEquals("foo", captor.getValue().getKey());
         assertEquals("bar", captor.getValue().getValue());
     }

     @Test
     void testSaveThrowsWhenValueTooLarge() {
         String largeValue = "x".repeat(1025); // 1025 bytes
         IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                 lruCacheService.save("big", largeValue));
         assertTrue(ex.getMessage().contains("1KB"));
     }

     @Test
     void testDelete() {
         lruCacheService.save("foo", "bar");
         lruCacheService.delete("foo");

         assertNull(lruCacheService.get("foo"));
         verify(repository).deleteById("foo");
     }

     @Test
     void testClear() {
         lruCacheService.save("foo", "bar");
         lruCacheService.save("baz", "qux");

         lruCacheService.clear();

         assertNull(lruCacheService.get("foo"));
         assertNull(lruCacheService.get("baz"));
         verify(repository).deleteAll();
     }

    @Test
    void testLruEviction() {
        // Set up a small cache for easy eviction
        LruCacheService smallCacheService = new LruCacheService(repository) {
            {
                // Override the cache to a smaller capacity for testing
                // Use Reflection for test only
                try {
                    Field cacheField = LruCacheService.class.getDeclaredField("cache");
                    cacheField.setAccessible(true);
                    cacheField.set(this, new LinkedHashMap<String, String>(3, 0.75f, true) {
                        @Override
                        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                            boolean shouldRemove = size() > 3;
                            if (shouldRemove) {
                                repository.deleteById(eldest.getKey());
                            }
                            return shouldRemove;
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        smallCacheService.save("one", "1");
        smallCacheService.save("two", "2");
        smallCacheService.save("three", "3");
        assertEquals("1", smallCacheService.get("one"));
        assertEquals("2", smallCacheService.get("two"));
        // Insert four, should evict "three" (LRU), because one and two was used recently
        smallCacheService.save("four", "4");

        assertNull(smallCacheService.get("three"));
        assertEquals("1", smallCacheService.get("one"));
        assertEquals("2", smallCacheService.get("two"));
        assertEquals("4", smallCacheService.get("four"));
        verify(repository, atLeastOnce()).deleteById("three");
    }
}
