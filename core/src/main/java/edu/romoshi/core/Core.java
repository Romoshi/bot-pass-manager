package edu.romoshi.core;

import edu.romoshi.core.dao.Tables;
import edu.romoshi.core.grpc.AccountsServiceGrpc;
import edu.romoshi.core.grpc.UsersServiceGrpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Core {
    private static final Logger logger = LoggerFactory.getLogger(Core.class);
    public static void main(String[] args) {
        try {
            Server core = ServerBuilder
                    .forPort(Integer.parseInt(System.getenv("PORT")))
                    .addService(new AccountsServiceGrpc())
                    .addService(new UsersServiceGrpc())
                    .build();
            if(!Tables.isFlag()) {
                Tables.initTables();
            }

            logger.info("Server started!");
            core.start();
            core.awaitTermination();
        } catch (Exception ex) {
            logger.error("Server problems", ex);
        }
    }
}
