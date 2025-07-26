package com.example.common_lib.utils;

import com.example.common_lib.error.ConstraintException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.utils  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 4:55 PM
 */

public class ReflectionUtilities {
    private static void setField(Object object, String fieldName, Object value) throws IllegalAccessException {
        Field field = getFieldByName(object.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            field.set(object, value);
        }
    }

    public static Object getField(Object object, String fieldName) throws IllegalAccessException {
        Field field = getFieldByName(object.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    private static List<Field> getAllFields(Object object) {
//		return new ArrayList<>(Arrays.asList(object.getClass().getDeclaredFields()));
        List<Field> fields = new ArrayList<>();
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    private static Field getFieldByName(Class<?> clazz, String fieldName) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        if (clazz.getSuperclass() != null) {
            return getFieldByName(clazz.getSuperclass(), fieldName);
        }
        return null;
    }

    public static void updatePartialFieldChange(Object objectExistedInDB, Object update, String... excludeFieldsToUpdate) {
        List<Field> updateFields = getAllFields(update);
        List<Field> existedFields = getAllFields(objectExistedInDB);
        List<String> excludeFieldsList = Arrays.asList(excludeFieldsToUpdate);

        for (Field field : updateFields) {
            try {
                if (!excludeFieldsList.contains(field.getName())) {
                    // Kiểm tra nếu trường tồn tại trong objectExistedInDB
                    boolean fieldExistsInExistedObject = existedFields.stream()
                            .anyMatch(existedField -> existedField.getName().equals(field.getName()));

                    if (fieldExistsInExistedObject) {
                        Object value = getField(update, field.getName());
                        Object oldValue = getField(objectExistedInDB, field.getName());
                        if (value != null && !value.equals(oldValue)) {
                            setField(objectExistedInDB, field.getName(), value);
                        }
                    }
                }
            } catch (Exception e) {
                throw new ConstraintException("Lỗi khi cập nhật trường " + field.getName());
            }
        }
    }
}