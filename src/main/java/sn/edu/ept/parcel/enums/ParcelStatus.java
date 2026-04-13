package sn.edu.ept.parcel.enums;

public enum ParcelStatus {
    EN_ATTENTE,       // Colis créé, en attente de prise en charge
    RECUPERE,         // Récupéré par le livreur chez l'expéditeur
    EN_TRANSIT,       // En cours de livraison
    EN_GROUPAGE,      // Intégré dans un groupe de livraison
    LIVRE,            // Livré avec succès au destinataire
    ECHEC_LIVRAISON,  // Tentative de livraison échouée
    RETOURNE,         // Retourné à l'expéditeur
    ANNULE            // Commande annulée
}
