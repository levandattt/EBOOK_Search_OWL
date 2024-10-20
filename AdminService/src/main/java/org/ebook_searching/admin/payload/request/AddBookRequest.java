package org.ebook_searching.admin.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class AddBookRequest {

    @NotNull
    @NotBlank
    @Length(max = 255, message = "Tên sách không được vượt quá 255 kí tự")
    private String title;

    @NotNull
    @NotBlank
    @Length(max = 255, message = "Tên thể loại không được vượt quá 255 kí tự")
    private String genre;

    @NotNull(message = "publishedAt không được vượt quá 255 kí tự")
    private LocalDate publishedAt;

    @NotNull
    @NotBlank
    @Length(min = 5, max = 255, message = "Tên nhà xuất bản không quá 255 kí tự")
    private String publisher;

    private String language;

    private BigDecimal avgRatings;

    private Long ratingsCount;

    // You can also include authors if needed
    private Set<Long> authorIds;
}
