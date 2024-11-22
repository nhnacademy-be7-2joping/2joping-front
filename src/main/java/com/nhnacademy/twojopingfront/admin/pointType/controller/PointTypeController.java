package com.nhnacademy.twojopingfront.admin.pointType.controller;

import com.nhnacademy.twojopingfront.admin.pointType.dto.PointTypeRequestDto;
import com.nhnacademy.twojopingfront.admin.pointType.dto.PointTypeResponseDto;
import com.nhnacademy.twojopingfront.admin.pointType.service.PointTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Point Type 컨트롤러
 *  @author : 박채연
 *  @date : 2024-11-17
 *
 */
@Controller
@RequestMapping("/admin/points")
@RequiredArgsConstructor
public class PointTypeController {
    private final PointTypeService pointTypeService;


    /**
     * 포인트 타입 목록 조회
     *
     * @param model 뷰로 데이터를 전달하기 위한 모델
     * @return 포인트 타입 목록 페이지
     */
    @GetMapping
    public String getAllActivePointTypes(Model model) {
        List<PointTypeResponseDto> pointTypes = pointTypeService.getAllActivePointTypes(); // 서비스 호출
        model.addAttribute("pointTypes", pointTypes);
        return "admin/point-type/point-type-list";
    }

    /**
     * 포인트 타입 생성 폼 페이지
     *
     * @return 포인트 타입 생성 페이지
     */
    @GetMapping("/create")
    public String showCreateForm() {
        return "admin/point-type/create-point-type";
    }

    /**
     * 포인트 타입 생성
     *
     * @param requestDto 생성 요청 DTO
     * @return 포인트 타입 목록 페이지로 리다이렉트
     */
    @PostMapping
    public String createPointType(PointTypeRequestDto requestDto) {
        pointTypeService.createPointType(requestDto);
        return "redirect:/admin/points";
    }

    /**
     * 포인트 타입 수정 폼 페이지
     *
     * @param typeId 포인트 타입 ID
     * @param model 뷰로 데이터를 전달하기 위한 모델
     * @return 포인트 타입 수정 페이지
     */
    @GetMapping("/edit/{type-id}")
    public String showEditForm(@PathVariable("type-id") Long typeId, Model model) {
        PointTypeResponseDto pointType = pointTypeService.getPointTypeById(typeId);
        model.addAttribute("pointType", pointType);
        return "admin/point-type/edit-point-type";
    }

    /**
     * 포인트 타입 수정
     *
     * @param typeId 포인트 타입 ID
     * @return 포인트 타입 목록 페이지로 리다이렉트
     */
    @PutMapping("/edit/{type-id}")
    public String updatePointType(
            @PathVariable("type-id") Long typeId,
            @ModelAttribute @Valid PointTypeRequestDto request) {
        pointTypeService.updatePointType(typeId, request);
        return "redirect:/admin/points";
    }

}
