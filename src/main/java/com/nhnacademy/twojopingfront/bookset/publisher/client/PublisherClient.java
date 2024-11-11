package com.nhnacademy.twojopingfront.bookset.publisher.client;


import com.nhnacademy.twojopingfront.bookset.publisher.dto.request.PublisherRequestDto;
import com.nhnacademy.twojopingfront.bookset.publisher.dto.response.PublisherCreateResponseDto;
import com.nhnacademy.twojopingfront.bookset.publisher.dto.response.PublisherResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "publisher", url = "localhost:8082/api/v1/bookstore")
public interface PublisherClient {

    @PostMapping("/publishers")
    PublisherCreateResponseDto registerPublisher(PublisherRequestDto publisherRequestDto);

    @GetMapping("/publishers")
    Page<PublisherResponseDto> getAllPublishers(@RequestParam("page") int page, @RequestParam("size") int size);

    @GetMapping("/publisher/{id}")
    PublisherResponseDto getPublisher(@PathVariable("id")  Long id);

    @DeleteMapping("/publisher/{id}")
    void deletePublisher(@PathVariable("id")  Long id);

    @PutMapping("/publisher/{id}")
    PublisherResponseDto updatePublisher(@PathVariable("id")  Long id, @RequestBody PublisherRequestDto publisherRequestDto);
}
