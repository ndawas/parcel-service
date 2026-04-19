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
    private UUID senderId;        // = clientId (nom attendu par order-service)
    private String description;
    private String type;          // String pour compatibilité Feign
    private Double weight;        // = poidsKg (nom attendu par order-service)
    private Double volumeM3;
    private String status;        // String pour compatibilité Feign
    private String originCity;    // = adresseOrigine.ville
    private String destinationCity; // = adresseDestination.ville
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
