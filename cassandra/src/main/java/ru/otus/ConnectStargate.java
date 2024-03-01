package ru.otus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.stargate.grpc.StargateBearerToken;
import io.stargate.proto.QueryOuterClass;
import io.stargate.proto.QueryOuterClass.Row;
import io.stargate.proto.StargateGrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectStargate {

    private static final Logger log = LoggerFactory.getLogger(ConnectStargate.class);

    private static final String STARGATE_USERNAME      = "cassandrauser";
    private static final String STARGATE_PASSWORD      = "pwd";
    private static final String STARGATE_HOST          = "localhost";
    private static final int    STARGATE_GRPC_PORT     = 8090;
    private static final String STARGATE_AUTH_ENDPOINT = "http://localhost:4444/realms/stargate/protocol/openid-connect/token";

    public static void main(String[] args) throws Exception {

        String token = getTokenFromAuthEndpoint(STARGATE_USERNAME, STARGATE_PASSWORD);

        log.info(token);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(STARGATE_HOST, STARGATE_GRPC_PORT).usePlaintext().build();

        StargateGrpc.StargateBlockingStub blockingStub =
                StargateGrpc.newBlockingStub(channel)
                        .withDeadlineAfter(10, TimeUnit.SECONDS)
                        .withCallCredentials(new StargateBearerToken(token));

        QueryOuterClass.Response queryString = blockingStub.executeQuery(
                QueryOuterClass.Query.newBuilder()
                .setCql("SELECT id, blocked FROM sef.blocking")
                .build());
        QueryOuterClass.ResultSet rs = queryString.getResultSet();
        for (Row row : rs.getRowsList()) {
            System.out.println(""
                    + "id=" + row.getValues(0).getString() + ", "
                    + "blocked=" + row.getValues(1).getBoolean());
        }
    }

    private static String getTokenFromAuthEndpoint(String username, String password) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(STARGATE_AUTH_ENDPOINT))
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            String.format(
                                    "username=%s&password=%s&grant_type=password&client_id=user-service",
                                    username,
                                    password)))
                    .build();
            HttpResponse<String> response = HttpClient.newBuilder().build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            var tp = new TypeToken<HashMap<String, String>>(){}.getType();
            HashMap<String, String> res = gson.fromJson(response.body(), tp);
            return res.getOrDefault("access_token", "");
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
