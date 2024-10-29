package org.ebook_searching.common.mapper;

import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.StringValue;
import org.springframework.stereotype.Component;

@Component
public class StringValueMapper {

    public StringValue map(String value) {
        return value == null ? null : StringValue.of(value);
    }

    public String map(StringValue value) {
        return value == null ? null : value.getValue();
    }
}
