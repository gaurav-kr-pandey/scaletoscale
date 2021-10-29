package com.s2s.scaletoscale.service;

import com.s2s.scaletoscale.models.response.UserProfile;

import java.util.List;

public interface UserProfileService {
    UserProfile getUserProfile(String username);
    List<UserProfile> getAllUserProfile();
    UserProfile saveUserProfile(com.s2s.scaletoscale.models.request.UserProfile userProfile);
    UserProfile getUserProfileById(int id);
}
