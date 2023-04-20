package edu.romoshi.puller;

import edu.romoshi.grpc.UserServiceGrpc;
import edu.romoshi.grps.AccountServiceGrpc;
import edu.romoshi.puller.balancer.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static edu.romoshi.puller.bot.Bot.bot;

public class Puller {
    private static final Logger logger = LoggerFactory.getLogger(Puller.class);
    private static final int REPLICAS = Integer.parseInt(System.getenv("REPLICAS"));
    public static final AccountServiceGrpc.AccountServiceBlockingStub stubAccount = AccountServiceGrpc.newBlockingStub(doGetServer(new RoundRobin()));
    public static final UserServiceGrpc.UserServiceBlockingStub stubUser = UserServiceGrpc.newBlockingStub(doGetServer(new RoundRobin()));
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
            logger.info("Bot ON");
        } catch (TelegramApiException e) {
            logger.error("Problems with bot register", e);
        }
    }

    private static ManagedChannel doGetServer(RoundRobin roundRobin) {
        ManagedChannel channel = null;

        for (int i = 0; i < Puller.REPLICAS; i++) {
            String serverId = roundRobin.getCore(String.valueOf(i));
            channel = ManagedChannelBuilder.forTarget(serverId)
                    .usePlaintext().build();
        }

        return channel;
    }
}