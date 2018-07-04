package com.lioncorp.common.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TTransport;

public class ThriftMultiplexedBinaryProtocolFactory implements ThriftProtocolFactory {

    private final String serviceName;

    public ThriftMultiplexedBinaryProtocolFactory(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public TProtocol makeProtocol(TTransport transport) {
        return new TMultiplexedProtocol(new TBinaryProtocol(new TFramedTransport(transport)), serviceName);
    }

	@Override
	public TProtocol makeCompactProtocol(TTransport transport) {
		return new TMultiplexedProtocol(new TCompactProtocol(new TFramedTransport(transport)), serviceName);
	}
    
    
}
