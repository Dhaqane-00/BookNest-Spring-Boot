package com.BookNest.Repository;
import com.BookNest.Models.Book;
import com.BookNest.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCreatedBy(User createdBy);
}

