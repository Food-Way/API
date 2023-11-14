package com.foodway.api.record.DTOs;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Establishment;

import java.util.List;

public record CustomerProfileDTO(String name, String profilePhoto, String bio, Integer level, Double rate, Integer XP, Long qtdComments, List<CommentDTO> comments, List<EstablishmentDTO> establishmentDTOs) {
}