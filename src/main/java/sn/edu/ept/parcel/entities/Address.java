package sn.edu.ept.parcel.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {

    private String rue;
    private String ville;
    private String region;
    private String pays;
    private Double latitude;
    private Double longitude;
}
