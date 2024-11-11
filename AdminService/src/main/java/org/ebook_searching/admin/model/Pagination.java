package org.ebook_searching.admin.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pagination {
    Integer limit;
    Integer offset;
}
