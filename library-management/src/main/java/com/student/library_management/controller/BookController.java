
package com.student.library_management.controller;

import com.student.library_management.model.Book;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private Map<Integer, Book> books = new HashMap<>();

    // POST: Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        if (books.containsKey(book.getId())) {
            return ResponseEntity.status(409).build();
        }

        books.put(book.getId(), book);
        return ResponseEntity.ok(book);
    }

    // GET: Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    // GET: Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {

        Book book = books.get(id);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    // PUT: Update book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Integer id,
            @RequestBody Book updatedBook) {

        Book existingBook = books.get(id);

        if (existingBook == null) {
            return ResponseEntity.notFound().build();
        }

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setAvailableCopies(updatedBook.getAvailableCopies());

        return ResponseEntity.ok(existingBook);
    }

    // DELETE: Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {

        if (!books.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        books.remove(id);
        return ResponseEntity.ok("Book deleted successfully!");
    }
}
