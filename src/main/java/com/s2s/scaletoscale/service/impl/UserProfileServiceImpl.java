package com.s2s.scaletoscale.service.impl;

import com.s2s.scaletoscale.cache.OtpService;
import com.s2s.scaletoscale.exception.UsernameNotAvailableException;
import com.s2s.scaletoscale.models.response.UserProfile;
import com.s2s.scaletoscale.repository.UserProfileRepository;
import com.s2s.scaletoscale.service.UserProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserProfile getUserProfile(String username) {
        return modelMapper.map(userProfileRepository.findByEmail(username),UserProfile.class);
    }

    @Override
    public List<UserProfile> getAllUserProfile() {
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfileRepository.findAll().forEach(userProfile -> userProfiles.add(modelMapper.map(userProfile,UserProfile.class)));
        return userProfiles;
    }

    @Override
    public UserProfile saveUserProfile(com.s2s.scaletoscale.models.request.UserProfile userProfileRequest) throws UsernameNotAvailableException, ExecutionException {
        String username = userProfileRequest.getEmail();
        String otp = userProfileRequest.getOtp();
        String role = userProfileRequest.getRole();
        if(userProfileRepository.findByEmail(username)!=null)
            throw new UsernameNotAvailableException("Username/Email already taken");
        if(StringUtils.hasText(otp) && otpService.isCorrectOtp(username,otp)){
            userProfileRequest.setEmailVerified(true);
        }
        if(!StringUtils.hasText(role)){
            userProfileRequest.setRole("ROLE_STUDENT");
        }
        com.s2s.scaletoscale.entities.UserProfile userProfileEntity = userProfileRepository.save(modelMapper.map(userProfileRequest, com.s2s.scaletoscale.entities.UserProfile.class));
        return modelMapper.map(userProfileEntity,UserProfile.class);
    }

    @Override
    public UserProfile getUserProfileById(int id) {
        return modelMapper.map(userProfileRepository.getById(id),UserProfile.class);
    }
}
