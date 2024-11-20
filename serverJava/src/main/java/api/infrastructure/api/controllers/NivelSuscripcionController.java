package api.infrastructure.api.controllers;

import api.domain.models.NivelSuscripcion;
import api.application.services.NivelSuscripcionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/niveles")
public class NivelSuscripcionController {
    private final NivelSuscripcionService nivelSuscripcionService;

    public NivelSuscripcionController(NivelSuscripcionService nivelSuscripcionService) {
        this.nivelSuscripcionService = nivelSuscripcionService;
    }

    @GetMapping
    public List<NivelSuscripcion> getAll() {
        return nivelSuscripcionService.getAll();
    }

    @PostMapping
    public NivelSuscripcion create(@RequestBody NivelSuscripcion nivel) {
        return nivelSuscripcionService.save(nivel);
    }
}