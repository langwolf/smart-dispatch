package com.lioncorp.common.thrift;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

public interface ThriftProtocolFactory {

    TProtocol makeProtocol(TTransport transport);
    
    TProtocol makeCompactProtocol(TTransport transport);
}
