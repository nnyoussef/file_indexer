package nyo.lu.appdeployer.jee.app.grpcservices;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import nyo.lu.appdeployer.grpcservices.HelloReply;
import nyo.lu.appdeployer.grpcservices.HelloRequest;
import nyo.lu.appdeployer.grpcservices.TestServiceGrpc;
import nyo.lu.appdeployer.jee.domain.bdd.repository.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class Test extends TestServiceGrpc.TestServiceImplBase {
    @Autowired
    private DirectoryRepository directoryRepository;

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        long s = System.nanoTime();
        int dataSize = 1_000_000;
        for (int i = 1; i < dataSize; i += 1)
            responseObserver.onNext(HelloReply.newBuilder().setMessage("a").build());
        long ss = System.nanoTime();
        System.out.println(((dataSize * 8) / ((ss - s) / 1000.0 / 1000.0 / 1000.0)) / 1024 / 1024);
        responseObserver.onCompleted();
    }

}
