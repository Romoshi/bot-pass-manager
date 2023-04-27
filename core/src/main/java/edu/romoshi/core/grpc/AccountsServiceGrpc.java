package edu.romoshi.core.grpc;

import com.google.protobuf.Empty;
import edu.romoshi.core.dao.accounts.Accounts;
import edu.romoshi.grps.AccountOuterClass;
import edu.romoshi.grps.AccountServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AccountsServiceGrpc extends AccountServiceGrpc.AccountServiceImplBase {
    @Override
    public void addAccount(AccountOuterClass.Account request, StreamObserver<Empty> responseObserver) {
        int chatId = request.getId();
        Accounts account = new Accounts(request.getNameService(), request.getLogin(), request.getPassword());
        account.addAccount(chatId);

        responseObserver.onCompleted();
    }

    @Override
    public void getAccounts(AccountOuterClass.IdRequest request, StreamObserver<AccountOuterClass.GetResponse> responseObserver) {



        int chatId = request.getId();
        List<Accounts> accountsList = Accounts.getAccounts(chatId);

        AccountOuterClass.GetResponse accounts = AccountOuterClass.GetResponse.newBuilder()
                .addAllAccounts(
                        accountsList.stream().map(
                                u -> AccountOuterClass.Account.newBuilder()
                                        .setNameService(u.getNameService())
                                        .setLogin(u.getLogin())
                                        .setPassword(u.getPassword())
                                        .build()
                        ).toList()
                ).build();

        responseObserver.onNext(accounts);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAccount(AccountOuterClass.DeleteRequest request, StreamObserver<Empty> responseObserver) {
        int chatId = request.getId();
        Accounts.deleteAccount(request.getNameService(), chatId);

        responseObserver.onCompleted();
    }
}
