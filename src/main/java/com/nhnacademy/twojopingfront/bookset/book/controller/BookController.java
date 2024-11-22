package com.nhnacademy.twojopingfront.bookset.book.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.BookCreateHtmlRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.ImageUploadRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookCreateResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.service.BookService;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorNameRoleResponseDto;
import com.nhnacademy.twojopingfront.bookset.publisher.dto.response.PublisherResponseDto;
import com.nhnacademy.twojopingfront.bookset.tag.dto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping()
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @PostMapping(value = "/admin/books/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createBook(@RequestParam("contributorList") String contributorListJson,
                             @ModelAttribute BookCreateHtmlRequestDto bookCreateHtmlRequestDto,
                             @RequestPart(value = "thumbnailImage", required = false) MultipartFile thumbnailImage,
                             @RequestPart(value = "detailImage", required = false) MultipartFile detailImage) {
        try {
            String contributorList = contributorListJson;

            // DTO에 기여자 리스트 추가
            BookCreateHtmlRequestDto updatedDto = new BookCreateHtmlRequestDto(
                    bookCreateHtmlRequestDto.publisherName(),
                    bookCreateHtmlRequestDto.title(),
                    bookCreateHtmlRequestDto.description(),
                    bookCreateHtmlRequestDto.publishedDate(),
                    bookCreateHtmlRequestDto.isbn(),
                    bookCreateHtmlRequestDto.retailPrice(),
                    bookCreateHtmlRequestDto.sellingPrice(),
                    bookCreateHtmlRequestDto.giftWrappable(),
                    bookCreateHtmlRequestDto.isActive(),
                    bookCreateHtmlRequestDto.remainQuantity(),
                    contributorList,
                    bookCreateHtmlRequestDto.category(),
                    bookCreateHtmlRequestDto.tagList()
            );

            // 이미지 처리
            ImageUploadRequestDto imageUploadRequestDto = new ImageUploadRequestDto(thumbnailImage, detailImage);
            bookService.createBook(updatedDto, imageUploadRequestDto);

            // 리다이렉트 경로로 이동
            return "redirect:/admin/books/get";
        } catch (Exception ex) {
            ex.printStackTrace();
            // 오류 발생 시에도 리다이렉트
            return "redirect:/admin/books/get";
        }
    }

    @GetMapping("/admin/books/register")
    public String showBookRegisterForm(Model model) {
        List<TagResponseDto> tagList = bookService.getAllTags();
        model.addAttribute("tags", tagList);
        List<PublisherResponseDto> publisherList = bookService.getAllPublishersForRegister();
        model.addAttribute("publishers", publisherList);
        List<ContributorNameRoleResponseDto> contributorList = bookService.getActiveContributors();
        model.addAttribute("contributors", contributorList);
        return "bookset/book/book-register";
    }

    @GetMapping("/books/get")
    public String getAllBooks(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {
        Page<BookSimpleResponseDto> books = bookService.getAllBooks(page, size);
        model.addAttribute("books", books);
        model.addAttribute("currentPath", "/books/get");
        return "bookset/book/get-booklist";
    }

    /**
     * 관리자 용 도서 조회로 가기 위해서 추가
     *
     */
    @GetMapping("/admin/books/get")
    public String adminGetAllBooks(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   Model model) {
        Page<BookSimpleResponseDto> books = bookService.getAllBooks(page, size);
        model.addAttribute("books", books);
        model.addAttribute("currentPath", "/admin/books/get");
        return "bookset/book/admin-get-booklist";
    }


    @GetMapping("/books/get/category/{categoryId}")
    public String getBooksByCategoryId(@PathVariable Long categoryId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       Model model) {
        Page<BookSimpleResponseDto> books = bookService.getBooksByCategoryId(categoryId, page, size);
        model.addAttribute("books", books);
        model.addAttribute("currentPath", "/books/get/category/" + categoryId);
        return "bookset/book/get-booklist";
    }

    @GetMapping("/books/get/contributor/{contributorId}")
    public String getBooksByContributorId(@PathVariable Long contributorId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       Model model) {
        Page<BookSimpleResponseDto> books = bookService.getBooksByContributorId(contributorId, page, size);
        model.addAttribute("books", books);
        model.addAttribute("currentPath", "/books/get/contributor/" + contributorId);
        return "bookset/book/get-booklist";
    }

    @GetMapping("/books/{bookId}")
    public String getBookByBookId(@PathVariable Long bookId, Model model) {
        BookResponseDto books = bookService.getBookById(bookId);
        model.addAttribute("books", books);
        return "bookset/book/bookdetails";
    }
}
