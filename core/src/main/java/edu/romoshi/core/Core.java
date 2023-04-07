package edu.romoshi.core;

import edu.romoshi.core.grpc.AccountsServiceGrpc;
import edu.romoshi.core.grpc.UsersServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class Core {
    public static void main(String[] args) throws InterruptedException, IOException {
//        if(!Tables.isFlag()) {
//            Tables.initTables();
//        }
//        Server core = ServerBuilder
//                .forPort(8080)
//                .addService(new AccountsServiceGrpc())
//                .addService(new UsersServiceGrpc())
//                .build();
//
//        server.start();
//        server.awaitTermination();
    }
}
