package sn.edu.ept.parcel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    @NotBlank(message = "La rue est obligatoire")
    private String rue;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    private String region;
    private String pays;
    private Double latitude;
    private Double longitude;
}
