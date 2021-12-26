package com.s2s.scaletoscale.repository;

import com.s2s.scaletoscale.entities.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Integer> {

    @Query(value = "SELECT liked FROM user_likes WHERE user_profile_id= :userId AND  blogs_id= :blogId", nativeQuery = true)
    boolean getUserLike(int userId, int blogId);
    @Query(value = "SELECT count(*) FROM user_likes WHERE blogs_id= :blogId AND liked IS true", nativeQuery = true)
    int getTotalLikes(int blogId);
    @Query(value = "SELECT * FROM user_likes WHERE user_profile_id= :userId AND blogs_id= :blogId", nativeQuery = true)
    Optional<UserLike> findByUserIdAndBlogId(int userId,int blogId);

}
