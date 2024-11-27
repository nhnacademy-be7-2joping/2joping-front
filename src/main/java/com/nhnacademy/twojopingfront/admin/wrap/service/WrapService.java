package com.nhnacademy.twojopingfront.admin.wrap.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.admin.wrap.client.AdminWrapClient;
import com.nhnacademy.twojopingfront.admin.wrap.dto.request.*;
import com.nhnacademy.twojopingfront.admin.wrap.dto.response.WrapCreateResponseDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.response.WrapResponseDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.response.WrapUpdateResponseDto;
import com.nhnacademy.twojopingfront.common.error.exception.image.ImageUploadException;
import com.nhnacademy.twojopingfront.review.dto.request.ReviewImageUploadRequestDto;
import com.nhnacademy.twojopingfront.review.dto.request.ReviewImageUrlRequestDto;
import com.nhnacademy.twojopingfront.review.dto.request.ReviewModifyDetailRequestDto;
import com.nhnacademy.twojopingfront.review.dto.request.ReviewModifyRequestDto;
import com.nhnacademy.twojopingfront.review.dto.response.ReviewModifyResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WrapService {
    private final AdminWrapClient wrapClient;
    private final RestTemplate restTemplate = new RestTemplate();

    public WrapCreateResponseDto createWrap(WrapDetailRequestDto wrapDetailRequestDto, WrapImageRequestDto wrapImageRequestDto) {
        String wrapImageUrl = saveImage(wrapImageRequestDto.wrapImage());
        WrapImageUrlRequestDto wrapImageUrlRequestDto = new WrapImageUrlRequestDto(wrapImageUrl);
        return wrapClient.createWrap(new WrapRequestDto(wrapDetailRequestDto,wrapImageUrlRequestDto));
    }

    public WrapUpdateResponseDto getWrap(Long wrapId) {
        return wrapClient.getWrap(wrapId);
    }

    public List<WrapUpdateResponseDto> findAllByIsActiveTrue() {
        return wrapClient.findAllByIsActiveTrue();
    }

    public WrapResponseDto updateWrap(Long wrapId, WrapModifyRequestDto wrapRequestDto) {
        return wrapClient.updateWrap(wrapId, wrapRequestDto).getBody();
    }

    public String saveImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }

        String uploadUrl = null;

        try {
            String folderPath = "wrap/";

            InputStream inputStream = image.getInputStream();
            String fileName = UUID.randomUUID().toString() + ".jpg";
            uploadUrl = "https://api-image.nhncloudservice.com/image/v2.0/appkeys/rUN43QEwj1P6jThk/images?path=/ejoping/book/" + folderPath + fileName;

            RequestCallback requestCallback = request -> {
                request.getHeaders().add("Content-Type", "multipart/form-data");
                request.getHeaders().add("Authorization", "I1XLVufp");
                IOUtils.copy(inputStream, request.getBody());
            };

            String responseBody = restTemplate.execute(uploadUrl, HttpMethod.PUT, requestCallback, response -> {
                InputStream body = response.getBody();
                if (body != null) {
                    return new String(body.readAllBytes(), StandardCharsets.UTF_8);
                }
                return null;
            });

            if (responseBody != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);
                return rootNode.path("file").path("url").asText();
            }

        } catch (IOException e) {
            throw new ImageUploadException();
        }
        return null;
    }
}

