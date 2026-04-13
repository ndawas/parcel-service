package sn.edu.ept.parcel.service;

import sn.edu.ept.parcel.dto.ParcelRequest;
import sn.edu.ept.parcel.dto.ParcelResponse;
import sn.edu.ept.parcel.enums.ParcelStatus;

import java.util.List;
import java.util.UUID;

public interface ParcelService {

    // Créer un nouveau colis
    ParcelResponse creerColis(ParcelRequest request);

    // Récupérer un colis par son ID
    ParcelResponse getColisById(UUID id);

    // Récupérer tous les colis d'un client
    List<ParcelResponse> getColisByClient(UUID clientId);

    // Récupérer tous les colis (admin)
    List<ParcelResponse> getAllColis();

    // Mettre à jour le statut d'un colis
    ParcelResponse updateStatut(UUID id, ParcelStatus nouveauStatut);

    // Supprimer un colis (uniquement si EN_ATTENTE)
    void supprimerColis(UUID id);
}
