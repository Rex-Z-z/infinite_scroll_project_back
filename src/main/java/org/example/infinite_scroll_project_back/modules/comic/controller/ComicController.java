package org.example.infinite_scroll_project_back.modules.comic.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.infinite_scroll_project_back.modules.comic.model.Comic;
import org.example.infinite_scroll_project_back.modules.comic.model.Source;
import org.example.infinite_scroll_project_back.modules.comic.service.ComicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comics")
@RequiredArgsConstructor
public class ComicController {

    private final ComicService comicService;

    // --- Comic Endpoints ---

    @Operation(
            summary = "Get All Comics",
            description = "Retrieves a list of all comics including their associated source details."
    )
    @GetMapping
    public ResponseEntity<List<Comic>> getAllComics() {
        return ResponseEntity.ok(comicService.getAllComics());
    }

    @Operation(
            summary = "Get Comic by ID",
            description = "Retrieves a single comic by its ID, including its associated source details."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Comic> getComicById(@PathVariable Long id) {
        return ResponseEntity.ok(comicService.getComicById(id));
    }

    @Operation(
            summary = "Create Comic",
            description = "Creates a new comic. The request body should include the sourceId to associate the comic with an existing source."
    )
    @PostMapping
    public ResponseEntity<Comic> createComic(@RequestBody Comic comic) {
        return ResponseEntity.ok(comicService.createComic(comic));
    }

    @Operation(
            summary = "Update Comic",
            description = "Updates an existing comic by its ID. The request body can include any fields to be updated, including sourceId to change the associated source."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Comic> updateComic(@PathVariable Long id, @RequestBody Comic comic) {
        return ResponseEntity.ok(comicService.updateComic(id, comic));
    }

    @Operation(
            summary = "Delete Comic",
            description = "Deletes a comic by its ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComic(@PathVariable Long id) {
        comicService.deleteComic(id);
        return ResponseEntity.noContent().build();
    }

    // --- Source Endpoints (Nested for convenience) ---

    @Operation(
            summary = "Get All Sources",
            description = "Retrieves a list of all comic sources."
    )
    @GetMapping("/sources")
    public ResponseEntity<List<Source>> getAllSources() {
        return ResponseEntity.ok(comicService.getAllSources());
    }

    @Operation(
            summary = "Create Source",
            description = "Creates a new comic source. The request body should include the name and url of the source."
    )
    @PostMapping("/sources")
    public ResponseEntity<Source> createSource(@RequestBody Source source) {
        return ResponseEntity.ok(comicService.createSource(source));
    }

    @Operation(
            summary = "Delete Source",
            description = "Deletes a comic source by its ID. This will also delete all comics associated with this source."
    )
    @DeleteMapping("/sources/{id}")
    public ResponseEntity<Void> deleteSource(@PathVariable Long id) {
        comicService.deleteSource(id);
        return ResponseEntity.noContent().build();
    }
}