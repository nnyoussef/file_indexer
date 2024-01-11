package nyo.lu.appdeployer.grpcservices;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.57.2)",
    comments = "Source: Test.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class XGrpc {

  private XGrpc() {}

  public static final java.lang.String SERVICE_NAME = "nyo.lu.appdeployer.grpcservices.X";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<nyo.lu.appdeployer.grpcservices.HelloRequest,
      nyo.lu.appdeployer.grpcservices.HelloReply> getSayBonjourMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SayBonjour",
      requestType = nyo.lu.appdeployer.grpcservices.HelloRequest.class,
      responseType = nyo.lu.appdeployer.grpcservices.HelloReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<nyo.lu.appdeployer.grpcservices.HelloRequest,
      nyo.lu.appdeployer.grpcservices.HelloReply> getSayBonjourMethod() {
    io.grpc.MethodDescriptor<nyo.lu.appdeployer.grpcservices.HelloRequest, nyo.lu.appdeployer.grpcservices.HelloReply> getSayBonjourMethod;
    if ((getSayBonjourMethod = XGrpc.getSayBonjourMethod) == null) {
      synchronized (XGrpc.class) {
        if ((getSayBonjourMethod = XGrpc.getSayBonjourMethod) == null) {
          XGrpc.getSayBonjourMethod = getSayBonjourMethod =
              io.grpc.MethodDescriptor.<nyo.lu.appdeployer.grpcservices.HelloRequest, nyo.lu.appdeployer.grpcservices.HelloReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SayBonjour"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  nyo.lu.appdeployer.grpcservices.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  nyo.lu.appdeployer.grpcservices.HelloReply.getDefaultInstance()))
              .setSchemaDescriptor(new XMethodDescriptorSupplier("SayBonjour"))
              .build();
        }
      }
    }
    return getSayBonjourMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static XStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XStub>() {
        @java.lang.Override
        public XStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XStub(channel, callOptions);
        }
      };
    return XStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static XBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XBlockingStub>() {
        @java.lang.Override
        public XBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XBlockingStub(channel, callOptions);
        }
      };
    return XBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static XFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XFutureStub>() {
        @java.lang.Override
        public XFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XFutureStub(channel, callOptions);
        }
      };
    return XFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void sayBonjour(nyo.lu.appdeployer.grpcservices.HelloRequest request,
        io.grpc.stub.StreamObserver<nyo.lu.appdeployer.grpcservices.HelloReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSayBonjourMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service X.
   */
  public static abstract class XImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return XGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service X.
   */
  public static final class XStub
      extends io.grpc.stub.AbstractAsyncStub<XStub> {
    private XStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected XStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XStub(channel, callOptions);
    }

    /**
     */
    public void sayBonjour(nyo.lu.appdeployer.grpcservices.HelloRequest request,
        io.grpc.stub.StreamObserver<nyo.lu.appdeployer.grpcservices.HelloReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSayBonjourMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service X.
   */
  public static final class XBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<XBlockingStub> {
    private XBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected XBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XBlockingStub(channel, callOptions);
    }

    /**
     */
    public nyo.lu.appdeployer.grpcservices.HelloReply sayBonjour(nyo.lu.appdeployer.grpcservices.HelloRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSayBonjourMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service X.
   */
  public static final class XFutureStub
      extends io.grpc.stub.AbstractFutureStub<XFutureStub> {
    private XFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected XFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<nyo.lu.appdeployer.grpcservices.HelloReply> sayBonjour(
        nyo.lu.appdeployer.grpcservices.HelloRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSayBonjourMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAY_BONJOUR = 0;

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
        case METHODID_SAY_BONJOUR:
          serviceImpl.sayBonjour((nyo.lu.appdeployer.grpcservices.HelloRequest) request,
              (io.grpc.stub.StreamObserver<nyo.lu.appdeployer.grpcservices.HelloReply>) responseObserver);
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
          getSayBonjourMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              nyo.lu.appdeployer.grpcservices.HelloRequest,
              nyo.lu.appdeployer.grpcservices.HelloReply>(
                service, METHODID_SAY_BONJOUR)))
        .build();
  }

  private static abstract class XBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    XBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return nyo.lu.appdeployer.grpcservices.Test.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("X");
    }
  }

  private static final class XFileDescriptorSupplier
      extends XBaseDescriptorSupplier {
    XFileDescriptorSupplier() {}
  }

  private static final class XMethodDescriptorSupplier
      extends XBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    XMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (XGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new XFileDescriptorSupplier())
              .addMethod(getSayBonjourMethod())
              .build();
        }
      }
    }
    return result;
  }
}
