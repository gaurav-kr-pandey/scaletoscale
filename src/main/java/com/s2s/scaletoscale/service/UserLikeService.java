package com.s2s.scaletoscale.service;

import com.s2s.scaletoscale.models.response.UserLike;
import org.springframework.stereotype.Service;

public interface UserLikeService {
    public boolean getUserLike(int blogId);
    public int getTotalLikes(int blogId);
    public void toggleUserLike(int blogId);
}
