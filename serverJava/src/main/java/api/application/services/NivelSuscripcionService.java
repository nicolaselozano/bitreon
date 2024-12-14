package api.application.services;

import api.domain.models.NivelSuscripcion;
import api.domain.repository.NivelSuscripcionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public Optional<NivelSuscripcion> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public NivelSuscripcion update(Long id, NivelSuscripcion updatedNivel) {
        return repository.findById(id)
                .map(existingNivel -> {
                    existingNivel.setDescripcion(updatedNivel.getDescripcion());
                    existingNivel.setPrecioMensual(updatedNivel.getPrecioMensual());
                    existingNivel.setTiposSuscripcion(updatedNivel.getTiposSuscripcion());
                    return repository.save(existingNivel);
                })
                .orElseThrow(() -> new IllegalArgumentException("Nivel de suscripción no encontrado"));
    }
}
