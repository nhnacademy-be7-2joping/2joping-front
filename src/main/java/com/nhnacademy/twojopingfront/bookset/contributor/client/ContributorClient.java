package com.nhnacademy.twojopingfront.bookset.contributor.client;

import com.nhnacademy.twojopingfront.bookset.contributor.dto.request.ContributorRequestDto;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ContributorClient", url = "${gateway.base-url}")
public interface ContributorClient {

    @PostMapping("/v1/bookstore/contributors")
    ResponseEntity<ContributorResponseDto> createContributor(@RequestBody ContributorRequestDto requestDto);

    @GetMapping("/v1/bookstore/contributors/{contributorId}")
    ResponseEntity<ContributorResponseDto> getContributor(@PathVariable Long contributorId);

    @PutMapping("/v1/bookstore/contributors/{contributorId}")
    ResponseEntity<ContributorResponseDto> updateContributor(@PathVariable Long contributorId, @RequestBody ContributorRequestDto requestDto);

    @PutMapping("/v1/bookstore/contributors/{contributorId}/deactivate")
    ResponseEntity<Void> deactivateContributor(@PathVariable Long contributorId);

    @PutMapping("/v1/bookstore/contributors/{contributorId}/activate")
    ResponseEntity<Void> activateContributor(@PathVariable Long contributorId);

    @GetMapping("/v1/bookstore/contributors/active")
    ResponseEntity<List<ContributorResponseDto>> getActiveContributors();

    @GetMapping("/v1/bookstore/contributors")
    ResponseEntity<Page<ContributorResponseDto>> getAllContributors(@RequestParam int page, @RequestParam int size);
}
