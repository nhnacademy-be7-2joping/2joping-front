package com.nhnacademy.twojopingfront.bookset.contributor.controller;

import com.nhnacademy.twojopingfront.bookset.contributor.dto.request.ContributorRequestDto;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorResponseDto;
import com.nhnacademy.twojopingfront.bookset.contributor.service.ContributorService;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/contributors")
@RequiredArgsConstructor
public class ContributorController {
    private final ContributorService contributorService;

    @GetMapping
    public String getAllContributors(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size,
                                     Model model) {
        try {
            Page<ContributorResponseDto> contributors = contributorService.getAllContributors(page, size);
            model.addAttribute("contributors", contributors.getContent());
            model.addAttribute("page", contributors);
        } catch (CustomApiException ex) {
            model.addAttribute("errorMessage", ex.getErrorResponse().errorMessage());
        }
        return "admin/contributors/contributor-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("contributor", new ContributorRequestDto(null, "", null));
        return "admin/contributors/contributor-form";
    }

    @PostMapping
    public String createContributor(@ModelAttribute ContributorRequestDto requestDto,
                                    RedirectAttributes redirectAttributes) {
        try {
            contributorService.createContributor(requestDto);
            redirectAttributes.addFlashAttribute("message", "도서 기여자가 성공적으로 생성되었습니다.");
        } catch (CustomApiException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            return "redirect:/admin/contributors/create";
        }
        return "redirect:/admin/contributors";
    }

    @GetMapping("/{contributorId}/edit")
    public String showEditForm(@PathVariable Long contributorId, Model model, RedirectAttributes redirectAttributes) {
        try {
            ContributorResponseDto contributor = contributorService.getContributor(contributorId);
            model.addAttribute("contributor", contributor);
        } catch (CustomApiException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            return "redirect:/admin/contributors";
        }
        return "admin/contributors/contributor-form";
    }

    @PostMapping("/{contributorId}/edit")
    public String updateContributor(@PathVariable Long contributorId,
                                    @ModelAttribute ContributorRequestDto requestDto,
                                    RedirectAttributes redirectAttributes) {
        try {
            contributorService.updateContributor(contributorId, requestDto);
            redirectAttributes.addFlashAttribute("message", "도서 기여자가 성공적으로 수정되었습니다.");
        } catch (CustomApiException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            return "redirect:/admin/contributors/" + contributorId + "/edit";
        }
        return "redirect:/admin/contributors";
    }

    @PostMapping("/{contributorId}/deactivate")
    public String deactivateContributor(@PathVariable Long contributorId, RedirectAttributes redirectAttributes) {
        try {
            contributorService.deactivateContributor(contributorId);
            redirectAttributes.addFlashAttribute("message", "도서 기여자가 성공적으로 비활성화되었습니다.");
        } catch (CustomApiException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorResponse().errorMessage());
        }
        return "redirect:/admin/contributors";
    }

    @PostMapping("/{contributorId}/activate")
    public String activateContributor(@PathVariable Long contributorId, RedirectAttributes redirectAttributes) {
        try {
            contributorService.activateContributor(contributorId);
            redirectAttributes.addFlashAttribute("message", "도서 기여자가 성공적으로 활성화되었습니다.");
        } catch (CustomApiException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorResponse().errorMessage());
        }
        return "redirect:/admin/contributors";
    }
}
