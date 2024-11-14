package com.nhnacademy.twojopingfront.bookset.publisher.controller;


import com.nhnacademy.twojopingfront.bookset.publisher.dto.request.PublisherRequestDto;
import com.nhnacademy.twojopingfront.bookset.publisher.dto.response.PublisherResponseDto;
import com.nhnacademy.twojopingfront.bookset.publisher.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    /**
     * 전체 출판사 목록을 조회하여 뷰에 전달
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 출판사 목록을 표시하는 HTML 뷰 이름
     */
    @GetMapping("/publishers")
    public String getAllPublishers(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   Model model) {
        Page<PublisherResponseDto> publishers = publisherService.getAllPublishers(page,size);
        model.addAttribute("publishers", publishers);
        return "bookset/publisher/publishers-list"; // 전체 출판사 목록을 표시할 템플릿 경로
    }


    /**
     * 새로운 출판사 등록 요청을 처리
     * @param publisherRequestDto 등록할 출판사 정보
     * @return 등록 후 전체 출판사 목록 페이지로 리다이렉트
     */
    @PostMapping("/register")
    public String registerPublisher(PublisherRequestDto publisherRequestDto) {
        publisherService.registerPublisher(publisherRequestDto);
        return "redirect:/publishers";
    }

    /**
     * 새로운 출판사를 생성하는 폼 페이지를 제공
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 출판사 생성 폼을 표시하는 HTML 뷰 이름
     */
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("publisher", new PublisherRequestDto(null));
        return "bookset/publisher/publisher-register"; // 등록 폼 페이지
    }


    /**
     * 출판사 수정 요청을 처리
     * @param id 수정할 출판사의 ID
     * @param publisherRequestDto 수정할 출판사 정보
     * @return 수정 후 전체 출판사 목록 페이지로 리다이렉트
     */
    @PatchMapping("/{id}")
    public String updatePublisher(@PathVariable("id") Long id, PublisherRequestDto publisherRequestDto) {
        publisherService.updatePublisher(id, publisherRequestDto);
        return "redirect:/publishers";
    }

    /**
     * 특정 출판사를 수정하는 폼 페이지를 제공
     * @param id 수정할 출판사의 ID
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 출판사 수정 폼을 표시하는 HTML 뷰 이름
     */
    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") Long id, Model model) {
        PublisherResponseDto publisher = publisherService.getPublisher(id); // 출판사 정보 조회
        model.addAttribute("publisher", publisher); // 뷰에 전달
        return "bookset/publisher/publisher-edit"; // 수정 폼 페이지
    }


    /**
     * 특정 출판사 삭제 요청을 처리
     * @param id 삭제할 출판사의 ID
     * @return 삭제 후 전체 출판사 목록 페이지로 리다이렉트
     */
    @DeleteMapping("/{id}")
    public String deletePublisher(@PathVariable("id") Long id) {
        publisherService.deletePublisher(id);
        return "redirect:/publishers";
    }

    /**
     * 특정 출판사를 삭제하는 폼 페이지를 제공
     * @param id 삭제할 출판사의 ID
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @return 출판사 삭제 폼을 표시하는 HTML 뷰 이름
     */
    @GetMapping("/{id}/delete")
    public String showDeletePage(@PathVariable("id") Long id, Model model) {
        PublisherResponseDto publisher = publisherService.getPublisher(id);
        model.addAttribute("publisher", publisher);
        return "bookset/publisher/publisher-delete";
    }
}