package org.example.infinite_scroll_project_back.modules.comic.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comic {
    private Long id;
    private String externalId;
    private String type;
    private String imageUrl;
    private String title;
    private String altTitle;
    private String description;
    private LocalDateTime lastRead;
    private String status;
    private String rating;
    private Integer chapters;
    private Integer lastReadChapter;
    private String tags; // Stored as "Action, Adventure"

    // Relationship
    private Long sourceId; // For saving
    private Source source; // For fetching/displaying
}