package com.cvt.lrucache.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class Cache {
    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
    private String key;

    @Column(length = 1024)
    private String value;


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Cache() {
    }

    public Cache(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
