package api.application.services;

import api.CreatorGrpc;
import api.CreatorRequest;
import api.CreatorResponse;
import api.domain.models.Creator;
import api.domain.repository.CreatorRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class CreatorGrpcService extends CreatorGrpc.CreatorImplBase {
    private final CreatorRepository repository;

    public CreatorGrpcService(CreatorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getCreator(CreatorRequest request, StreamObserver<CreatorResponse> responseObserver) {
        // Fetch data from the repository
        Creator creator = repository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        // Map domain object to gRPC response
        CreatorResponse response = CreatorResponse.newBuilder()
                .setId(creator.getId())
                .setDescripcion(creator.getDescripcion())
                .setNiveles(creator.getNiveles())
                .build();

        // Respond to the client
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
