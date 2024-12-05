package com.nhnacademy.twojopingfront.bookset.book.controller;

import com.nhnacademy.twojopingfront.admin.shipment.dto.response.ShipmentPolicyResponseDto;
import com.nhnacademy.twojopingfront.admin.shipment.service.ShipmentPolicyService;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.BookCreateHtmlRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.BookUpdateHtmlRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.request.ImageUploadRequestDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookAdminSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookSimpleResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.dto.response.BookUpdateResponseDto;
import com.nhnacademy.twojopingfront.bookset.book.service.BookService;
import com.nhnacademy.twojopingfront.bookset.category.dto.response.CategoryResponseDto;
import com.nhnacademy.twojopingfront.bookset.contributor.dto.response.ContributorNameRoleResponseDto;
import com.nhnacademy.twojopingfront.bookset.publisher.dto.response.PublisherResponseDto;
import com.nhnacademy.twojopingfront.bookset.tag.dto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final ShipmentPolicyService shipmentPolicyService;
    @PostMapping(value = "/admin/books/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createBook(@RequestParam("contributorList") String contributorListJson,
                             @ModelAttribute BookCreateHtmlRequestDto bookCreateHtmlRequestDto,
                             @RequestPart(value = "thumbnailImage", required = false) MultipartFile thumbnailImage,
                             @RequestPart(value = "detailImage", required = false) MultipartFile detailImage,
                             RedirectAttributes redirectAttributes) {
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
                contributorListJson,
                bookCreateHtmlRequestDto.topCategoryId(),
                bookCreateHtmlRequestDto.middleCategoryId(),
                bookCreateHtmlRequestDto.bottomCategoryId(),
                bookCreateHtmlRequestDto.tagList()
        );

        ImageUploadRequestDto imageUploadRequestDto = new ImageUploadRequestDto(thumbnailImage, detailImage);
        bookService.createBook(updatedDto, imageUploadRequestDto);

        redirectAttributes.addFlashAttribute("message", "도서가 성공적으로 생성되었습니다.");
        return "redirect:/admin/books/get";
    }

    @GetMapping("/admin/books/register")
    public String showBookRegisterForm(Model model) {
        List<TagResponseDto> tagList = bookService.getAllTags().getBody();
        model.addAttribute("tags", tagList);
        List<PublisherResponseDto> publisherList = bookService.getAllPublishersForRegister();
        model.addAttribute("publishers", publisherList);
        List<ContributorNameRoleResponseDto> contributorList = bookService.getActiveContributors();
        model.addAttribute("contributors", contributorList);
        List<CategoryResponseDto> topCategories = bookService.getTopCategories();
        model.addAttribute("topCategories", topCategories);
        return "bookset/book/book-register";
    }

    @GetMapping(value = "/admin/api/categories/{categoryId}/children", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CategoryResponseDto> getChildCategories(@PathVariable Long categoryId) {
        return bookService.getChildCategories(categoryId);
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
        Page<BookAdminSimpleResponseDto> books = bookService.adminGetAllBooks(page, size);
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
        List<ShipmentPolicyResponseDto> shipmentPolicies = shipmentPolicyService.getAllIsActiveShipmentPolicies();
        model.addAttribute("books", books);
        model.addAttribute("shipmentPolicies", shipmentPolicies);
        return "bookset/book/bookdetails";
    }

    @GetMapping("/admin/books/modify/{bookId}")
    public String showBookModifyForm(@PathVariable Long bookId, Model model) {
        BookUpdateResponseDto bookData = bookService.getUpdateBookById(bookId);
        model.addAttribute("book", bookData);

        List<PublisherResponseDto> publisherList = bookService.getAllPublishersForRegister();
        model.addAttribute("publishers", publisherList);

        List<ContributorNameRoleResponseDto> contributorList = bookService.getActiveContributors();
        model.addAttribute("contributors", contributorList);

        List<CategoryResponseDto> topCategories = bookService.getTopCategories();
        model.addAttribute("topCategories", topCategories);

        List<TagResponseDto> tagList = bookService.getAllTags().getBody();
        model.addAttribute("tags", tagList);

        return "bookset/book/book-update";
    }

    @PutMapping(value = "/admin/books/modify/{bookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateBook(@PathVariable Long bookId,
                             @RequestParam("contributorList") String contributorListJson,
                             @ModelAttribute BookUpdateHtmlRequestDto bookUpdateHtmlRequestDto,
                             @RequestPart(value = "thumbnailImage", required = false) MultipartFile thumbnailImage,
                             @RequestPart(value = "detailImage", required = false) MultipartFile detailImage,
                             RedirectAttributes redirectAttributes) {
        try {
            BookUpdateHtmlRequestDto updatedDto = new BookUpdateHtmlRequestDto(
                    bookUpdateHtmlRequestDto.title(),
                    bookUpdateHtmlRequestDto.description(),
                    bookUpdateHtmlRequestDto.publisherName(),
                    bookUpdateHtmlRequestDto.publishedDate(),
                    bookUpdateHtmlRequestDto.isbn(),
                    bookUpdateHtmlRequestDto.retailPrice(),
                    bookUpdateHtmlRequestDto.sellingPrice(),
                    bookUpdateHtmlRequestDto.giftWrappable(),
                    bookUpdateHtmlRequestDto.isActive(),
                    bookUpdateHtmlRequestDto.remainQuantity(),
                    contributorListJson, // Combined contributors
                    bookUpdateHtmlRequestDto.topCategoryId(),
                    bookUpdateHtmlRequestDto.middleCategoryId(),
                    bookUpdateHtmlRequestDto.bottomCategoryId(),
                    bookUpdateHtmlRequestDto.tagList(),
                    bookUpdateHtmlRequestDto.removeThumbnailImage(),
                    bookUpdateHtmlRequestDto.removeDetailImage()
            );

            ImageUploadRequestDto imageUploadRequestDto = new ImageUploadRequestDto(thumbnailImage, detailImage);
            bookService.updateBook(bookId, updatedDto, imageUploadRequestDto);
            redirectAttributes.addFlashAttribute("message", "도서가 성공적으로 수정되었습니다.");
            return "redirect:/admin/books/get";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "도서 생성을 실패했습니다.");
            return "redirect:/admin/books/get";
        }
    }

    /**
     * 특정 도서를 비활성화하는 컨트롤러
     * @param bookId 비활성화할 도서 ID
     * @return 도서 목록 페이지로 리다이렉트
     */
    @PutMapping("/admin/books/{book-id}/deactivate")
    public String deactivateBook(@PathVariable("book-id") Long bookId) {
        try {
            bookService.deactivateBook(bookId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/admin/books/get";
        }
        return "redirect:/admin/books/get";
    }
}
