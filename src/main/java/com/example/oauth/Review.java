package com.example.oauth;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 리뷰 작성자
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
    @Column(nullable = false)
    private String email;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "content_title", nullable = false)
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private Review(String email, String title, String comment) {
        this.email = email;
        this.title = title;
        this.comment = comment;
    }

    public static Review create(String email, String title, String comment) {
        return Review.builder()
                .email(email)
                .title(title)
                .comment(comment)
                .build();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String title, String comment) {
        this.title = title;
        this.comment = comment;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}