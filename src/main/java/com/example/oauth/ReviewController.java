package com.example.oauth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;

    // 리뷰 작성
    @PostMapping
    public String createReview(@RequestParam String title, @RequestParam String comment, @AuthenticationPrincipal OAuth2User user) {
        Review review = Review.create(
                user.getAttribute("email"),
                title,
                comment);

        reviewRepository.save(review);

        return "success";
    }

    // 리뷰 보기
    @GetMapping
    public List<Review> getReviews(@AuthenticationPrincipal OAuth2User user) {

        String email = Objects.requireNonNull(user.getAttribute("email"));
        List<Review> reviews = reviewRepository.findByEmail(email);

        return reviews;
    }

    // 리뷰 수정
    @Transactional
    @PatchMapping("/{id}")
    public String updateReview(@PathVariable Long id,
                               @RequestParam String title,
                               @RequestParam String comment,
                               @AuthenticationPrincipal OAuth2User user) {

        System.out.println("id = " + id + ", title = " + title + ", comment = " + comment + ", user = " + user);

        String email = Objects.requireNonNull(user.getAttribute("email"));

        Review review = reviewRepository.findById(id).orElseThrow();

        if (!review.getEmail().equals(email)) {
            throw new RuntimeException("권한 없음");
        }

        if (title == null)
            title = review.getTitle();

        if (comment == null)
            comment = review.getComment();

        review.update(title, comment);

        return "success";
    }

    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id,
                               @AuthenticationPrincipal OAuth2User user) {

        String email = Objects.requireNonNull(user.getAttribute("email"));
        Review review = reviewRepository.findById(id).orElseThrow();

        if (!review.getEmail().equals(email)) {
            throw new RuntimeException("권한 없음");
        }

        reviewRepository.delete(review);

        return "success";
    }
}
