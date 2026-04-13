package sn.edu.ept.parcel.exception;

import sn.edu.ept.parcel.enums.ParcelStatus;

public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(ParcelStatus ancien, ParcelStatus nouveau) {
        super("Transition de statut invalide : " + ancien + " → " + nouveau);
    }
}
