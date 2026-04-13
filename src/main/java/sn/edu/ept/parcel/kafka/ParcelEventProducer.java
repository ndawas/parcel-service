package sn.edu.ept.parcel.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sn.edu.ept.parcel.kafka.events.ParcelCreatedEvent;
import sn.edu.ept.parcel.kafka.events.ParcelStatusUpdatedEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParcelEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishParcelCreated(ParcelCreatedEvent event) {
        log.info("Publication événement ParcelCreated - parcelId: {}", event.getParcelId());
        kafkaTemplate.send(KafkaTopics.PARCEL_CREATED, event.getParcelId().toString(), event);
    }

    public void publishParcelStatusUpdated(ParcelStatusUpdatedEvent event) {
        log.info("Publication événement ParcelStatusUpdated - parcelId: {} | {} → {}",
                event.getParcelId(), event.getAncienStatut(), event.getNouveauStatut());
        kafkaTemplate.send(KafkaTopics.PARCEL_STATUS_UPDATED, event.getParcelId().toString(), event);
    }
}
