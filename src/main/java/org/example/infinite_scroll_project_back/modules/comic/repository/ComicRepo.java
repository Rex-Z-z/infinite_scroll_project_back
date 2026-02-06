package org.example.infinite_scroll_project_back.modules.comic.repository;

import org.apache.ibatis.annotations.*;
import org.example.infinite_scroll_project_back.modules.comic.model.Comic;
import java.util.List;
import java.util.Optional;

@Mapper
public interface ComicRepo {
    // 1. Get all comics with their source information
    @Select("SELECT c.*, s.id as s_id, s.name as s_name, s.icon as s_icon " +
            "FROM comics c " +
            "LEFT JOIN sources s ON c.source_id = s.id")
    @Results(id = "ComicResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "sourceId", column = "source_id"),
            @Result(property = "source.id", column = "s_id"),
            @Result(property = "source.name", column = "s_name"),
            @Result(property = "source.icon", column = "s_icon"),
            @Result(property = "externalId", column = "external_id"),
            @Result(property = "imageUrl", column = "image_url"),
            @Result(property = "altTitle", column = "alt_title"),
            @Result(property = "lastRead", column = "last_read"),
            @Result(property = "lastReadChapter", column = "last_read_chapter")
    })
    List<Comic> findAll();

    // 2. Get a comic by ID with its source information
    @Select("SELECT c.*, s.id as s_id, s.name as s_name, s.icon as s_icon " +
            "FROM comics c " +
            "LEFT JOIN sources s ON c.source_id = s.id " +
            "WHERE c.id = #{id}")
    @ResultMap("ComicResultMap")
    Optional<Comic> findById(Long id);

    // 3. Get comics by source ID
    @Insert("INSERT INTO comics(external_id, type, image_url, title, alt_title, description, " +
            "last_read, status, rating, chapters, last_read_chapter, tags, source_id) " +
            "VALUES(#{externalId}, #{type}, #{imageUrl}, #{title}, #{altTitle}, #{description}, " +
            "#{lastRead}, #{status}, #{rating}, #{chapters}, #{lastReadChapter}, #{tags}, #{sourceId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Comic comic);

    // 4. Update a comic by ID
    @Update("UPDATE comics SET external_id=#{externalId}, type=#{type}, image_url=#{imageUrl}, " +
            "title=#{title}, alt_title=#{altTitle}, description=#{description}, last_read=#{lastRead}, " +
            "status=#{status}, rating=#{rating}, chapters=#{chapters}, " +
            "last_read_chapter=#{lastReadChapter}, tags=#{tags}, source_id=#{sourceId} " +
            "WHERE id=#{id}")
    void update(Comic comic);

    // 5. Delete a comic by ID
    @Delete("DELETE FROM comics WHERE id = #{id}")
    void delete(Long id);
}