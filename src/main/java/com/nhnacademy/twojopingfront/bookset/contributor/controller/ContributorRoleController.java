package com.nhnacademy.twojopingfront.bookset.contributor.controller;

import com.nhnacademy.twojopingfront.bookset.contributor.dto.request.ContributorRoleRequestDto;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorRoleResponseDto;
import com.nhnacademy.twojopingfront.bookset.contributor.service.ContributorRoleService;
import com.nhnacademy.twojopingfront.common.error.exception.backServer.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/contributors/roles")
@RequiredArgsConstructor
public class ContributorRoleController {
    private final ContributorRoleService contributorRoleService;

    @GetMapping
    public String getAllContributorRoles(Model model) {
        try {
            List<ContributorRoleResponseDto> roles = contributorRoleService.getAllContributorRoles();
            model.addAttribute("roles", roles);
        } catch (CustomApiException ex) {
            model.addAttribute("errorMessage", ex.getErrorResponse().errorMessage());
        }
        return "admin/contributors/role-list";
    }

    @GetMapping("/create")
    public String showCreateRoleForm(Model model) {
        model.addAttribute("role", new ContributorRoleRequestDto(null, ""));
        return "admin/contributors/role-form";
    }

    @PostMapping
    public String createRole(@ModelAttribute ContributorRoleRequestDto requestDto, RedirectAttributes redirectAttributes) {
        try {
            contributorRoleService.createContributorRole(requestDto);
            redirectAttributes.addFlashAttribute("message", "도서 기여자 역할이 성공적으로 생성되었습니다.");
        } catch (CustomApiException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            return "redirect:/admin/contributors/roles/create";
        }
        return "redirect:/admin/contributors/roles";
    }

    @GetMapping("/{contributorRoleId}/edit")
    public String showEditRoleForm(@PathVariable Long contributorRoleId, Model model, RedirectAttributes redirectAttributes) {
        try {
            ContributorRoleResponseDto role = contributorRoleService.getContributorRole(contributorRoleId);
            model.addAttribute("role", role);
        } catch (CustomApiException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            return "redirect:/admin/contributors/roles";
        }
        return "admin/contributors/role-form";
    }

    @PostMapping("/{contributorRoleId}/edit")
    public String updateRole(@PathVariable Long contributorRoleId, @ModelAttribute ContributorRoleRequestDto requestDto,
                             RedirectAttributes redirectAttributes) {
        try {
            contributorRoleService.updateContributorRole(contributorRoleId, requestDto);
            redirectAttributes.addFlashAttribute("message", "도서 기여자 역할이 성공적으로 수정되었습니다.");
        } catch (CustomApiException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorResponse().errorMessage());
            return "redirect:/admin/contributors/roles/" + contributorRoleId + "/edit";
        }
        return "redirect:/admin/contributors/roles";
    }

    @PostMapping("/{contributorRoleId}/delete")
    public String deleteRole(@PathVariable Long contributorRoleId, RedirectAttributes redirectAttributes) {
        try {
            contributorRoleService.deleteContributorRole(contributorRoleId);
            redirectAttributes.addFlashAttribute("message", "도서 기여자 역할이 성공적으로 삭제되었습니다.");
        } catch (CustomApiException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorResponse().errorMessage());
        }
        return "redirect:/admin/contributors/roles";
    }
}
