package api.application.services;

import api.domain.repository.NivelSuscripcionRepository;
import org.springframework.stereotype.Service;
import api.domain.models.NivelSuscripcion;
import java.util.List;

@Service
public class NivelSuscripcionService {
    private final NivelSuscripcionRepository repository;

    public NivelSuscripcionService(NivelSuscripcionRepository repository) {
        this.repository = repository;
    }

    public List<NivelSuscripcion> getAll() {
        return repository.findAll();
    }

    public NivelSuscripcion save(NivelSuscripcion nivel) {
        return repository.save(nivel);
    }
}