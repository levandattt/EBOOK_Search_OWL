package org.ebook_searching.authentication.mapper;

//
//@Mapper(componentModel = "spring", uses = {StringUtils.class, AuthorMapper.class})
//public interface BookMapper {
//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toSingleString")
//    @Mapping(target = "categories", source = "categories", qualifiedByName = "toSingleString")
//    Book toBook(AddBookRequest request);
//
//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringList")
//    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringList")
//    AddBookResponse toAddBookResponse(Book book);
//
//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toSingleString")
//    @Mapping(target = "categories", source = "categories", qualifiedByName = "toSingleString")
//    void updateBookFromRequest(@MappingTarget Book book, UpdateBookRequest request);
//
//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringList")
//    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringList")
//    UpdateBookResponse toUpdateBookResponse(Book book);
//
//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringList")
//    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringList")
//    GetBookResponse toGetBookResponse(Book book);
//}
