package com.example.oauth;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEmail(String email);

//    Page<Review> findByUser(String email, Pageable pageable);
}
