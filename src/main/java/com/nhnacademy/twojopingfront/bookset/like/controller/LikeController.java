package com.nhnacademy.twojopingfront.bookset.like.controller;

import com.nhnacademy.twojopingfront.bookset.like.dto.LikeRequestDto;
import com.nhnacademy.twojopingfront.bookset.like.dto.LikeResponseDto;
import com.nhnacademy.twojopingfront.bookset.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/add/likes")
    public LikeResponseDto addLike(@RequestBody LikeRequestDto request){
        log.debug("method start");
        log.debug("request data: {}", request.bookId());
        return likeService.setBookLike(request);
    }

}
