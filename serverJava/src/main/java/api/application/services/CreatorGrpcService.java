package api.application.services;

import api.CreatorServiceGrpc;
import api.domain.models.Creator;
import api.domain.repository.CreatorRepository;

import api.CreatorOuterClass.CreatorRequest;
import api.CreatorOuterClass.CreatorResponse;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class CreatorGrpcService extends CreatorServiceGrpc.CreatorServiceImplBase {
    private final CreatorRepository repository;

    public CreatorGrpcService(CreatorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getCreator(CreatorRequest request, StreamObserver<CreatorResponse> responseObserver) {
        try {
            // Fetch data from the repository
            Creator creator = repository.findById(((long) request.getId()))
                    .orElseThrow(() -> new RuntimeException("Creator not found"));

            // Map domain object to gRPC response
            CreatorResponse response = CreatorResponse.newBuilder()
                    .setId(Math.toIntExact(creator.getId()))
                    .setDescripcion(creator.getDescripcion())
                    .build();

            // Respond to the client
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Handle errors and respond with an error status
            responseObserver.onError(e);
        }
    }
}
