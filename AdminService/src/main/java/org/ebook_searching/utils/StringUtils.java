package org.ebook_searching.utils;

import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.ebook_searching.common.constants.Common.COMMA;

public class StringUtils {

    @Named("toSingleString")
    public static String toSingleString(List<String> list) {
        return String.join(COMMA, list);
    }

    @Named("toStringList")
    public static List<String> toStringList(String str) {
        return Optional.ofNullable(str)
                .map(s -> Arrays.asList(s.split(COMMA)))
                .orElse(List.of());
    }
}