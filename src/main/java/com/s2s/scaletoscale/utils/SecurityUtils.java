package com.s2s.scaletoscale.utils;

import com.s2s.scaletoscale.models.response.UserProfile;
import com.s2s.scaletoscale.security.UserProfileDetails;
import com.s2s.scaletoscale.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUtils {

    @Autowired
    private UserProfileService userProfileService;

    public Optional<Authentication> getAuth(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth instanceof AnonymousAuthenticationToken)
            return Optional.empty();
        return Optional.of(auth);
    }

    public boolean isUserLoggedIn(){
        return getAuth().isPresent();
    }

    public Optional<UserProfileDetails> getLoggedInUser(){
        if(getAuth().isPresent())
            return Optional.of((UserProfileDetails) getAuth().get().getPrincipal());
        return Optional.empty();
    }

    public String getLoggedInUsername(){
        if (getLoggedInUser().isPresent())
            return getLoggedInUser().get().getUsername();

        return null;
    }

    public int getLoggedInUserId(){
        if (getLoggedInUser().isPresent())
            return getLoggedInUser().get().getUserProfile().getId();

        return -1;
    }

    public String getLoggedInUserFirstAndLastName(){
        String name = "";
        if(getLoggedInUser().isPresent()){
            UserProfile userProfile = userProfileService.getUserProfile(getLoggedInUsername());
            name = userProfile.getFirstName()+" "+userProfile.getLastName();
        }
        return name;
    }
}
