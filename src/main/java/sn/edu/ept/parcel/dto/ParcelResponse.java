package sn.edu.ept.parcel.dto;

import lombok.Getter;
import lombok.Setter;
import sn.edu.ept.parcel.enums.ParcelStatus;
import sn.edu.ept.parcel.enums.ParcelType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ParcelResponse {

    private UUID id;
    private UUID clientId;
    private String description;
    private ParcelType type;
    private Double poidsKg;
    private Double volumeM3;
    private ParcelStatus statut;
    private AddressDto adresseOrigine;
    private AddressDto adresseDestination;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
