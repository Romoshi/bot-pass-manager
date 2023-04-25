package edu.romoshi.puller.balancer;

import java.util.List;

public class RoundRobin implements LoadBalancer {
    private static Integer position = 0;

    @Override
    public String getCore(String coreIp) {
        List<String> serverList = IpPool.getIpPoll();
        String target;

        synchronized (position) {
            if (position > serverList.size() - 1) {
                position = 0;
            }
            target = serverList.get(position);
            position++;
        }
        return target;
    }
}
