package com.library.book.service;

import com.library.book.entity.Book;
import com.library.book.exception.BookNotFoundException;
import com.library.book.exception.DuplicateIsbnException;
import com.library.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book addBook(Book book) {
        if (repository.existsByIsbn(book.getIsbn())) {
            throw new DuplicateIsbnException(book.getIsbn());
        }
        book.setId(null); // ensure a new row is always created
        return repository.save(book);
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBookById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book updateBook(Long id, Book updated) {
        Book existing = getBookById(id);

        repository.findByIsbn(updated.getIsbn()).ifPresent(other -> {
            if (!other.getId().equals(id)) {
                throw new DuplicateIsbnException(updated.getIsbn());
            }
        });

        existing.setTitle(updated.getTitle());
        existing.setAuthor(updated.getAuthor());
        existing.setIsbn(updated.getIsbn());
        return repository.save(existing);
    }

    public void deleteBook(Long id) {
        Book existing = getBookById(id);
        repository.delete(existing);
    }
}
