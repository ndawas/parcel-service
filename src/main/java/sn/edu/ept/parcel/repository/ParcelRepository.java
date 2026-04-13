package sn.edu.ept.parcel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.edu.ept.parcel.entities.Parcel;
import sn.edu.ept.parcel.enums.ParcelStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, UUID> {

    // Tous les colis d'un client
    List<Parcel> findByClientId(UUID clientId);

    // Colis par statut
    List<Parcel> findByStatut(ParcelStatus statut);

    // Colis d'un client par statut
    List<Parcel> findByClientIdAndStatut(UUID clientId, ParcelStatus statut);

    // Colis entre deux villes (utile pour le grouping-service)
    List<Parcel> findByAdresseOrigineVilleAndAdresseDestinationVille(
            String villeOrigine, String villeDestination);
}
