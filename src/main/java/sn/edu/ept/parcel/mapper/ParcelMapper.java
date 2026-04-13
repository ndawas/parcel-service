package sn.edu.ept.parcel.mapper;

import org.springframework.stereotype.Component;
import sn.edu.ept.parcel.dto.AddressDto;
import sn.edu.ept.parcel.dto.ParcelRequest;
import sn.edu.ept.parcel.dto.ParcelResponse;
import sn.edu.ept.parcel.entities.Address;
import sn.edu.ept.parcel.entities.Parcel;

@Component
public class ParcelMapper {

    public Parcel toEntity(ParcelRequest request) {
        Parcel parcel = new Parcel();
        parcel.setClientId(request.getClientId());
        parcel.setDescription(request.getDescription());
        parcel.setType(request.getType());
        parcel.setPoidsKg(request.getPoidsKg());
        parcel.setVolumeM3(request.getVolumeM3());
        parcel.setAdresseOrigine(toAddressEntity(request.getAdresseOrigine()));
        parcel.setAdresseDestination(toAddressEntity(request.getAdresseDestination()));
        return parcel;
    }

    public ParcelResponse toResponse(Parcel parcel) {
        ParcelResponse response = new ParcelResponse();
        response.setId(parcel.getId());
        response.setClientId(parcel.getClientId());
        response.setDescription(parcel.getDescription());
        response.setType(parcel.getType());
        response.setPoidsKg(parcel.getPoidsKg());
        response.setVolumeM3(parcel.getVolumeM3());
        response.setStatut(parcel.getStatut());
        response.setAdresseOrigine(toAddressDto(parcel.getAdresseOrigine()));
        response.setAdresseDestination(toAddressDto(parcel.getAdresseDestination()));
        response.setCreatedAt(parcel.getCreatedAt());
        response.setUpdatedAt(parcel.getUpdatedAt());
        return response;
    }

    private Address toAddressEntity(AddressDto dto) {
        if (dto == null) return null;
        Address address = new Address();
        address.setRue(dto.getRue());
        address.setVille(dto.getVille());
        address.setRegion(dto.getRegion());
        address.setPays(dto.getPays());
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());
        return address;
    }

    private AddressDto toAddressDto(Address address) {
        if (address == null) return null;
        AddressDto dto = new AddressDto();
        dto.setRue(address.getRue());
        dto.setVille(address.getVille());
        dto.setRegion(address.getRegion());
        dto.setPays(address.getPays());
        dto.setLatitude(address.getLatitude());
        dto.setLongitude(address.getLongitude());
        return dto;
    }
}
