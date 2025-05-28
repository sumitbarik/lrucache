package com.cvt.lrucache.controller;

import com.cvt.lrucache.service.LruCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheControllerTest {

    @Mock
    LruCacheService cacheService;
    private CacheController controller;

    @BeforeEach
    void setUp() {
        controller = new CacheController(cacheService);
    }

    @Test
    void get() {
        when(cacheService.get("name")).thenReturn("Sumit");
        ResponseEntity<String> getResp = controller.get("name");
        assertNotNull(getResp);
        assertEquals("Sumit",getResp.getBody());

        when(cacheService.get("name")).thenReturn(null);
        ResponseEntity<String> getResp2 = controller.get("name");
        assertNotNull(getResp2);
        assertEquals(404, getResp2.getStatusCode().value());
    }

    @Test
    void save() {
        ResponseEntity<String> resp = controller.save("name", "sumit");
        assertEquals(200, resp.getStatusCode().value());
        assertEquals("Cached", resp.getBody());
    }

    @Test
    void delete() {
        ResponseEntity<String> resp = controller.delete("name");
        assertEquals(200, resp.getStatusCode().value());
        assertEquals("Deleted", resp.getBody());
    }

    @Test
    void clear() {
        ResponseEntity<String> resp = controller.clear();
        assertEquals(200, resp.getStatusCode().value());
        assertEquals("Cleared", resp.getBody());
    }
}