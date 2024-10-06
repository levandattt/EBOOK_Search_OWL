package org.ebook_searching.admin.mapper;

import lombok.Data;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.proto.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public Event.AddBookEvent toAddBookEvent(AddBookRequest request) {
        Event.AddBookEvent.Builder builder = Event.AddBookEvent.newBuilder();

        // Map fields from AddBookRequest to AddBookEvent
        builder.setTitle(request.getTitle())
                .setGenre(request.getGenre())
                .setPublisher(request.getPublisher())
                .setLanguage(request.getLanguage())
                .setAvgRatings(request.getAvgRatings().doubleValue())
                .setRatingsCount(request.getRatingsCount());

        // If authorIds are provided, map them
        if (request.getAuthorIds() != null) {
            builder.addAllAuthorIds(request.getAuthorIds());
        }

        return builder.build();
    }
}
