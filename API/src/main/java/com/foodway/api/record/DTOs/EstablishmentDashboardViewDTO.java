package com.foodway.api.record.DTOs;

import com.foodway.api.model.Tag;

import java.util.List;

public record EstablishmentDashboardViewDTO(
        List<CommentDTO> comments,
        Double generalRate,
        List<EstablishmentRateDto> establishmentRate,
        List<QtdEvaluationDaysForWeek> qtdEvaluationDaysForWeek,
        List<Tag> tags
) {
}