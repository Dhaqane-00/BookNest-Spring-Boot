package com.BookNest.Models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        private String author;
        private String genre;

        @Column(columnDefinition = "TEXT")
        private String description;

        private String coverImageUrl;

        private LocalDateTime createdAt;

        @ManyToOne
        @JoinColumn(name = "created_by", nullable = false)
        private User createdBy;

        @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
        private List<Review> reviews;

        @PrePersist
        public void prePersist() {
                createdAt = LocalDateTime.now();
        }
}
