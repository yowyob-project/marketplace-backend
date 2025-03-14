package com.marketplace.repositories;

import com.marketplace.entities.Review;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends CassandraRepository<Review, UUID> {
    @Query("SELECT * FROM reviews WHERE product_id = ?0 ALLOW FILTERING")
    List<Review> findByProductId(UUID productId);

    @Query("SELECT * FROM reviews WHERE user_id = ?0 ALLOW FILTERING")
    List<Review> findByUserId(UUID userId);

    Optional<Review> findByUserIdAndProductId(UUID userId, UUID productId);

    @Query("SELECT * FROM reviews WHERE id = ?0 ALLOW FILTERING")
    Optional<Review> findByReviewId(UUID reviewId);
}