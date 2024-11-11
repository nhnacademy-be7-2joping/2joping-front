package com.nhnacademy.twojopingfront.admin.wrap.controller;

import com.nhnacademy.twojopingfront.admin.wrap.dto.WrapRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.WrapResponseDto;
import com.nhnacademy.twojopingfront.admin.wrap.service.WrapService;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        return "admin/wrap/wrap"; // 이거 는 html
    }

    @PostMapping
    public String createWrap(@Valid WrapRequestDto requestDto) {
        wrapService.createWrap(requestDto);

        return "redirect:/admin/wraps";
    }

    @GetMapping("/{wrap-id}")
    public String getWrap(@PathVariable("wrap-id") Long wrapId, Model model) {
        WrapResponseDto responseDto = wrapService.getWrap(wrapId);
        model.addAttribute("wrap", responseDto); // 모델에 데이터 추가
        return "admin/wrap/wrap-details";

    }

    @GetMapping("/list")
    public String getAllWraps(Model model) {
        List<WrapResponseDto> wraps = wrapService.getAllWraps();
        model.addAttribute("wraps", wraps); // 모델에 전체 wrap 리스트 추가
        return "admin/wrap/wrap-list"; // 전체 목록을 표시할 HTML 템플릿
    }

    @GetMapping("/edit/{wrapId}") // 기존에 있던 데이터를 불러와서 채워넣기 위한 controller
    public String showEditForm(@PathVariable("wrapId") Long wrapId, Model model) {
        WrapResponseDto wrap = wrapService.getWrap(wrapId); // 수정할 데이터를 가져옴
        model.addAttribute("wrap", wrap); // 모델에 데이터 추가
        return "admin/wrap/wrap-edit"; // 수정 화면으로 이동
    }

    @PutMapping("/{wrap-id}")
    public String updateWrap(
            @PathVariable("wrap-id") Long wrapId,
            @RequestParam("name") String name,
            @RequestParam("wrapPrice") Integer wrapPrice,
            @RequestParam("isActive") Boolean isActive,
            RedirectAttributes redirectAttributes) {
        WrapRequestDto dto = new WrapRequestDto(name, wrapPrice, !Objects.isNull(isActive) && isActive);
        WrapResponseDto updatedResponseDto = wrapService.updateWrap(wrapId, dto);
        redirectAttributes.addFlashAttribute("message", "업데이트가 성공적으로 완료되었습니다.");
        return "redirect:/admin/wraps/list";
    }

    @DeleteMapping("/{wrap-id}")
    public String deleteWrap(@PathVariable("wrap-id") Long wrapId, RedirectAttributes redirectAttributes) {
        wrapService.deleteWrap(wrapId);
        redirectAttributes.addFlashAttribute("message", "포장 정책이 성공적으로 삭제되었습니다.");
        return "redirect:/admin/wraps/list";
    }
}


