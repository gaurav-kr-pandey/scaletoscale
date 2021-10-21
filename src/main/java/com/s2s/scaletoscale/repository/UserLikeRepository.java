package com.s2s.scaletoscale.repository;

import com.s2s.scaletoscale.entities.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Integer> {
}
