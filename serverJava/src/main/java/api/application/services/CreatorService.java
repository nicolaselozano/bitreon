package api.application.services;

import api.domain.models.Creator;
import api.domain.repository.CreatorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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
}
