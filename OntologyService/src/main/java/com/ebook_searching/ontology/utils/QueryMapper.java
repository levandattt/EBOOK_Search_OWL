package com.ebook_searching.ontology.utils;

import java.lang.reflect.Method;
import org.apache.jena.query.QuerySolution;

public class QueryMapper {

    public static <T> T mapToObject(QuerySolution soln, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            soln.varNames().forEachRemaining(varName -> {
                try {
                    Object value = null;
                    if (soln.get(varName).isLiteral()) {
                        value = soln.getLiteral(varName).getString();
                    } else if (soln.get(varName).isResource()) {
                        value = soln.getResource(varName).getURI();
                    }

                    String methodName = "set" + capitalize(varName);
                    Method method = clazz.getMethod(methodName, String.class);

                    if (value != null) {
                        method.invoke(instance, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method để viết hoa chữ cái đầu tiên của tên biến
    private static String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
