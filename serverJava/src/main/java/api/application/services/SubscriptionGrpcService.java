package api.application.services;

import api.application.grpc.SubscriptionServiceGrpc;
import api.application.grpc.SubscriptionServiceProto;
import api.domain.models.Creator;
import api.domain.models.NivelSuscripcion;
import api.domain.models.UserEntity;
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
    private final UserService userService;

    public SubscriptionGrpcService(CreatorService creatorService, NivelSuscripcionService nivelSuscripcionService, UserService userService) {
        this.creatorService = creatorService;
        this.nivelSuscripcionService = nivelSuscripcionService;
        this.userService = userService;
    }

    @Override
    public void createCreator(SubscriptionServiceProto.CreatorRequest request, StreamObserver<SubscriptionServiceProto.CreatorResponse> responseObserver) {
        try {
            // Validar existencia del usuario
            UserEntity userEntity = userService.findById(Long.parseLong(request.getUserId()))
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + request.getUserId()));

            // Crear y guardar el Creator
            Creator creator = new Creator();
            creator.setUsuario(userEntity);
            creator.setDescripcion(request.getDescripcion());
            Creator savedCreator = creatorService.save(creator);

            // Crear respuesta
            SubscriptionServiceProto.CreatorResponse response = SubscriptionServiceProto.CreatorResponse.newBuilder()
                    .setId(String.valueOf(savedCreator.getId()))
                    .setDescripcion(savedCreator.getDescripcion())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCreators(SubscriptionServiceProto.Empty request, StreamObserver<SubscriptionServiceProto.CreatorListResponse> responseObserver) {
        try {
            List<SubscriptionServiceProto.CreatorResponse> creators = creatorService.getAll().stream()
                    .map(creator -> SubscriptionServiceProto.CreatorResponse.newBuilder()
                            .setId(String.valueOf(creator.getId()))
                            .setDescripcion(creator.getDescripcion())
                            .build())
                    .collect(Collectors.toList());

            SubscriptionServiceProto.CreatorListResponse response = SubscriptionServiceProto.CreatorListResponse.newBuilder()
                    .addAllCreators(creators)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void createNivelSuscripcion(SubscriptionServiceProto.NivelSuscripcionRequest request, StreamObserver<SubscriptionServiceProto.NivelSuscripcionResponse> responseObserver) {
        try {
            // Validar existencia del Creator
            Creator creator = creatorService.findById(Long.parseLong(request.getCreatorId()))
                    .orElseThrow(() -> new IllegalArgumentException("Creator no encontrado con ID: " + request.getCreatorId()));

            // Crear y guardar Nivel de Suscripción
            NivelSuscripcion nivel = new NivelSuscripcion();
            nivel.setCreador(creator);
            nivel.setDescripcion(request.getDescripcion());
            nivel.setPrecioMensual(BigDecimal.valueOf(request.getPrecioMensual()));
            nivel.setTiposSuscripcion(TiposSuscripcion.valueOf(request.getTiposSuscripcion()));

            NivelSuscripcion savedNivel = nivelSuscripcionService.save(nivel);

            // Crear respuesta
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

    @Override
    public void getNiveles(SubscriptionServiceProto.Empty request, StreamObserver<SubscriptionServiceProto.NivelSuscripcionListResponse> responseObserver) {
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
