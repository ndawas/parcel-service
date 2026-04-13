package sn.edu.ept.parcel.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.edu.ept.parcel.enums.ParcelStatus;
import sn.edu.ept.parcel.enums.ParcelType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Événement publié sur Kafka quand un colis est créé.
 * Consommé par : order-service, grouping-service
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParcelCreatedEvent {

    private UUID parcelId;
    private UUID clientId;
    private ParcelType type;
    private Double poidsKg;
    private Double volumeM3;
    private String villeOrigine;
    private String villeDestination;
    private Double origineLatitude;
    private Double origineLongitude;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private LocalDateTime createdAt;
}
