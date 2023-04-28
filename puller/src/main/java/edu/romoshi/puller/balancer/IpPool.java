package edu.romoshi.puller.balancer;

import java.util.ArrayList;
import java.util.List;

public class IpPool {
    private static final String DNS = System.getenv("DNS");
    private static final int REPLICAS = Integer.parseInt(System.getenv("REPLICAS"));
    private static final String PORT_FIRST = System.getenv("PORT_FIRST");
    private static final String PORT = System.getenv("PORT");

    public static List<String> getIpPoll() {
        List<String> ipPool = new ArrayList <>();

        for (int i = 1; i < REPLICAS; i++) {
            //int port = Integer.parseInt(PORT_FIRST) + i;
            ipPool.add(DNS + i + ":" + PORT);
        }

        return ipPool;
    }
}
