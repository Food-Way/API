package com.foodway.api.repository;

import com.foodway.api.model.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UpvoteRepository extends JpaRepository<Upvote, Long> {

    @Query("SELECT COUNT(u.idComment) FROM com.foodway.api.model.Upvote u WHERE u.idComment = ?1")
    Integer countUpvotesByComment(UUID idPost);
}
