package api.application.services;

import api.application.grpc.SubscriptionServiceGrpc;
import api.domain.models.Creator;
import api.domain.repository.CreatorRepository;

import api.application.grpc.SubscriptionServiceProto.CreatorRequest;
import api.application.grpc.SubscriptionServiceProto.CreatorResponse;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class CreatorGrpcService extends SubscriptionServiceGrpc.SubscriptionServiceImplBase {
    private final CreatorRepository repository;

    public CreatorGrpcService(CreatorRepository repository) {
        this.repository = repository;
    }

    public void getCreator(CreatorRequest request, StreamObserver<CreatorResponse> responseObserver) {
        try {
            // Fetch data from the repository
            Creator creator = repository.findById(Long.valueOf((request.getUserId())))
                    .orElseThrow(() -> new RuntimeException("Creator not found"));

            // Map domain object to gRPC response
            CreatorResponse response = CreatorResponse.newBuilder()
                    .setId(String.valueOf(creator.getId()))
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
