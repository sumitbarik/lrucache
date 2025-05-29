package com.cvt.lrucache.repository;

import com.cvt.lrucache.model.Cache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Cache entity operations.
 * Provides CRUD operations for Cache entities using JPA.
 * Extends JpaRepository to inherit basic database operations.
 * The repository uses String as the key type and Cache as the entity type.
 */
@Repository
public interface CacheRepository extends JpaRepository<Cache, String> {
}
