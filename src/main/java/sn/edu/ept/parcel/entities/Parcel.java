package sn.edu.ept.parcel.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sn.edu.ept.parcel.enums.ParcelStatus;
import sn.edu.ept.parcel.enums.ParcelType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parcels")
@Getter
@Setter
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // ID du client expéditeur (géré par user-service)
    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParcelType type;

    @Column(nullable = false)
    private Double poidsKg;

    private Double volumeM3;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParcelStatus statut;

    // Adresse d'origine (embedded)
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "rue",       column = @Column(name = "origine_rue")),
        @AttributeOverride(name = "ville",     column = @Column(name = "origine_ville")),
        @AttributeOverride(name = "region",    column = @Column(name = "origine_region")),
        @AttributeOverride(name = "pays",      column = @Column(name = "origine_pays")),
        @AttributeOverride(name = "latitude",  column = @Column(name = "origine_lat")),
        @AttributeOverride(name = "longitude", column = @Column(name = "origine_lng"))
    })
    private Address adresseOrigine;

    // Adresse de destination (embedded)
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "rue",       column = @Column(name = "destination_rue")),
        @AttributeOverride(name = "ville",     column = @Column(name = "destination_ville")),
        @AttributeOverride(name = "region",    column = @Column(name = "destination_region")),
        @AttributeOverride(name = "pays",      column = @Column(name = "destination_pays")),
        @AttributeOverride(name = "latitude",  column = @Column(name = "destination_lat")),
        @AttributeOverride(name = "longitude", column = @Column(name = "destination_lng"))
    })
    private Address adresseDestination;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.statut == null) {
            this.statut = ParcelStatus.EN_ATTENTE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
