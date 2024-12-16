package api.config;

import api.application.services.CreatorGrpcService;
import api.application.services.SubscriptionGrpcService;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrpcConfig {
    private final Server server;

    public GrpcConfig(CreatorGrpcService creatorGrpcService, SubscriptionGrpcService subscriptionGrpcService) {
        this.server = ServerBuilder.forPort(50051)
                .addService((BindableService) creatorGrpcService)
                .addService((BindableService) subscriptionGrpcService)
                .addService(ProtoReflectionService.newInstance())
                .build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("gRPC Server started on port 50051 with Reflection enabled.");
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
    }

    public void blockUntilShutdown() throws InterruptedException {
        server.awaitTermination();
    }
}
