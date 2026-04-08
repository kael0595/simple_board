package com.example.demo.auth.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VerificationRepository {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    public String findByEmail(String email) {
        return store.get(email);
    }

    public void save(String email, String code) {
        store.put(email, code);
    }

    public void remove(String email) {
        store.remove(email);
    }
}
