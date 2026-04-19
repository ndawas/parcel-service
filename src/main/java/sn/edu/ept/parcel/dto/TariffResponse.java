package sn.edu.ept.parcel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TariffResponse {
    private UUID parcelId;
    private Double tariff;
    private Double basePrice;
    private Double weightCost;
    private Double volumeCost;
    private Double distanceCost;
}
