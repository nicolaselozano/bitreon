package api.application.services;

import api.application.grpc.SubscriptionServiceGrpc;
import api.application.grpc.SubscriptionServiceProto;
import api.application.grpc.CreatorServiceProto;
import api.domain.models.Creator;
import api.domain.models.NivelSuscripcion;
import api.infrastructure.api.utils.TiposSuscripcion;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionGrpcService extends SubscriptionServiceGrpc.SubscriptionServiceImplBase {

    private final CreatorService creatorService;
    private final NivelSuscripcionService nivelSuscripcionService;

    public SubscriptionGrpcService(CreatorService creatorService, NivelSuscripcionService nivelSuscripcionService, UserService userService) {
        this.creatorService = creatorService;
        this.nivelSuscripcionService = nivelSuscripcionService;
    }

    @Override
    public void createNivelSuscripcion(SubscriptionServiceProto.NivelSuscripcionRequest request, StreamObserver<SubscriptionServiceProto.NivelSuscripcionResponse> responseObserver) {
        try {
            Creator creator = creatorService.findById(Long.parseLong(request.getCreatorId()))
                    .orElseThrow(() -> new IllegalArgumentException("Creator no encontrado con ID: " + request.getCreatorId()));

            NivelSuscripcion nivel = new NivelSuscripcion();
            nivel.setCreador(creator);
            nivel.setDescripcion(request.getDescripcion());
            nivel.setPrecioMensual(BigDecimal.valueOf(request.getPrecioMensual()));
            nivel.setTiposSuscripcion(TiposSuscripcion.valueOf(request.getTiposSuscripcion()));

            NivelSuscripcion savedNivel = nivelSuscripcionService.save(nivel);

            SubscriptionServiceProto.NivelSuscripcionResponse response = SubscriptionServiceProto.NivelSuscripcionResponse.newBuilder()
                    .setId(String.valueOf(savedNivel.getId()))
                    .setDescripcion(savedNivel.getDescripcion())
                    .setTiposSuscripcion(savedNivel.getTiposSuscripcion().toString())
                    .setPrecioMensual(savedNivel.getPrecioMensual().doubleValue())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public void getNiveles(CreatorServiceProto.Empty request, StreamObserver<SubscriptionServiceProto.NivelSuscripcionListResponse> responseObserver) {
        try {
            List<SubscriptionServiceProto.NivelSuscripcionResponse> niveles = nivelSuscripcionService.getAll().stream()
                    .map(nivel -> SubscriptionServiceProto.NivelSuscripcionResponse.newBuilder()
                            .setId(String.valueOf(nivel.getId()))
                            .setDescripcion(nivel.getDescripcion())
                            .setTiposSuscripcion(nivel.getTiposSuscripcion().toString())
                            .setPrecioMensual(nivel.getPrecioMensual().doubleValue())
                            .build())
                    .collect(Collectors.toList());

            SubscriptionServiceProto.NivelSuscripcionListResponse response = SubscriptionServiceProto.NivelSuscripcionListResponse.newBuilder()
                    .addAllNiveles(niveles)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
