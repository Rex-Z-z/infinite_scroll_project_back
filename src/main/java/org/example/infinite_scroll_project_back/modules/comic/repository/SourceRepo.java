package org.example.infinite_scroll_project_back.modules.comic.repository;

import org.apache.ibatis.annotations.*;
import org.example.infinite_scroll_project_back.modules.comic.model.Source;
import java.util.List;
import java.util.Optional;

@Mapper
public interface SourceRepo {
    @Select("SELECT * FROM sources")
    List<Source> findAll();

    @Select("SELECT * FROM sources WHERE id = #{id}")
    Optional<Source> findById(Long id);

    @Insert("INSERT INTO sources(name, icon) VALUES(#{name}, #{icon})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Source source);

    @Update("UPDATE sources SET name=#{name}, icon=#{icon} WHERE id=#{id}")
    void update(Source source);

    @Delete("DELETE FROM sources WHERE id = #{id}")
    void delete(Long id);
}