package org.ebook_searching.common.utils;

import com.google.protobuf.Internal;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.ProtocolStringList;
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

    @Named("toProtocolStringList")
    public static ProtocolStringList toProtocolStringList(String str) {
        // Split the input string by commas and create a list
        List<String> list = Optional.ofNullable(str)
                .map(s -> Arrays.asList(s.split(COMMA)))
                .orElse(List.of());

        // Use LazyStringArrayList which is compatible with ProtocolStringList
        LazyStringArrayList protobufList = new LazyStringArrayList();
        protobufList.addAll(list);

        // Return as ProtocolStringList
        return protobufList.getUnmodifiableView();
    }
}
