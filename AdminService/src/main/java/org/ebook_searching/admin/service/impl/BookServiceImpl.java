package org.ebook_searching.admin.service.impl;

import org.ebook_searching.admin.exception.InvalidFieldsException;
import org.ebook_searching.admin.exception.RecordNotFoundException;
import org.ebook_searching.admin.mapper.BookMapper;
import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.DeleteBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;
import org.ebook_searching.admin.repository.BookRepository;
import org.ebook_searching.admin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public AddBookResponse addBook(AddBookRequest request) {
        // Convert the AddBookRequest to a Book entity
        Book book = bookMapper.toBook(request);

//        Book book = new Book();
//        if (request.getTitle() != null) {
//            book.setTitle(request.getTitle());
//        }
//
//        if (request.getLanguage() != null) {
//            book.setLanguage(request.getLanguage());
//        }
//
//        book.setLanguage(request.getLanguage());
//        book.setGenre(request.getGenre());
//        book.setPublisher(request.getPublisher());
//        book.setAvgRatings(request.getAvgRatings());
//        book.setRatingsCount(request.getRatingsCount());
//        book.setPublishedAt(request.getPublishedAt());

        // Save the request entity
        Book savedBook = bookRepository.save(book);

        // Convert the saved Book entity to AddBookResponse
        return null;
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookRequest book) {
        if (book.getId() == null) {
            throw InvalidFieldsException.fromFieldError("id", "Id là trường bắt buộc");
        }

        Book existingBook = findById(book.getId());
        if (book.getTitle() != null) {
            existingBook.setTitle(book.getTitle());
        }

        if (book.getGenre() != null) {
            existingBook.setGenre(book.getGenre());
        }

        if (book.getPublishedAt() != null) {
            existingBook.setPublishedAt(book.getPublishedAt());
        }

        if (book.getPublisher() != null) {
            existingBook.setTitle(book.getTitle());
        }

        if (book.getTitle() != null) {
            existingBook.setTitle(book.getTitle());
        }


        if (optionalBaseProductUnit.isEmpty()) {
            throw new RuntimeException();
        }

        if (optionalBaseProductUnitDto.isEmpty()) {
            throw InvalidFieldsException.fromFieldError("productUnits", "Phải có 1 đơn vị tính có tỉ lệ quy đổi bằng 1");
        }

        if (!Objects.equals(optionalBaseProductUnitDto.get().getUnitId(), optionalBaseProductUnit.get().getUnit().getId())) {
            throw InvalidFieldsException.fromFieldError("productUnits", "Không được phép thay đổi đơn vị tính có tỉ lệ quy đổi bằng 1");
        }

        Product product = productMapper.productDTOToProduct(productDTO);
        ingredientRepository.deleteByProductId(product.getId());
        productUnitRepository.deleteByProductId(product.getId());
        productImageRepository.deleteByProductId(product.getId());
        productRepository.save(product);
        return productMapper.productToProductDTO(product);
    }

    @Override
    public DeleteBookResponse deleteBook(Long id) {
        Book bookSelected = findById(id);
        bookRepository.deleteById(bookSelected.getId());
        DeleteBookResponse response = new DeleteBookResponse();
        response.setId(id);
        return response;
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> optionalCustomer = bookRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new RecordNotFoundException("Không tồn tại cuốn sách này");
        } else return optionalCustomer.get();
    }
}
