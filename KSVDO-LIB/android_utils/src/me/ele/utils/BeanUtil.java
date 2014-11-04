package me.ele.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

public class BeanUtil {

    private static final String TARGET_OBJECT = "target object: ";
    private static final String TARGET_OBJECT_CAN_NOT_BE_NULL = "target object can not be null";

    private BeanUtil() {

    }

    public static void invokeMethod(String methodName, Object target, Object... args) throws Exception {
        if (TextUtils.isEmpty(methodName)) {
            throw new RuntimeException("method name can not be empty");
        }
        if (target == null) {
            throw new RuntimeException(TARGET_OBJECT_CAN_NOT_BE_NULL);
        }
        List<Class<?>> parameterTypes = new ArrayList<Class<?>>();
        for (Object arg : args) {
            parameterTypes.add(arg.getClass());
        }
        Method method = target.getClass().getDeclaredMethod(methodName,
                (Class<?>[]) parameterTypes.toArray());
        if (method == null) {
            throw new RuntimeException(TARGET_OBJECT
                    + target.getClass().getName()
                    + " do not have this method: " + methodName
                    + " with parameters: " + parameterTypes.toString());
        }
        method.setAccessible(true);
        method.invoke(target, args);
    }

    public static void setField(String fieldName, Object target, Object value) throws Exception {
        if (TextUtils.isEmpty(fieldName)) {
            throw new RuntimeException("field name can not be empty");
        }
        if (target == null) {
            throw new RuntimeException(TARGET_OBJECT_CAN_NOT_BE_NULL);
        }
        Field field = target.getClass().getDeclaredField(fieldName);
        if (field == null) {
            throw new RuntimeException(TARGET_OBJECT
                    + target.getClass().getName() + " do not have this field: "
                    + fieldName);
        }
        field.setAccessible(true);
        field.set(target, value);
    }

    public static Object getField(String fieldName, Object target) throws Exception {
        if (TextUtils.isEmpty(fieldName)) {
            throw new RuntimeException("field name can not be empty");
        }
        if (target == null) {
            throw new RuntimeException(TARGET_OBJECT_CAN_NOT_BE_NULL);
        }
        Field field = target.getClass().getDeclaredField(fieldName);
        if (field == null) {
            throw new RuntimeException(TARGET_OBJECT
                    + target.getClass().getName() + " do not have this field: "
                    + fieldName);
        }
        field.setAccessible(true);
        return field.get(target);
    }

    public static boolean isEquals(Object one, Object another) {
        if (one == another) {
            return true;
        }
        if (one == null) {
            return false;
        }
        if (another == null) {
            return false;
        }
        if (one.getClass() != another.getClass()) {
            return false;
        }
        return equalFields(one, another);
    }

    public static int hashCode(Object object) {
        final int prime = 31;
        int result = 1;
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                result = prime * result + ((value == null) ? 0 : value.hashCode());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    private static boolean equalFields(Object one, Object another) {
        Field[] onefields = one.getClass().getDeclaredFields();
        Field[] anotherfields = another.getClass().getDeclaredFields();
        if (onefields.length != anotherfields.length) {
            return false;
        }
        try {
            for (int i = 0; i < onefields.length; i++) {
                Field oneField = onefields[i];
                oneField.setAccessible(true);
                Field anotherfield = anotherfields[i];
                anotherfield.setAccessible(true);
                Object oneFieldValue = oneField.get(one);
                Object anotherFieldValue = anotherfield.get(another);
                if (oneFieldValue == null 
                        && anotherFieldValue != null) {
                    return false;
                }
                if (oneFieldValue != null 
                        && !oneFieldValue.equals(anotherFieldValue)) {
                    return false;
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
}
