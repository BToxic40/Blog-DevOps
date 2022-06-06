package ru.learn.learnSpring.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.learn.learnSpring.model.Tag2post;

import java.util.List;

public interface Tag2postRepository extends CrudRepository<Tag2post, Integer> {
    List<Tag2post> findAllByOrderByIdDesc();
    Tag2post findAllById(int idTag);

    @Query(value="SELECT COUNT(post_id) as count FROM tag2post group by tag_id", nativeQuery = true)
    List<Integer> findCountTag();

    @Query(value="SELECT COUNT(post_id) / ?1 FROM tag2post group by tag_id", nativeQuery = true)
    List<Double> findWeightTag(float count);

    @Query(value="SELECT MAX(count) FROM (SELECT COUNT(post_id) / ?1 as count FROM tag2post group by tag_id) a", nativeQuery = true)
    Double findMaxWeightTag(float count);

    @Query(value="SELECT ?1 / COUNT(post_id) FROM tag2post group by tag_id", nativeQuery = true)
    List<Double> findKoefTag(double maxWeightTag);

    @Query(value="SELECT tag_id FROM tag2post where post_id=?1  group by tag_id", nativeQuery = true)
    List<Integer> findTagsByPostId(int postId);

}
