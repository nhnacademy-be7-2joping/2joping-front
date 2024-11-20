package com.nhnacademy.twojopingfront.bookset.tag.service;

import com.nhnacademy.twojopingfront.bookset.tag.client.TagClient;
import com.nhnacademy.twojopingfront.bookset.tag.dto.TagRequestDto;
import com.nhnacademy.twojopingfront.bookset.tag.dto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagClient tagClient;

    public void createTag(TagRequestDto requestDto) {
        tagClient.createTag(requestDto);
    }

    public TagResponseDto getTag(Long tagId) {
        return tagClient.getTag(tagId).getBody();
    }

    public List<TagResponseDto> getAllTags() {
        return tagClient.getAllTags().getBody();
    }

    public TagResponseDto updateTag(Long tagId, TagRequestDto requestDto) {
        return tagClient.updateTag(tagId, requestDto).getBody();
    }

    public void deleteTag(Long tagId) {
        tagClient.deleteTag(tagId);
    }


}
