package sn.edu.ept.parcel.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.edu.ept.parcel.enums.ParcelStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Événement publié sur Kafka quand le statut d'un colis change.
 * Consommé par : order-service, notification-service, tracking-service
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParcelStatusUpdatedEvent {

    private UUID parcelId;
    private UUID clientId;
    private ParcelStatus ancienStatut;
    private ParcelStatus nouveauStatut;
    private LocalDateTime updatedAt;
}
