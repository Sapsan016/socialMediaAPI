package ru.gorbunov.social_media_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gorbunov.social_media_api.models.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM posts WHERE user_id = ? ORDER BY created  DESC offset ? LIMIT ?", nativeQuery = true)
    List<Post> getAllStudentsSortAsc(Long userId, Integer from, Integer size);
    @Query(value = "SELECT * FROM posts WHERE user_id = ? ORDER BY created  ASC offset ? LIMIT ?", nativeQuery = true)
    List<Post> getAllStudentsSortDesc(Long userId, Integer from, Integer size);
}
