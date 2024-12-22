package com.khorcha.utils;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class ThreadLocalContext {

    private static final ThreadLocal<Map<String, Object>> context = ThreadLocal.withInitial(HashMap::new);

    private ThreadLocalContext() {

    }

    public static void setValue(String key, Object value) {
        Map<String, Object> contextMap = context.get();
        contextMap.put(key,value);
    }

    public static Object getValue(String  key) {
        Map<String, Object> contextMap = context.get();
        return contextMap.get(key);
    }
}
