package edu.romoshi.puller.balancer;

import java.util.ArrayList;
import java.util.List;

public class IpPool {
    private static final String DNS = System.getenv("DNS");
    private static final int REPLICAS = Integer.parseInt(System.getenv("REPLICAS"));
    private static final String PORT = System.getenv("PORT");

    public static List<String> getIpPoll() {
        List<String> ipPool = new ArrayList <>();

        for (int i = 0; i < REPLICAS; i++) {
            ipPool.add(DNS + i + ":" + PORT);
        }

        return ipPool;
    }
}
