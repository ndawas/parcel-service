package sn.edu.ept.parcel.exception;

import java.util.UUID;

public class ParcelNotFoundException extends RuntimeException {

    public ParcelNotFoundException(UUID id) {
        super("Colis introuvable avec l'identifiant : " + id);
    }
}
