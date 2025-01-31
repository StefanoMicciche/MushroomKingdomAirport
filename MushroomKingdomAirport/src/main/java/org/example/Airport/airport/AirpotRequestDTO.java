package org.example.Airport.airport;

import jakarta.validation.constraints.NotNull;
import org.example.Airport.airport.Enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirpotRequestDTO {
    @NotBlank(message = "Airport code is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Airport code must be 3 uppercase letters")
    private String code;

    @NotBlank(message = "Airport name is required")
    private String name;

    @NotBlank(message = "Airport city is required")
    private String city;

    @NotBlank(message = "Airport country is required")
    private String country;

    @NotNull(message = "Airport type is required")
    private String type;

    @NotNull(message = "Airport size is required")
    private String size;

    @NotNull(message = "Region is required")
    private String region;
}
