package org.ac.cst8277.Khan.Yasar.usermanagement;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {
    // In-memory storage: Maps Token String -> User Object
    public static final Map<String, User> activeTokens = new ConcurrentHashMap<>();
}