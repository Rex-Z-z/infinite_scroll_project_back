package org.example.infinite_scroll_project_back.modules.comic.service;

import org.example.infinite_scroll_project_back.modules.comic.model.Comic;
import org.example.infinite_scroll_project_back.modules.comic.model.Source;
import java.util.List;

public interface ComicService {
    // Comic Operations
    List<Comic> getAllComics();
    Comic getComicById(Long id);
    Comic createComic(Comic comic);
    Comic updateComic(Long id, Comic comic);
    void deleteComic(Long id);

    // Source Operations (Simple CRUD for managing sources)
    List<Source> getAllSources();
    Source createSource(Source source);
    void deleteSource(Long id);
}