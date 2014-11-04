package me.ele.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import android.os.Parcelable;

public class FieldUtils {
    
    private static final Class<?>[] BASIC_TYPES = {
        int.class
        , Integer.class
        , Double.class
        , double.class
        , Float.class
        , float.class
        , Boolean.class
        , boolean.class
        , String.class
        , char.class
        , Long.class
        , long.class
        , short.class
        , Short.class
        , byte.class
        , Byte.class, };
    
    private FieldUtils() {
        
    }
    
    public static boolean isBasicType(Field field) {
        return isBasicType(getFeildClass(field)) || field.getType().isPrimitive();
    }
    
    public static boolean isBasicType(Class<?> type) {
        boolean contains = false;
        for (Class<?> clazz : BASIC_TYPES) {
            if (clazz.equals(type)) {
                contains = true;
                break;
            }
        }
        return contains;
    }
    
    public static boolean isIntType(Field field) {
        return isIntType(getFeildClass(field));
    }
    public static boolean isIntType(Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return true;
        }
        return false;
    }
    
    public static boolean isDoubleType(Field field) {
        return isDoubleType(getFeildClass(field));
    }
    
    public static boolean isDoubleType(Class<?> type) {
        if (type == double.class || type == Double.class) {
            return true;
        }
        return false;
    }
    
    public static boolean isFloatType(Field field) {
        return isFloatType(getFeildClass(field));
    }
    
    public static boolean isFloatType(Class<?> type) {
        if (type == float.class || type == Float.class) {
            return true;
        }
        return false;
    }
    
    public static boolean isStringType(Field field) {
        return isStringType(getFeildClass(field));
    }
    
    public static boolean isStringType(Class<?> type) {
        if (type == String.class) {
            return true;
        }
        return false;
    }
    
    public static boolean isCharType(Field field) {
        return isCharType(getFeildClass(field));
    }
    
    public static boolean isCharType(Class<?> type) {
        if (type == char.class) {
            return true;
        }
        return false;
    }
    
    public static boolean isLongType(Field field) {
        return isLongType(getFeildClass(field));
    }
    
    public static boolean isLongType(Class<?> type) {
        if (type == long.class || type == Long.class) {
            return true;
        }
        return false;
    }
    
    public static boolean isShortType(Field field) {
        return isShortType(getFeildClass(field));
    }
    
    public static boolean isShortType(Class<?> type) {
        if (type == short.class || type == Short.class) {
            return true;
        }
        return false;
    }
    
    public static boolean isByteType(Field field) {
        return isBasicType(getFeildClass(field));
    }
    
    public static boolean isByteType(Class<?> type) {
        if (type == byte.class || type == Byte.class) {
            return true;
        }
        return false;
    }
    
    public static boolean isBooleanType(Field field) {
        return isBooleanType(getFeildClass(field));
    }
    
    public static boolean isBooleanType(Class<?> type) {
        if (type == boolean.class || type == Boolean.class) {
            return true;
        }
        return false;
    }
    
    public static boolean isArray(Field field) {
        if (getFeildClass(field).isArray()) {
            return true;
        }
        return false;
    }

    public static Class<?> getFeildClass(Field field) {
        return field.getType();
    }
    
    public static boolean isSerializable(Class<?> clazz) {
        if (isAssignableFrom(clazz, Serializable.class)) {
            return true;
        }
        return false;
    }
    
    public static boolean isSerializable(Field field) {
        return isSerializable(getFeildClass(field));
    }
    
    public static boolean isParcelable(Field field) {
        return isParcelable(getFeildClass(field));
    }
    
    public static boolean isParcelable(Class<?> clazz) {
        if (isAssignableFrom(clazz, Parcelable.class)) {
            return true;
        }
        return false;
    }
    
    public static boolean isAssignableFrom(Class<?> clazz, Class<?> targetClazz) {
        if (targetClazz.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }
    
    public static boolean isAssignableFrom(Field field, Class<?> targetClazz) {
        return isAssignableFrom(getFeildClass(field), targetClazz);
    }
    
    public static Class<?> getArrayActualType(Field field) {
        return getFeildClass(field).getComponentType();
    }
    
    public static boolean isList(Field field) {
        return List.class.isAssignableFrom(getFeildClass(field));
    }
    
    public static Class<?> getListActualType(Field field) {
        return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }
}
