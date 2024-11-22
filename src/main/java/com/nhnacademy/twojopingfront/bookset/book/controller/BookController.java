package com.nhnacademy.twojopingfront.bookset.book.controller;

import com.nhnacademy.twojopingfront.bookset.book.dto.request.BookCreateHtmlRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.ImageUploadRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookCreateResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.service.BookService;
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

import java.util.List;


@Controller
@RequestMapping()
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/admin/books/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createBook(@ModelAttribute BookCreateHtmlRequestDto bookCreateHtmlRequestDto,
                             @RequestPart(value = "thumbnailImage", required = false) MultipartFile thumbnailImage,
                             @RequestPart(value = "detailImage", required = false) MultipartFile detailImage,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        try {
            ImageUploadRequestDto imageUploadRequestDto = new ImageUploadRequestDto(thumbnailImage, detailImage);
            BookCreateResponseDto response = bookService.createBook(bookCreateHtmlRequestDto, imageUploadRequestDto);

            // admin-get-booklist 템플릿에 필요한 데이터 설정
            int page = 0; // 기본 페이지 번호
            int size = 10; // 기본 페이지 크기
            Page<BookSimpleResponseDto> books = bookService.getAllBooks(page, size); // 도서 목록 가져오기
            model.addAttribute("books", books);
            model.addAttribute("currentPath", "/admin/books/get");

            // 성공적으로 등록되었으면 템플릿으로 이동
            // 리턴 경로는 임시
            if (response != null) {
                return "bookset/book/admin-get-booklist";
            } else {
                return "bookset/book/admin-get-booklist"; // 실패 시에도 동일한 템플릿 반환
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            // 예외 발생 시에도 데이터를 설정한 뒤 템플릿 반환
            int page = 0; // 기본 페이지 번호
            int size = 10; // 기본 페이지 크기
            Page<BookSimpleResponseDto> books = bookService.getAllBooks(page, size); // 도서 목록 가져오기
            model.addAttribute("books", books);
            model.addAttribute("currentPath", "/admin/books/get");

            return "bookset/book/admin-get-booklist";
        }
    }

    @GetMapping("/admin/books/register")
    public String showBookRegisterForm(Model model) {
        List<TagResponseDto> tagList = bookService.getAllTags();
        model.addAttribute("tags", tagList);
        List<PublisherResponseDto> publisherList = bookService.getAllPublishersForRegister();
        model.addAttribute("publishers", publisherList);
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
