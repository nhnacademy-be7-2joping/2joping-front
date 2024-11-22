package com.nhnacademy.twojopingfront.bookset.tag.controller;

import com.nhnacademy.twojopingfront.bookset.tag.dto.TagRequestDto;
import com.nhnacademy.twojopingfront.bookset.tag.dto.TagResponseDto;
import com.nhnacademy.twojopingfront.bookset.tag.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 *  Tag 컨트롤러
 *  @author : 박채연
 *  @date : 2024-11-17
 *
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tags")
public class TagController {
    private final TagService tagService;

    /**
     * 태그 리스트 페이지를 표시합니다.
     *
     * @param model 뷰에 데이터를 전달하기 위한 모델
     * @return 태그 리스트 페이지 이름
     */
    @GetMapping
    public String getAllTags(Model model) {
        List<TagResponseDto> tagList = tagService.getAllTags();
        model.addAttribute("tags", tagList);
        return "admin/tag/tag-list";
    }

    /**
     * 태그 생성 폼 페이지를 표시합니다.
     *
     * @return 태그 생성 페이지 이름
     */
    @GetMapping("create-tag")
    public String getCreateTagForm() {
        return "admin/tag/create-tag";
    }

    /**
     * 새로운 태그를 생성합니다.
     *
     * @param requestDto 태그 생성 요청 데이터
     * @return 태그 리스트 페이지로 리다이렉트
     */
    @PostMapping
    public String createTag(@Valid TagRequestDto requestDto) {
        tagService.createTag(requestDto);
        return "redirect:/admin/tags";
    }

    /**
     * 태그 수정 폼 페이지를 표시합니다.
     *
     * @param tagId 태그 ID
     * @param model 뷰에 데이터를 전달하기 위한 모델
     * @return 태그 수정 페이지 이름
     */
    @GetMapping("/edit/{tag-id}")
    public String getEditTagForm(@PathVariable("tag-id") Long tagId, Model model) {
        TagResponseDto tag = tagService.getTag(tagId);
        model.addAttribute("tag", tag);
        return "admin/tag/edit-tag";
    }

    /**
     * 태그 정보를 수정합니다.
     *
     * @param tagId 태그 ID
     * @param requestDto 수정할 태그 데이터
     * @return 태그 리스트 페이지로 리다이렉트
     */
    @PutMapping("/{tag-id}")
    public String updateTag(@PathVariable("tag-id") Long tagId, @Valid TagRequestDto requestDto) {
        tagService.updateTag(tagId, requestDto);
        return "redirect:/admin/tags";
    }

    /**
     * 태그를 삭제합니다.
     *
     * @param tagId 태그 ID
     * @return 태그 리스트 페이지로 리다이렉트
     */
    @DeleteMapping("/{tag-id}")
    public String deleteTag(@PathVariable("tag-id") Long tagId) {
        tagService.deleteTag(tagId);
        return "redirect:/admin/tags";
    }
}

