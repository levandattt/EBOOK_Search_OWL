package com.ebook_searching.ontology.payload;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AddClassReq {
        private String className;
        private String classLabel;
        private String classComment;
}
