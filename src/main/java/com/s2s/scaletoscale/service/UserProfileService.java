package com.s2s.scaletoscale.service;

import com.s2s.scaletoscale.exception.UsernameNotAvailableException;
import com.s2s.scaletoscale.models.response.UserProfile;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserProfileService {
    UserProfile getUserProfile(String username);
    List<UserProfile> getAllUserProfile();
    UserProfile saveUserProfile(com.s2s.scaletoscale.models.request.UserProfile userProfile) throws UsernameNotAvailableException, ExecutionException;
    UserProfile getUserProfileById(int id);
    UserProfile updateUserProfile(com.s2s.scaletoscale.models.request.UserProfile user) throws ExecutionException;
}
