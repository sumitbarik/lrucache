package com.cvt.lrucache.repository;

import com.cvt.lrucache.model.Cache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepository extends JpaRepository<Cache, String> {
}
