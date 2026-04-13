package sn.edu.ept.parcel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.edu.ept.parcel.dto.ParcelRequest;
import sn.edu.ept.parcel.dto.ParcelResponse;
import sn.edu.ept.parcel.entities.Parcel;
import sn.edu.ept.parcel.enums.ParcelStatus;
import sn.edu.ept.parcel.exception.InvalidStatusTransitionException;
import sn.edu.ept.parcel.exception.ParcelNotFoundException;
import sn.edu.ept.parcel.kafka.ParcelEventProducer;
import sn.edu.ept.parcel.kafka.events.ParcelCreatedEvent;
import sn.edu.ept.parcel.kafka.events.ParcelStatusUpdatedEvent;
import sn.edu.ept.parcel.mapper.ParcelMapper;
import sn.edu.ept.parcel.repository.ParcelRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final ParcelMapper parcelMapper;
    private final ParcelEventProducer eventProducer;

    // Transitions de statut autorisées
    private static final java.util.Map<ParcelStatus, Set<ParcelStatus>> TRANSITIONS_AUTORISEES =
            java.util.Map.of(
                ParcelStatus.EN_ATTENTE,      Set.of(ParcelStatus.RECUPERE, ParcelStatus.ANNULE),
                ParcelStatus.RECUPERE,        Set.of(ParcelStatus.EN_TRANSIT, ParcelStatus.EN_GROUPAGE),
                ParcelStatus.EN_TRANSIT,      Set.of(ParcelStatus.LIVRE, ParcelStatus.ECHEC_LIVRAISON),
                ParcelStatus.EN_GROUPAGE,     Set.of(ParcelStatus.EN_TRANSIT),
                ParcelStatus.ECHEC_LIVRAISON, Set.of(ParcelStatus.EN_TRANSIT, ParcelStatus.RETOURNE),
                ParcelStatus.LIVRE,           Set.of(),
                ParcelStatus.RETOURNE,        Set.of(),
                ParcelStatus.ANNULE,          Set.of()
            );

    @Override
    @Transactional
    public ParcelResponse creerColis(ParcelRequest request) {
        log.info("Création d'un nouveau colis pour le client : {}", request.getClientId());

        Parcel parcel = parcelMapper.toEntity(request);
        Parcel saved = parcelRepository.save(parcel);

        // Publier l'événement Kafka
        ParcelCreatedEvent event = new ParcelCreatedEvent(
                saved.getId(),
                saved.getClientId(),
                saved.getType(),
                saved.getPoidsKg(),
                saved.getVolumeM3(),
                saved.getAdresseOrigine().getVille(),
                saved.getAdresseDestination().getVille(),
                saved.getAdresseOrigine().getLatitude(),
                saved.getAdresseOrigine().getLongitude(),
                saved.getAdresseDestination().getLatitude(),
                saved.getAdresseDestination().getLongitude(),
                saved.getCreatedAt()
        );
        eventProducer.publishParcelCreated(event);

        log.info("Colis créé avec succès - ID : {}", saved.getId());
        return parcelMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ParcelResponse getColisById(UUID id) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new ParcelNotFoundException(id));
        return parcelMapper.toResponse(parcel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParcelResponse> getColisByClient(UUID clientId) {
        return parcelRepository.findByClientId(clientId)
                .stream()
                .map(parcelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParcelResponse> getAllColis() {
        return parcelRepository.findAll()
                .stream()
                .map(parcelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParcelResponse updateStatut(UUID id, ParcelStatus nouveauStatut) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new ParcelNotFoundException(id));

        ParcelStatus ancienStatut = parcel.getStatut();

        // Vérifier que la transition est autorisée
        Set<ParcelStatus> transitionsPermises = TRANSITIONS_AUTORISEES.getOrDefault(ancienStatut, Set.of());
        if (!transitionsPermises.contains(nouveauStatut)) {
            throw new InvalidStatusTransitionException(ancienStatut, nouveauStatut);
        }

        parcel.setStatut(nouveauStatut);
        Parcel saved = parcelRepository.save(parcel);

        // Publier l'événement Kafka
        ParcelStatusUpdatedEvent event = new ParcelStatusUpdatedEvent(
                saved.getId(),
                saved.getClientId(),
                ancienStatut,
                nouveauStatut,
                LocalDateTime.now()
        );
        eventProducer.publishParcelStatusUpdated(event);

        log.info("Statut colis {} mis à jour : {} → {}", id, ancienStatut, nouveauStatut);
        return parcelMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void supprimerColis(UUID id) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new ParcelNotFoundException(id));

        if (parcel.getStatut() != ParcelStatus.EN_ATTENTE) {
            throw new IllegalStateException(
                "Impossible de supprimer un colis avec le statut : " + parcel.getStatut()
            );
        }

        parcelRepository.delete(parcel);
        log.info("Colis supprimé - ID : {}", id);
    }
}
