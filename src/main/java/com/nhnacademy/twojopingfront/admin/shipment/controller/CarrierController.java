package com.nhnacademy.twojopingfront.admin.shipment.controller;

import com.nhnacademy.twojopingfront.admin.shipment.dto.request.CarrierRequestDto;
import com.nhnacademy.twojopingfront.admin.shipment.dto.response.CarrierResponseDto;
import com.nhnacademy.twojopingfront.admin.shipment.service.CarrierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/carriers")
public class CarrierController {

    private final CarrierService carrierService;

    @GetMapping
    public String getAllCarriers(Model model) {
        List<CarrierResponseDto> carriers = carrierService.getAllCarriers();
        model.addAttribute("carriers", carriers);
        return "admin/shipment/carrier-list";
    }

    @GetMapping("/new")
    public String showAddCarrierForm(Model model) {
        model.addAttribute("carrier",new CarrierRequestDto(null, "", "", "", ""));
        return "admin/shipment/carrier-form";
    }

    @GetMapping("/{carrierId}/edit")
    public String showEditCarrierForm(@PathVariable Long carrierId, Model model) {
        CarrierResponseDto carrier = carrierService.getCarrier(carrierId);
        model.addAttribute("carrier", carrier);
        return "admin/shipment/carrier-form";
    }

    @PostMapping
    public String createCarrier(CarrierRequestDto carrierRequestDto, RedirectAttributes redirectAttributes) {
        carrierService.createCarrier(carrierRequestDto);
        redirectAttributes.addFlashAttribute("message", "배송업체가 성공적으로 추가되었습니다.");
        return "redirect:/admin/carriers";
    }

    @PostMapping("/{carrierId}/edit")
    public String updateCarrier(@PathVariable Long carrierId, CarrierRequestDto carrierRequestDto, RedirectAttributes redirectAttributes) {
        carrierService.updateCarrier(carrierId, carrierRequestDto);
        redirectAttributes.addFlashAttribute("message", "배송업체가 성공적으로 수정되었습니다.");
        return "redirect:/admin/carriers";
    }

    @PostMapping("/{carrierId}/delete")
    public String deleteCarrier(@PathVariable Long carrierId, RedirectAttributes redirectAttributes) {
        carrierService.deleteCarrier(carrierId);
        redirectAttributes.addFlashAttribute("message", "배송업체가 성공적으로 삭제되었습니다.");
        return "redirect:/admin/carriers";
    }
}
