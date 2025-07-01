package com.BookNest.Repository;
import com.BookNest.Models.Review;
import com.BookNest.Models.Book;
import com.BookNest.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBook(Book book);
    List<Review> findByUser(User user);
    boolean existsByUserAndBook(User user, Book book);
}
