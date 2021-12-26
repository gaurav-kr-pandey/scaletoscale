package com.s2s.scaletoscale.service.impl;

import com.s2s.scaletoscale.entities.UserLike;
import com.s2s.scaletoscale.entities.UserProfile;
import com.s2s.scaletoscale.repository.BlogRepository;
import com.s2s.scaletoscale.repository.UserLikeRepository;
import com.s2s.scaletoscale.security.UserProfileDetails;
import com.s2s.scaletoscale.service.BlogService;
import com.s2s.scaletoscale.service.UserLikeService;
import com.s2s.scaletoscale.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserLikeServiceImpl implements UserLikeService {

    @Autowired
    private UserLikeRepository userLikeRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public boolean getUserLike(int blogId) {
        Optional<UserProfileDetails> userProfileDetails = securityUtils.getLoggedInUser();
        if (userProfileDetails.isPresent()) {
            UserProfile userProfile = userProfileDetails.get().getUserProfile();
            Optional<UserLike> userLikeOptional = isUserLikeExists(userProfile.getId(),blogId);
            UserLike userLike;
            if (userLikeOptional.isPresent()) {
                userLike = userLikeOptional.get();
                return userLike.getLiked();
            }
        }
        return false;
    }

    @Override
    public int getTotalLikes(int blogId) {
        return userLikeRepository.getTotalLikes(blogId);
    }

    @Transactional
    @Override
    public void toggleUserLike(int blogId) {
        Optional<UserProfileDetails> userProfileDetails = securityUtils.getLoggedInUser();
        if (userProfileDetails.isPresent()) {
            UserProfile userProfile = userProfileDetails.get().getUserProfile();
            Optional<UserLike> userLikeOptional = isUserLikeExists(userProfile.getId(),blogId);
            UserLike userLike;
            if (userLikeOptional.isPresent()) {
                userLike = userLikeOptional.get();
                userLike.setLiked(!userLike.getLiked());
            } else {
                userLike = new UserLike();
                userLike.setUserProfile(userProfile);
                userLike.setLiked(true);
                userLike.setBlog(blogRepository.getById(blogId));
            }
            userLikeRepository.save(userLike);
        }
    }

    private Optional<UserLike> isUserLikeExists(int userId, int blogId){
        return userLikeRepository.findByUserIdAndBlogId(userId, blogId);
    }
}
