package com.nhnacademy.twojopingfront.bookset.contributor.client;

import com.nhnacademy.twojopingfront.bookset.contributor.dto.request.ContributorRoleRequestDto;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorRoleResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ContributorRoleClient", url = "${gateway.base-url}")
public interface ContributorRoleClient {

    @PostMapping("/v1/bookstore/contributors/role")
    ResponseEntity<ContributorRoleResponseDto> createContributorRole(@RequestBody ContributorRoleRequestDto requestDto);

    @GetMapping("/v1/bookstore/contributors/role/{contributorRoleId}")
    ResponseEntity<ContributorRoleResponseDto> getContributorRole(@PathVariable Long contributorRoleId);

    @PutMapping("/v1/bookstore/contributors/role/{contributorRoleId}")
    ResponseEntity<ContributorRoleResponseDto> updateContributorRole(@PathVariable Long contributorRoleId,
                                                                     @RequestBody ContributorRoleRequestDto requestDto);

    @DeleteMapping("/v1/bookstore/contributors/role/{contributorRoleId}")
    ResponseEntity<Void> deleteContributorRole(@PathVariable Long contributorRoleId);

    @GetMapping("/v1/bookstore/contributors/role")
    ResponseEntity<List<ContributorRoleResponseDto>> getAllContributorRoles();
}
