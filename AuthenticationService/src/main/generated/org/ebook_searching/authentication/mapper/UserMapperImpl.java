package org.ebook_searching.authentication.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.ebook_searching.authentication.dto.Profile;
import org.ebook_searching.authentication.model.User;
import org.ebook_searching.authentication.model.UserProfile;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-12T17:06:12+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public Profile toProfile(User user) {
        if ( user == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setUserId( user.getId() );
        profile.setUsername( user.getUsername() );
        profile.setEmail( user.getEmail() );
        profile.setFullName( userProfileFullName( user ) );
        profile.setGender( userProfileGender( user ) );
        LocalDate dateOfBirth = userProfileDateOfBirth( user );
        if ( dateOfBirth != null ) {
            profile.setDateOfBirth( DateTimeFormatter.ISO_LOCAL_DATE.format( dateOfBirth ) );
        }
        Boolean setup = userProfileSetup( user );
        if ( setup != null ) {
            profile.setSetup( setup );
        }

        return profile;
    }

    private String userProfileFullName(User user) {
        if ( user == null ) {
            return null;
        }
        UserProfile profile = user.getProfile();
        if ( profile == null ) {
            return null;
        }
        String fullName = profile.getFullName();
        if ( fullName == null ) {
            return null;
        }
        return fullName;
    }

    private String userProfileGender(User user) {
        if ( user == null ) {
            return null;
        }
        UserProfile profile = user.getProfile();
        if ( profile == null ) {
            return null;
        }
        String gender = profile.getGender();
        if ( gender == null ) {
            return null;
        }
        return gender;
    }

    private LocalDate userProfileDateOfBirth(User user) {
        if ( user == null ) {
            return null;
        }
        UserProfile profile = user.getProfile();
        if ( profile == null ) {
            return null;
        }
        LocalDate dateOfBirth = profile.getDateOfBirth();
        if ( dateOfBirth == null ) {
            return null;
        }
        return dateOfBirth;
    }

    private Boolean userProfileSetup(User user) {
        if ( user == null ) {
            return null;
        }
        UserProfile profile = user.getProfile();
        if ( profile == null ) {
            return null;
        }
        Boolean setup = profile.getSetup();
        if ( setup == null ) {
            return null;
        }
        return setup;
    }
}
