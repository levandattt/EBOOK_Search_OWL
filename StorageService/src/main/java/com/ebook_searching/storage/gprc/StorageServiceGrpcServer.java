package com.ebook_searching.storage.gprc;
import net.devh.boot.grpc.server.service.GrpcService;

import com.ebook_searching.storage.model.File;
import com.ebook_searching.storage.repository.FileRepository;
import io.grpc.stub.StreamObserver;
import org.ebook_searching.storage_service.StorageServiceGrpc;
import org.ebook_searching.storage_service.StorageServiceOuterClass;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GrpcService
public class StorageServiceGrpcServer extends StorageServiceGrpc.StorageServiceImplBase {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public void confirmFileUsage(StorageServiceOuterClass.ConfirmFileRequest request, StreamObserver<StorageServiceOuterClass.ConfirmFileResponse> responseObserver) {
        System.out.println("confirmFileUsage");
        StorageServiceOuterClass.ConfirmFileResponse response;
        // Validate and update file status
        Optional<File> optionalFile = fileRepository.findByFilePath(request.getFilePath());
        if (optionalFile.isPresent()) {
            File file = optionalFile.get();
            file.setConfirmed(true);
            fileRepository.save(file);

            // Build and send response
            response = StorageServiceOuterClass.ConfirmFileResponse.newBuilder()
                    .setFilePath(request.getFilePath())
                    .setSuccess(true)
                    .setMessage("File confirmed successfully.")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } else {
            // Build and send response
            response = StorageServiceOuterClass.ConfirmFileResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("File not found for " + request.getFilePath())
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        System.out.println("Complete");
    }
}
