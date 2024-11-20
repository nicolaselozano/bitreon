package api.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import api.domain.models.Creator;

public interface CreatorRepository extends JpaRepository<Creator, Long> {}