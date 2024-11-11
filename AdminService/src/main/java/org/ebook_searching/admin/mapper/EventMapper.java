    package org.ebook_searching.admin.mapper;

    import org.ebook_searching.admin.model.Author;
    import org.ebook_searching.admin.model.Book;
    import org.ebook_searching.admin.model.Genre;
    import org.ebook_searching.common.mapper.DateMapper;
    import org.ebook_searching.common.mapper.StringValueMapper;
    import org.ebook_searching.common.utils.StringUtils;
    import org.ebook_searching.proto.Event;
    import org.mapstruct.*;

    @Mapper(componentModel = "spring", uses = {StringUtils.class, DateMapper.class, StringValueMapper.class}, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    public interface EventMapper {

        @Mapping(target = "authorsList", source = "authors")
        @Mapping(target="genresList", source = "genres")
        Event.AddBookEvent toBookEvent(Book addBookRequest);

        @Mapping(target = "authorsList", source = "addBookRequest.authors")
        @Mapping(target="genresList", source = "addBookRequest.genres")
        @Mapping(target = "oldTitle", expression = "java(oldTitle)")
        Event.AddBookEvent toUpdateBookEvent(Book addBookRequest, @Context String oldTitle);

        Event.Author toAuthor(Author author);

        @Mapping(target = "oldName", expression = "java(oldName)")
        Event.Author toUpdateAuthor(Author author, String oldName);

        Event.Genre toGenre(Genre genre);

        @Mapping(target = "oldName", expression = "java(oldName)")
        Event.Genre toUpdateGenre(Genre genre, String oldName);

    }