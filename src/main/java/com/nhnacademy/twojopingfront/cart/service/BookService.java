//package com.nhnacademy.twojopingfront.cart.service;
//
//import com.nhnacademy.twojopingfront.cart.entity.Book;
//import com.nhnacademy.twojopingfront.cart.repository.BookRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class BookService {
//
//    private final BookRepository bookRepository;
//
//    public List<Book> getAllBooks(List<Long> bookIds) {
//        return bookRepository.findAllByBookIdIn(bookIds);
//    }
//
//}
