package com.freelance.platform.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationRequest {

    @NotNull(message = "ID do job é obrigatório")
    private Long jobId;
}