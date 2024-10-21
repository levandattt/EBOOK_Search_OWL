package com.ebook_searching.ontology.payload;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ListKeyWordReq {
    private List<String> keywords;
}
