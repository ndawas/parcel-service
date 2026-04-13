package sn.edu.ept.parcel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.edu.ept.parcel.dto.ParcelRequest;
import sn.edu.ept.parcel.dto.ParcelResponse;
import sn.edu.ept.parcel.enums.ParcelStatus;
import sn.edu.ept.parcel.service.ParcelService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/parcels")
@RequiredArgsConstructor
@Tag(name = "Parcel Service", description = "Gestion des colis SunuDelivery")
public class ParcelController {

    private final ParcelService parcelService;

    // ──────────────────────────────────────────────
    // POST /api/parcels
    // Créer un nouveau colis
    // ──────────────────────────────────────────────
    @PostMapping
    @Operation(summary = "Créer un nouveau colis")
    public ResponseEntity<ParcelResponse> creerColis(@Valid @RequestBody ParcelRequest request) {
        ParcelResponse response = parcelService.creerColis(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ──────────────────────────────────────────────
    // GET /api/parcels/{id}
    // Récupérer un colis par son ID
    // ──────────────────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un colis par son ID")
    public ResponseEntity<ParcelResponse> getColisById(@PathVariable UUID id) {
        return ResponseEntity.ok(parcelService.getColisById(id));
    }

    // ──────────────────────────────────────────────
    // GET /api/parcels
    // Récupérer tous les colis (admin)
    // ──────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Récupérer tous les colis (admin)")
    public ResponseEntity<List<ParcelResponse>> getAllColis() {
        return ResponseEntity.ok(parcelService.getAllColis());
    }

    // ──────────────────────────────────────────────
    // GET /api/parcels/client/{clientId}
    // Récupérer les colis d'un client
    // ──────────────────────────────────────────────
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Récupérer tous les colis d'un client")
    public ResponseEntity<List<ParcelResponse>> getColisByClient(@PathVariable UUID clientId) {
        return ResponseEntity.ok(parcelService.getColisByClient(clientId));
    }

    // ──────────────────────────────────────────────
    // PATCH /api/parcels/{id}/statut
    // Mettre à jour le statut d'un colis
    // ──────────────────────────────────────────────
    @PatchMapping("/{id}/statut")
    @Operation(summary = "Mettre à jour le statut d'un colis")
    public ResponseEntity<ParcelResponse> updateStatut(
            @PathVariable UUID id,
            @RequestParam ParcelStatus nouveauStatut) {
        return ResponseEntity.ok(parcelService.updateStatut(id, nouveauStatut));
    }

    // ──────────────────────────────────────────────
    // DELETE /api/parcels/{id}
    // Supprimer un colis (uniquement si EN_ATTENTE)
    // ──────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un colis (uniquement si EN_ATTENTE)")
    public ResponseEntity<Void> supprimerColis(@PathVariable UUID id) {
        parcelService.supprimerColis(id);
        return ResponseEntity.noContent().build();
    }
}
