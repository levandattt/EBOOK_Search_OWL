package org.ebook_searching.storage_service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.56.0)",
    comments = "Source: grpc/storage_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class StorageServiceGrpc {

  private StorageServiceGrpc() {}

  public static final String SERVICE_NAME = "org.ebook_searching.storage_service.StorageService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest,
      org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse> getConfirmFileUsageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConfirmFileUsage",
      requestType = org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest.class,
      responseType = org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest,
      org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse> getConfirmFileUsageMethod() {
    io.grpc.MethodDescriptor<org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest, org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse> getConfirmFileUsageMethod;
    if ((getConfirmFileUsageMethod = StorageServiceGrpc.getConfirmFileUsageMethod) == null) {
      synchronized (StorageServiceGrpc.class) {
        if ((getConfirmFileUsageMethod = StorageServiceGrpc.getConfirmFileUsageMethod) == null) {
          StorageServiceGrpc.getConfirmFileUsageMethod = getConfirmFileUsageMethod =
              io.grpc.MethodDescriptor.<org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest, org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConfirmFileUsage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StorageServiceMethodDescriptorSupplier("ConfirmFileUsage"))
              .build();
        }
      }
    }
    return getConfirmFileUsageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StorageServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StorageServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StorageServiceStub>() {
        @java.lang.Override
        public StorageServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StorageServiceStub(channel, callOptions);
        }
      };
    return StorageServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StorageServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StorageServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StorageServiceBlockingStub>() {
        @java.lang.Override
        public StorageServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StorageServiceBlockingStub(channel, callOptions);
        }
      };
    return StorageServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StorageServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StorageServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StorageServiceFutureStub>() {
        @java.lang.Override
        public StorageServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StorageServiceFutureStub(channel, callOptions);
        }
      };
    return StorageServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Unary RPC to confirm a single file
     * </pre>
     */
    default void confirmFileUsage(org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest request,
        io.grpc.stub.StreamObserver<org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConfirmFileUsageMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service StorageService.
   */
  public static abstract class StorageServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return StorageServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service StorageService.
   */
  public static final class StorageServiceStub
      extends io.grpc.stub.AbstractAsyncStub<StorageServiceStub> {
    private StorageServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StorageServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StorageServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC to confirm a single file
     * </pre>
     */
    public void confirmFileUsage(org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest request,
        io.grpc.stub.StreamObserver<org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConfirmFileUsageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service StorageService.
   */
  public static final class StorageServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<StorageServiceBlockingStub> {
    private StorageServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StorageServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StorageServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC to confirm a single file
     * </pre>
     */
    public org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse confirmFileUsage(org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConfirmFileUsageMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service StorageService.
   */
  public static final class StorageServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<StorageServiceFutureStub> {
    private StorageServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StorageServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StorageServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC to confirm a single file
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse> confirmFileUsage(
        org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConfirmFileUsageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CONFIRM_FILE_USAGE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONFIRM_FILE_USAGE:
          serviceImpl.confirmFileUsage((org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest) request,
              (io.grpc.stub.StreamObserver<org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getConfirmFileUsageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileRequest,
              org.ebook_searching.storage_service.StorageServiceOuterClass.ConfirmFileResponse>(
                service, METHODID_CONFIRM_FILE_USAGE)))
        .build();
  }

  private static abstract class StorageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StorageServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.ebook_searching.storage_service.StorageServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StorageService");
    }
  }

  private static final class StorageServiceFileDescriptorSupplier
      extends StorageServiceBaseDescriptorSupplier {
    StorageServiceFileDescriptorSupplier() {}
  }

  private static final class StorageServiceMethodDescriptorSupplier
      extends StorageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StorageServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StorageServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StorageServiceFileDescriptorSupplier())
              .addMethod(getConfirmFileUsageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
