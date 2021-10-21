package com.s2s.scaletoscale.repository;

import com.s2s.scaletoscale.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    UserProfile findByEmail(String email);
}
