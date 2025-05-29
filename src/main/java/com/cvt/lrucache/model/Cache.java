package com.cvt.lrucache.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity class representing a cache entry in the persistent storage.
 * Each entry consists of a key-value pair with size limitations on the value.
 */
@Data
@Entity
public class Cache {
    /** Unique identifier for the cache entry */
    @Id
    private String key;

    /** Cached value with maximum length of 1KB */
    @Column(length = 1024)
    private String value;

    /**
     * Returns the key of this cache entry.
     * @return the cache entry key
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value stored in this cache entry.
     * @return the cached value
     */
    public String getValue() {
        return value;
    }

    /**
     * Default constructor required by JPA.
     */
    public Cache() {
    }

    /**
     * Creates a new cache entry with the specified key and value.
     * @param key the key for this cache entry
     * @param value the value to be cached
     */
    public Cache(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
