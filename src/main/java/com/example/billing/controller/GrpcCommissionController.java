package com.example.billing.controller;


import com.example.billing.service.CommissionService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.grpc.service.CommissionServiceProto;
import net.devh.boot.grpc.server.service.GrpcService;


@Slf4j
@GrpcService
@RequiredArgsConstructor
public class GrpcCommissionController extends my.grpc.service.CommissionServiceGrpc.CommissionServiceImplBase{
    private final CommissionService commissionService;

    @Override
    public void sendCommission(CommissionServiceProto.CommissionRequest request,
                               StreamObserver<CommissionServiceProto.CommissionResponse> responseObserver) {
        try {

            log.info("Received Commission Request: id={}, from_whom={}, to_whom={}, amount={}, currency={}",
                    request.getId(), request.getFromWhom(), request.getToWhom(), request.getAmount(), request.getCurrency());


            boolean isProcessed = commissionService.processCommission(
                    request.getId(),
                    request.getFromWhom(),
                    request.getToWhom(),
                    request.getAmount(),
                    request.getCurrency());


            CommissionServiceProto.CommissionResponse response = CommissionServiceProto.CommissionResponse.newBuilder()
                    .setSuccess(isProcessed)
                    .setMessage(isProcessed ? "Commission processed successfully" : "Commission processing failed")
                    .build();


            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {

            log.error("Error processing commission: {}", e.getMessage());


            CommissionServiceProto.CommissionResponse response = CommissionServiceProto.CommissionResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error processing commission: " + e.getMessage())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
