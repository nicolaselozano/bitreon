package api.application.services;

import api.application.grpc.CreatorServiceGrpc;
import api.application.grpc.CreatorServiceProto;
import api.application.grpc.SubscriptionServiceGrpc;
import api.domain.models.Creator;
import api.domain.models.UserEntity;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreatorGrpcService extends CreatorServiceGrpc.CreatorServiceImplBase {
    private final CreatorService creatorService;
    private final UserService userService;

    public CreatorGrpcService(CreatorService creatorService, UserService userService) {
        this.creatorService = creatorService;
        this.userService = userService;
    }

    public void createCreator(CreatorServiceProto.CreatorRequest request, StreamObserver<CreatorServiceProto.CreatorResponse> responseObserver) {
        try {
            UserEntity userEntity = userService.findById(Long.parseLong(request.getUserId()))
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + request.getUserId()));

            Creator creator = new Creator();
            creator.setUsuario(userEntity);
            creator.setDescripcion(request.getDescripcion());
            Creator savedCreator = creatorService.save(creator);

            CreatorServiceProto.CreatorResponse response = CreatorServiceProto.CreatorResponse.newBuilder()
                    .setId(String.valueOf(savedCreator.getId()))
                    .setDescripcion(savedCreator.getDescripcion())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public void getCreators(CreatorServiceProto.Empty request, StreamObserver<CreatorServiceProto.CreatorListResponse> responseObserver) {
        try {
            List<CreatorServiceProto.CreatorResponse> creators = creatorService.getAll().stream()
                    .map(creator -> CreatorServiceProto.CreatorResponse.newBuilder()
                            .setId(String.valueOf(creator.getId()))
                            .setDescripcion(creator.getDescripcion())
                            .build())
                    .collect(Collectors.toList());

            CreatorServiceProto.CreatorListResponse response = CreatorServiceProto.CreatorListResponse.newBuilder()
                    .addAllCreators(creators)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
