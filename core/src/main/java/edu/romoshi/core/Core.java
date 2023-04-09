package edu.romoshi.core;

import edu.romoshi.core.dao.Tables;
import edu.romoshi.core.grpc.AccountsServiceGrpc;
import edu.romoshi.core.grpc.UsersServiceGrpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class Core {
    private static final Logger logger = LoggerFactory.getLogger(Core.class);
    public static void main(String[] args) throws InterruptedException, IOException {
        Server core = ServerBuilder
                .forPort(8080)
                .addService(new AccountsServiceGrpc())
                .addService(new UsersServiceGrpc())
                .build();
        if(!Tables.isFlag()) {
            Tables.initTables();
        }

        logger.info("Server started!");
        core.start();
        core.awaitTermination();
    }
}
