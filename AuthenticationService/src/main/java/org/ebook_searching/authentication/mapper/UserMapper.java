package org.ebook_searching.authentication.mapper;


import org.ebook_searching.authentication.dto.Profile;
import org.ebook_searching.authentication.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "userId", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "fullName", source = "profile.fullName")
    @Mapping(target = "gender", source = "profile.gender")
    @Mapping(target = "dateOfBirth", source = "profile.dateOfBirth")
    @Mapping(target = "setup", source = "profile.setup")
    @Mapping(target = "avatar", source = "profile.avatar")
    Profile toProfile(User user);
}
