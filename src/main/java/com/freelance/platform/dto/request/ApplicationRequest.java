package com.freelance.platform.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequest {

    @NotNull(message = "ID do job é obrigatório")
    private Long jobId;
}