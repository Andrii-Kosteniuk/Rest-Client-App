package dev.homework.restclientapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchVehiclesRequest {
    @NotBlank
    private String provinceName;
    @NotBlank
    private String dateFrom;
    @NotBlank
    private String dateTo;
}

