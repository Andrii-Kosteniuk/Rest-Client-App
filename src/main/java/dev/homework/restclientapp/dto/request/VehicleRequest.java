package dev.homework.restclientapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class VehicleRequest {

    private String provinceName;
    private String dateFrom;
    private String dateTo;
    private boolean registered;
    private int page;
    private int limit;
}

