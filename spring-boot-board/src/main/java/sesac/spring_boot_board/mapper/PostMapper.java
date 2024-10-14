package sesac.spring_boot_board.mapper;

import org.apache.ibatis.annotations.*;
import sesac.spring_boot_board.domain.Post;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("SELECT * FROM posts")
    List<Post> findAll();

    @Select("SELECT * FROM posts WHERE id = #{id}")
    Post findById(Long id);

    @Insert("INSERT INTO posts (writer, title, content) VALUES (#{writer}, #{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Post post);

    @Update("UPDATE posts SET writer = #{writer}, title = #{title}, content = #{content} WHERE id = #{id}")
    void update(Post post);

    @Delete("DELETE FROM posts WHERE id = #{id}")
    void delete(Long id);
}