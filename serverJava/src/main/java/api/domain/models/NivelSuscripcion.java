package api.domain.models;
import jakarta.persistence.*;
import java.math.BigDecimal;
import api.infrastructure.api.utils.TiposSuscripcion;

@Entity
public class NivelSuscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TiposSuscripcion tiposSuscripcion;

    private String descripcion;
    private BigDecimal precioMensual;

    @ManyToOne
    @JoinColumn(name = "creador_id")
    private Creator creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TiposSuscripcion getTiposSuscripcion() {
        return tiposSuscripcion;
    }

    public void setTiposSuscripcion(TiposSuscripcion tiposSuscripcion) {
        this.tiposSuscripcion = tiposSuscripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(BigDecimal precioMensual) {
        this.precioMensual = precioMensual;
    }

    public Creator getCreador() {
        return creator;
    }

    public void setCreador(Creator creator) {
        this.creator = creator;
    }
}
