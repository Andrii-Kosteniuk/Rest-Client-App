package dev.homework.restclientapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarSearchRequestDto {
    @NotBlank
    private String wojewodztwo;
    @NotBlank
    private String dataOd;
    @NotBlank
    private String dataDo;
}

