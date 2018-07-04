package com.lioncorp.common.thrift;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.google.common.base.Splitter;
import com.google.common.collect.MapMaker;



public class ServiceInfo {

	private static ConcurrentMap<String, ServiceInfo> allInfos = new MapMaker().weakValues()
	            .makeMap();

	private static Splitter splitter = Splitter.on(':');
	    
    private final String host;

    private final int port;

    public ServiceInfo(String host, int port) {
//        super();
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    private ServiceInfo(String hostAndPort) {
        List<String> split = splitter.splitToList(hostAndPort);
        assert split.size() == 2;
        this.host = split.get(0);
        this.port = Integer.parseInt(split.get(1));
    }

    public static ServiceInfo of(String host, int port) {
        return allInfos.computeIfAbsent(host + ":" + port, ServiceInfo::new);
    }

    public static ServiceInfo of(String hostAndPort) {
        return allInfos.computeIfAbsent(hostAndPort, ServiceInfo::new);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + port;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ServiceInfo other = (ServiceInfo) obj;
        if (host == null) {
            if (other.host != null) return false;
        } else if (!host.equals(other.host)) return false;
        if (port != other.port) return false;
        return true;
    }

    @Override
    public String toString() {
        return "ServiceInfo [host=" + host + ", port=" + port + "]";
    }
}
