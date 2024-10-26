package com.ebook_searching.book.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Book {
    private String title;
    private String authorName;
    private String genre;
    private String isbn;
    private String publisher;
    private String publicationYear;

    // ToString method for debugging
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + authorName + '\'' +
                ", genre='" + genre + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publicationYear=" + publicationYear +
                '}';
    }

//    // Getter and Setter methods
//    public void setPublicationYear(String publicationYear) {
//        this.publicationYear = Integer.parseInt(publicationYear);
//    }
}
