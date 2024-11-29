package org.ebook_searching.admin.adapter;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Getter;
import org.ebook_searching.storage_service.StorageServiceGrpc;

import java.util.concurrent.TimeUnit;

@Getter
public class StorageServiceClient implements AutoCloseable {

    private final ManagedChannel channel;
    private final StorageServiceGrpc.StorageServiceBlockingStub blockingStub;

    public StorageServiceClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.blockingStub = StorageServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void close() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    // Additional methods to interact with the Storage service
}