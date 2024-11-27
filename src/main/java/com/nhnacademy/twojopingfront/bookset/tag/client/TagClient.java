package com.nhnacademy.twojopingfront.bookset.tag.client;

import com.nhnacademy.twojopingfront.bookset.tag.dto.TagRequestDto;
import com.nhnacademy.twojopingfront.bookset.tag.dto.TagResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "AdminTagClient", url = "${gateway.base-url}")
public interface TagClient {
    @PostMapping("/v1/tags")
    ResponseEntity<TagResponseDto> createTag(@Valid @RequestBody TagRequestDto requestDto);

    @GetMapping("/v1/tags/{tag-id}")
    ResponseEntity<TagResponseDto> getTag(@PathVariable("tag-id") Long tagId);

    @GetMapping("/v1/tags")
    ResponseEntity<List<TagResponseDto>> getAllTags();

    @PutMapping("/v1/tags/{tag-id}")
    ResponseEntity<TagResponseDto> updateTag(
            @PathVariable("tag-id") Long tagId,
            @Valid @RequestBody TagRequestDto requestDto
    );

    @DeleteMapping("/v1/tags/{tag-id}")
    ResponseEntity<Void> deleteTag(@PathVariable("tag-id") Long tagId);
}