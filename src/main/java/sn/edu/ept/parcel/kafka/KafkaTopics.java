package sn.edu.ept.parcel.kafka;

/**
 * Noms des topics Kafka utilisés par parcel-service.
 * Ces constantes doivent être partagées avec les autres services consommateurs.
 */
public class KafkaTopics {

    public static final String PARCEL_CREATED         = "parcel.created";
    public static final String PARCEL_STATUS_UPDATED  = "parcel.status.updated";

    private KafkaTopics() {}
}
