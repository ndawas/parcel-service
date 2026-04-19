package sn.edu.ept.parcel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sn.edu.ept.parcel.dto.TariffResponse;
import sn.edu.ept.parcel.entities.Parcel;
import sn.edu.ept.parcel.exception.ParcelNotFoundException;
import sn.edu.ept.parcel.repository.ParcelRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TariffService {

    private final ParcelRepository parcelRepository;

    private static final double BASE_STANDARD   = 2000.0;
    private static final double BASE_FRAGILE    = 3500.0;
    private static final double BASE_CARGO      = 5000.0;
    private static final double BASE_VOLUMINEUX = 4000.0;
    private static final double COST_PER_KG     = 500.0;
    private static final double COST_PER_M3     = 1000.0;
    private static final double COST_PER_KM     = 50.0;

    public TariffResponse calculateTariff(UUID parcelId) {
        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(parcelId));

        double basePrice = switch (parcel.getType()) {
            case STANDARD   -> BASE_STANDARD;
            case FRAGILE    -> BASE_FRAGILE;
            case CARGO      -> BASE_CARGO;
            case VOLUMINEUX -> BASE_VOLUMINEUX;
        };

        double weightCost = 0.0;
        if (parcel.getPoidsKg() != null && parcel.getPoidsKg() > 1.0) {
            weightCost = (parcel.getPoidsKg() - 1.0) * COST_PER_KG;
        }

        double volumeCost = 0.0;
        if (parcel.getVolumeM3() != null && parcel.getVolumeM3() > 0) {
            volumeCost = parcel.getVolumeM3() * COST_PER_M3;
        }

        double distanceCost = 0.0;
        if (parcel.getAdresseOrigine() != null && parcel.getAdresseDestination() != null
                && parcel.getAdresseOrigine().getLatitude() != null
                && parcel.getAdresseDestination().getLatitude() != null) {
            double distanceKm = calculerDistance(
                    parcel.getAdresseOrigine().getLatitude(),
                    parcel.getAdresseOrigine().getLongitude(),
                    parcel.getAdresseDestination().getLatitude(),
                    parcel.getAdresseDestination().getLongitude()
            );
            distanceCost = distanceKm * COST_PER_KM;
        }

        double totalTariff = basePrice + weightCost + volumeCost + distanceCost;
        log.info("Tarif calculé pour colis {} : {} FCFA", parcelId, totalTariff);

        return new TariffResponse(parcelId, totalTariff, basePrice, weightCost, volumeCost, distanceCost);
    }

    private double calculerDistance(double lat1, double lon1, double lat2, double lon2) {
        final int RAYON_TERRE = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RAYON_TERRE * c;
    }
}
