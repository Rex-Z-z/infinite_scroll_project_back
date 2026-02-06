package org.example.infinite_scroll_project_back.modules.comic.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.infinite_scroll_project_back.modules.comic.model.Comic;
import org.example.infinite_scroll_project_back.modules.comic.model.Source;
import org.example.infinite_scroll_project_back.modules.comic.repository.ComicRepo;
import org.example.infinite_scroll_project_back.modules.comic.repository.SourceRepo;
import org.example.infinite_scroll_project_back.modules.comic.service.ComicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComicServiceImpl implements ComicService {

    private final ComicRepo comicRepo;
    private final SourceRepo sourceRepo;

    // --- Comic Methods ---

    // 1. Get all comics with their source information
    @Override
    public List<Comic> getAllComics() {
        return comicRepo.findAll();
    }

    // 2. Get a comic by ID with its source information
    @Override
    public Comic getComicById(Long id) {
        return comicRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Comic not found"));
    }

    // 3. Create a new comic
    @Override
    @Transactional
    public Comic createComic(Comic comic) {
        // If lastRead is not provided but status implies reading, you might want to set it
        if (comic.getLastRead() == null) {
            comic.setLastRead(LocalDateTime.now());
        }
        comicRepo.save(comic);
        return comic;
    }

    // 4. Update an existing comic by ID
    @Override
    @Transactional
    public Comic updateComic(Long id, Comic comic) {
        Comic existing = getComicById(id);

        // Update fields
        existing.setExternalId(comic.getExternalId());
        existing.setType(comic.getType());
        existing.setImageUrl(comic.getImageUrl());
        existing.setTitle(comic.getTitle());
        existing.setAltTitle(comic.getAltTitle());
        existing.setDescription(comic.getDescription());
        existing.setLastRead(comic.getLastRead());
        existing.setStatus(comic.getStatus());
        existing.setRating(comic.getRating());
        existing.setChapters(comic.getChapters());
        existing.setLastReadChapter(comic.getLastReadChapter());
        existing.setTags(comic.getTags());
        existing.setSourceId(comic.getSourceId());

        comicRepo.update(existing);
        return existing;
    }

    // 5. Delete a comic by ID
    @Override
    public void deleteComic(Long id) {
        comicRepo.delete(id);
    }

    // --- Source Methods ---

    // Get all sources
    @Override
    public List<Source> getAllSources() {
        return sourceRepo.findAll();
    }

    // Create a new source
    @Override
    public Source createSource(Source source) {
        sourceRepo.save(source);
        return source;
    }

    // Delete a source by ID
    @Override
    public void deleteSource(Long id) {
        sourceRepo.delete(id);
    }
}