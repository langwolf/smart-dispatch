package com.lioncorp.common.thrift;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class ThriftUtil {

    private static Logger logger = LoggerFactory.getLogger(ThriftUtil.class);

    /**
     * close internal transport
     * 
     * @param client
     */
    public static void closeClient(TServiceClient client) {
        if (client == null) {
            return;
        }
        try {
            TProtocol proto = client.getInputProtocol();
            if (proto != null) {
                proto.getTransport().close();
            }
        } catch (Throwable e) {
            logger.warn("close input transport fail", e);
        }
        try {
            TProtocol proto = client.getOutputProtocol();
            if (proto != null) {
                proto.getTransport().close();
            }
        } catch (Throwable e) {
            logger.warn("close output transport fail", e);
        }

    }
}
