package com.nhnacademy.twojopingfront.admin.shipment.controller;

import com.nhnacademy.twojopingfront.admin.shipment.dto.request.ShipmentPolicyRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.ShipmentPolicyResponseDto;
import com.nhnacademy.twojopingfront.admin.shipment.service.ShipmentPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/shipment-policies")
public class ShipmentPolicyController {

    private final ShipmentPolicyService shipmentPolicyService;

    @GetMapping
    public String getAllShipmentPolicies(@PageableDefault(size = 10, sort = "categoryId") Pageable pageable, Model model) {
        Page<ShipmentPolicyResponseDto> policies = shipmentPolicyService.getAllShipmentPolicies(pageable.getPageNumber(), pageable.getPageSize());
        model.addAttribute("policies", policies);
        model.addAttribute("page", policies);
        return "admin/shipment/shipment-policy-list";
    }

    @GetMapping("/new")
    public String showAddShipmentPolicyForm(Model model) {
        model.addAttribute("policy", new ShipmentPolicyRequestDto(null, "", 0, true, 0));
        return "admin/shipment/shipment-policy-form";
    }

    @GetMapping("/{policyId}/edit")
    public String showEditShipmentPolicyForm(@PathVariable Long policyId, Model model) {
        ShipmentPolicyResponseDto policy = shipmentPolicyService.getShipmentPolicy(policyId);
        model.addAttribute("policy", policy);
        return "admin/shipment/shipment-policy-form";
    }

    @PostMapping
    public String createShipmentPolicy(ShipmentPolicyRequestDto requestDto, RedirectAttributes redirectAttributes) {
        shipmentPolicyService.createShipmentPolicy(requestDto);
        redirectAttributes.addFlashAttribute("message", "배송 정책이 성공적으로 추가되었습니다.");
        return "redirect:/admin/shipment-policies";
    }

    @PostMapping("/{policyId}/edit")
    public String updateShipmentPolicy(@PathVariable Long policyId, ShipmentPolicyRequestDto requestDto, RedirectAttributes redirectAttributes) {
        shipmentPolicyService.updateShipmentPolicy(policyId, requestDto);
        redirectAttributes.addFlashAttribute("message", "배송 정책이 성공적으로 수정되었습니다.");
        return "redirect:/admin/shipment-policies";
    }

    @PostMapping("/{policyId}/deactivate")
    public String deactivateShipmentPolicy(@PathVariable Long policyId, RedirectAttributes redirectAttributes) {
        shipmentPolicyService.deactivateShipmentPolicy(policyId);
        redirectAttributes.addFlashAttribute("message", "배송 정책이 성공적으로 비활성화되었습니다.");
        return "redirect:/admin/shipment-policies";
    }

    @PostMapping("/{policyId}/activate")
    public String activateShipmentPolicy(@PathVariable Long policyId, RedirectAttributes redirectAttributes) {
        shipmentPolicyService.activateShipmentPolicy(policyId);
        redirectAttributes.addFlashAttribute("message", "배송 정책이 성공적으로 활성화되었습니다.");
        return "redirect:/admin/shipment-policies";
    }
}
