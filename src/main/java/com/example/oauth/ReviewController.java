package com.example.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;

    // 리뷰 작성
    @PostMapping
    public String review(String comment, @AuthenticationPrincipal OAuth2User user) {
        Review review = Review.create(
                user.getAttribute("email"),
                "content",
                comment);

        reviewRepository.save(review);

        return "success";
    }

    // 리뷰 보기
    @GetMapping
    public List<String> review(@AuthenticationPrincipal OAuth2User user) {
        List<Review> reviews = reviewRepository.findByEmail(Objects.requireNonNull(user.getAttribute("email")).toString());

        return reviews.stream()
                .map(Review::getComment)   // content 필드만 추출
                .toList();
    }
}
