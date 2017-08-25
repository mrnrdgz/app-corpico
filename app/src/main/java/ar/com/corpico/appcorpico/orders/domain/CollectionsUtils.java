package ar.com.corpico.appcorpico.orders.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

public class CollectionsUtils {
    private CollectionsUtils() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    @SuppressWarnings("rawtypes")
    public static Object getObject(Map map, Object key) {
        return getObject(map, key, null);
    }

    @SuppressWarnings("rawtypes")
    public static Object getObject(Map map, Object key, Object defaultValue) {
        if (map != null) {
            Object value = map.get(key);

            if (value != null) {
                return value;
            }
        }
        return defaultValue;
    }
    /*public static Object getPage(List<Order> order, Object key, Object defaultValue) {
        if (order != null) {
            Object value = order.get(key);

            if (value != null) {
                return value;
            }
        }
        return defaultValue;
    }*/
}