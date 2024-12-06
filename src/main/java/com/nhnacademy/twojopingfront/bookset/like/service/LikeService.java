package com.nhnacademy.twojopingfront.bookset.like.service;

import com.nhnacademy.twojopingfront.bookset.like.client.LikeClient;
import com.nhnacademy.twojopingfront.bookset.like.dto.LikeRequestDto;
import com.nhnacademy.twojopingfront.bookset.like.dto.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeClient likeClient;

    public LikeResponseDto setBookLike(LikeRequestDto request){
        return likeClient.setBookLike(request);
    }

    public Long getLikeCount(Long bookId){
        return likeClient.getLikeCount(bookId);
    }
}
