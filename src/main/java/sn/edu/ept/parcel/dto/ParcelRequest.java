package sn.edu.ept.parcel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import sn.edu.ept.parcel.enums.ParcelType;

import java.util.UUID;

@Getter
@Setter
public class ParcelRequest {

    @NotNull(message = "L'identifiant du client est obligatoire")
    private UUID clientId;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "Le type de colis est obligatoire")
    private ParcelType type;

    @NotNull(message = "Le poids est obligatoire")
    @Positive(message = "Le poids doit être positif")
    private Double poidsKg;

    private Double volumeM3;

    @NotNull(message = "L'adresse d'origine est obligatoire")
    private AddressDto adresseOrigine;

    @NotNull(message = "L'adresse de destination est obligatoire")
    private AddressDto adresseDestination;
}
