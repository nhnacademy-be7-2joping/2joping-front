package com.nhnacademy.twojopingfront.admin.wrap.controller;


import com.nhnacademy.twojopingfront.admin.wrap.dto.request.WrapDetailRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.request.WrapImageRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.request.WrapModifyRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.request.WrapUpdateDetailRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.response.WrapCreateResponseDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.response.WrapResponseDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.response.WrapUpdateResponseDto;
import com.nhnacademy.twojopingfront.admin.wrap.service.WrapService;
import com.nhnacademy.twojopingfront.review.dto.request.ReviewImageUploadRequestDto;
import com.nhnacademy.twojopingfront.review.dto.request.ReviewModifyDetailRequestDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewModifyResponseDto;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/wraps")
public class WrapController {
    private final WrapService wrapService;

    @GetMapping
    public String wrap() {
        return "admin/wrap/wrap";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createWrap(@ModelAttribute WrapDetailRequestDto wrapDetailRequestDto,
                             @RequestPart(value = "wrapImage", required = false) MultipartFile wrapImage) {
        WrapImageRequestDto wrapImageRequestDto = new WrapImageRequestDto(wrapImage);
        WrapCreateResponseDto wrapCreateResponseDto = wrapService.createWrap(wrapDetailRequestDto, wrapImageRequestDto);
        return "redirect:/admin/wraps/" + wrapCreateResponseDto.wrapId();
    }

    @GetMapping("/{wrap-id}")
    public String getWrap(@PathVariable("wrap-id") Long wrapId, Model model) {
        WrapUpdateResponseDto wrapUpdateResponseDto = wrapService.getWrap(wrapId);
        model.addAttribute("wrap", wrapUpdateResponseDto); // 모델에 데이터 추가
        return "admin/wrap/wrap-details";
    }

    @GetMapping("/list")
    public String findAllByIsActiveTrue(Model model) {
        List<WrapUpdateResponseDto> wraps = wrapService.findAllByIsActiveTrue();
        model.addAttribute("wraps", wraps); // 모델에 전체 wrap 리스트 추가
        return "admin/wrap/wrap-list"; // 전체 목록을 표시할 HTML 템플릿
    }

    @GetMapping("/edit/{wrap-id}") // 기존에 있던 데이터를 불러와서 채워넣기 위한 controller
    public String showEditForm(@PathVariable("wrap-id") Long wrapId, Model model) {
        WrapUpdateResponseDto wrap = wrapService.getWrap(wrapId);
        model.addAttribute("wrap", wrap);
        return "admin/wrap/wrap-edit";
    }

    @PutMapping("/{wrap-id}")
    public String updateWrap(
            @PathVariable("wrap-id") Long wrapId,
            @RequestParam("name") String name,
            @RequestParam("wrapPrice") Integer wrapPrice,
            @RequestParam("isActive") Boolean isActive,
            RedirectAttributes redirectAttributes) {
        WrapModifyRequestDto dto = new WrapModifyRequestDto(name, wrapPrice, !Objects.isNull(isActive) && isActive);
        WrapResponseDto updatedResponseDto = wrapService.updateWrap(wrapId, dto);
        redirectAttributes.addFlashAttribute("message", "업데이트가 성공적으로 완료되었습니다.");
        return "redirect:/admin/wraps/list";
    }
}


