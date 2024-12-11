package api.domain.models;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserEntity userEntity;

    private String descripcion;

    @OneToMany(mappedBy = "creator")
    private Set<NivelSuscripcion> niveles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUsuario() {
        return userEntity;
    }

    public void setUsuario(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<NivelSuscripcion> getNiveles() {
        return niveles;
    }

    public void setNiveles(Set<NivelSuscripcion> niveles) {
        this.niveles = niveles;
    }
}
