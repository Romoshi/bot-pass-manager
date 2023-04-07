package edu.romoshi.core.grpc;

import com.google.protobuf.Empty;
import edu.romoshi.core.dao.users.Users;
import io.grpc.stub.StreamObserver;

public class UsersServiceGrpc extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void addUser(User.AddRequest request, StreamObserver<Empty> responseObserver) {
        Users user = new Users(request.getId());
        user.addUser(request.getKey());

        responseObserver.onCompleted();
    }

    @Override
    public void userExist(User.IdRequest request, StreamObserver<User.ExistResponse> responseObserver) {
        Users user = new Users(request.getId());

        User.ExistResponse resp = User.ExistResponse.newBuilder()
                .setResponse(user.userExist())
                .build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void getMk(User.IdRequest request, StreamObserver<User.UserResponse> responseObserver) {
        Users user = new Users(request.getId());

        User.UserResponse resp = User.UserResponse.newBuilder()
                .setMasterKey(user.getMk())
                .build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
