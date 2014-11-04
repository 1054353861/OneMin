package me.ele.utils;

import java.util.Collection;
import java.util.Map;

public class CollectionsUtils {

    private CollectionsUtils() {
        
    }
    
    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }
    
    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }
    
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
    
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
