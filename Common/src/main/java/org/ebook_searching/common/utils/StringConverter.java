package org.ebook_searching.common.utils;

import com.google.protobuf.StringValue;

public class StringConverter {
    private StringConverter() {
    }

    public static String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder camelCaseString = new StringBuilder();

        // Split the input string into words by spaces
        String[] words = input.split("\\s+");

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            // Capitalize the first letter and add it to the result
            camelCaseString.append(Character.toUpperCase(word.charAt(0)));

            // Append the rest of the word in lowercase
            if (word.length() > 1) {
                camelCaseString.append(word.substring(1).toLowerCase());
            }
        }

        return camelCaseString.toString();
    }

    public static StringValue map(String str) {
        if (str == null) {
            return StringValue.getDefaultInstance();  // Return a default instance if input is null
        }
        return StringValue.of(str); // Convert string to StringValue
    }
}
