package api.application.services;

import api.domain.models.Creator;
import api.domain.repository.CreatorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CreatorService {
    private final CreatorRepository repository;

    public CreatorService(CreatorRepository repository) {
        this.repository = repository;
    }

    public List<Creator> getAll() {
        return repository.findAll();
    }

    public Creator save(Creator creador) {
        return repository.save(creador);
    }

    public Optional<Creator> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
