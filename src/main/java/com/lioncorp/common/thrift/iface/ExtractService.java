package com.lioncorp.common.thrift.iface;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.server.AbstractNonblockingServer.AsyncFrameBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * test
 *
 */
public class ExtractService {

	  public interface Iface {

	    /**
	     * A method definition looks like C code. It has a return type, arguments,
	     * and optionally a list of exceptions that it may throw. Note that argument
	     * lists and exception lists are specified using the exact same syntax as
	     * field lists in struct or exception definitions.
	     */
	    public String ping() throws org.apache.thrift.TException;

	    public int add(int num1, int num2) throws org.apache.thrift.TException;

	    /**
	     * This method has a oneway modifier. That means the client only makes
	     * a request and does not listen for any response at all. Oneway methods
	     * must be void.
	     * 
	     * @param str
	     * @param type
	     */
	    public void zip(String str, int type) throws org.apache.thrift.TException;

	    public void uploadAction(String str) throws org.apache.thrift.TException;

	  }

	  public interface AsyncIface {

	    public void ping(org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

	    public void add(int num1, int num2, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

	    public void zip(String str, int type, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

	    public void uploadAction(String str, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

	  }

	  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
	    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
	      public Factory() {}
	      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
	        return new Client(prot);
	      }
	      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
	        return new Client(iprot, oprot);
	      }
	    }

	    public Client(org.apache.thrift.protocol.TProtocol prot)
	    {
	      super(prot, prot);
	    }

	    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
	      super(iprot, oprot);
	    }

	    public String ping() throws org.apache.thrift.TException
	    {
	      send_ping();
	      return recv_ping();
	    }

	    public void send_ping() throws org.apache.thrift.TException
	    {
	      ping_args args = new ping_args();
	      sendBase("ping", args);
	    }

	    public String recv_ping() throws org.apache.thrift.TException
	    {
	      ping_result result = new ping_result();
	      receiveBase(result, "ping");
	      if (result.isSetSuccess()) {
	        return result.success;
	      }
	      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "ping failed: unknown result");
	    }

	    public int add(int num1, int num2) throws org.apache.thrift.TException
	    {
	      send_add(num1, num2);
	      return recv_add();
	    }

	    public void send_add(int num1, int num2) throws org.apache.thrift.TException
	    {
	      add_args args = new add_args();
	      args.setNum1(num1);
	      args.setNum2(num2);
	      sendBase("add", args);
	    }

	    public int recv_add() throws org.apache.thrift.TException
	    {
	      add_result result = new add_result();
	      receiveBase(result, "add");
	      if (result.isSetSuccess()) {
	        return result.success;
	      }
	      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "add failed: unknown result");
	    }

	    public void zip(String str, int type) throws org.apache.thrift.TException
	    {
	      send_zip(str, type);
	    }

	    public void send_zip(String str, int type) throws org.apache.thrift.TException
	    {
	      zip_args args = new zip_args();
	      args.setStr(str);
	      args.setType(type);
	      sendBaseOneway("zip", args);
	    }

	    public void uploadAction(String str) throws org.apache.thrift.TException
	    {
	      send_uploadAction(str);
	      recv_uploadAction();
	    }

	    public void send_uploadAction(String str) throws org.apache.thrift.TException
	    {
	      uploadAction_args args = new uploadAction_args();
	      args.setStr(str);
	      sendBase("uploadAction", args);
	    }

	    public void recv_uploadAction() throws org.apache.thrift.TException
	    {
	      uploadAction_result result = new uploadAction_result();
	      receiveBase(result, "uploadAction");
	      return;
	    }

	  }
	  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
	    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
	      private org.apache.thrift.async.TAsyncClientManager clientManager;
	      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
	      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
	        this.clientManager = clientManager;
	        this.protocolFactory = protocolFactory;
	      }
	      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
	        return new AsyncClient(protocolFactory, clientManager, transport);
	      }
	    }

	    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
	      super(protocolFactory, clientManager, transport);
	    }

	    public void ping(org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
	      checkReady();
	      ping_call method_call = new ping_call(resultHandler, this, ___protocolFactory, ___transport);
	      this.___currentMethod = method_call;
	      ___manager.call(method_call);
	    }

	    public static class ping_call extends org.apache.thrift.async.TAsyncMethodCall {
	      public ping_call(org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
	        super(client, protocolFactory, transport, resultHandler, false);
	      }

	      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
	        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("ping", org.apache.thrift.protocol.TMessageType.CALL, 0));
	        ping_args args = new ping_args();
	        args.write(prot);
	        prot.writeMessageEnd();
	      }

	      public String getResult() throws org.apache.thrift.TException {
	        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
	          throw new IllegalStateException("Method call not finished!");
	        }
	        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
	        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
	        return (new Client(prot)).recv_ping();
	      }
	    }

	    public void add(int num1, int num2, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
	      checkReady();
	      add_call method_call = new add_call(num1, num2, resultHandler, this, ___protocolFactory, ___transport);
	      this.___currentMethod = method_call;
	      ___manager.call(method_call);
	    }

	    public static class add_call extends org.apache.thrift.async.TAsyncMethodCall {
	      private int num1;
	      private int num2;
	      public add_call(int num1, int num2, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
	        super(client, protocolFactory, transport, resultHandler, false);
	        this.num1 = num1;
	        this.num2 = num2;
	      }

	      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
	        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("add", org.apache.thrift.protocol.TMessageType.CALL, 0));
	        add_args args = new add_args();
	        args.setNum1(num1);
	        args.setNum2(num2);
	        args.write(prot);
	        prot.writeMessageEnd();
	      }

	      public int getResult() throws org.apache.thrift.TException {
	        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
	          throw new IllegalStateException("Method call not finished!");
	        }
	        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
	        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
	        return (new Client(prot)).recv_add();
	      }
	    }

	    public void zip(String str, int type, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
	      checkReady();
	      zip_call method_call = new zip_call(str, type, resultHandler, this, ___protocolFactory, ___transport);
	      this.___currentMethod = method_call;
	      ___manager.call(method_call);
	    }

	    public static class zip_call extends org.apache.thrift.async.TAsyncMethodCall {
	      private String str;
	      private int type;
	      public zip_call(String str, int type, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
	        super(client, protocolFactory, transport, resultHandler, true);
	        this.str = str;
	        this.type = type;
	      }

	      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
	        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("zip", org.apache.thrift.protocol.TMessageType.ONEWAY, 0));
	        zip_args args = new zip_args();
	        args.setStr(str);
	        args.setType(type);
	        args.write(prot);
	        prot.writeMessageEnd();
	      }

	      public void getResult() throws org.apache.thrift.TException {
	        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
	          throw new IllegalStateException("Method call not finished!");
	        }
	        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
	        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
	      }
	    }

	    public void uploadAction(String str, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
	      checkReady();
	      uploadAction_call method_call = new uploadAction_call(str, resultHandler, this, ___protocolFactory, ___transport);
	      this.___currentMethod = method_call;
	      ___manager.call(method_call);
	    }

	    public static class uploadAction_call extends org.apache.thrift.async.TAsyncMethodCall {
	      private String str;
	      public uploadAction_call(String str, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
	        super(client, protocolFactory, transport, resultHandler, false);
	        this.str = str;
	      }

	      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
	        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("uploadAction", org.apache.thrift.protocol.TMessageType.CALL, 0));
	        uploadAction_args args = new uploadAction_args();
	        args.setStr(str);
	        args.write(prot);
	        prot.writeMessageEnd();
	      }

	      public void getResult() throws org.apache.thrift.TException {
	        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
	          throw new IllegalStateException("Method call not finished!");
	        }
	        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
	        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
	        (new Client(prot)).recv_uploadAction();
	      }
	    }

	  }

	  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
	    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
	    public Processor(I iface) {
	      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
	    }

	    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
	      super(iface, getProcessMap(processMap));
	    }

	    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
	      processMap.put("ping", new ping());
	      processMap.put("add", new add());
	      processMap.put("zip", new zip());
	      processMap.put("uploadAction", new uploadAction());
	      return processMap;
	    }

	    public static class ping<I extends Iface> extends org.apache.thrift.ProcessFunction<I, ping_args> {
	      public ping() {
	        super("ping");
	      }

	      public ping_args getEmptyArgsInstance() {
	        return new ping_args();
	      }

	      protected boolean isOneway() {
	        return false;
	      }

	      public ping_result getResult(I iface, ping_args args) throws org.apache.thrift.TException {
	        ping_result result = new ping_result();
	        result.success = iface.ping();
	        return result;
	      }
	    }

	    public static class add<I extends Iface> extends org.apache.thrift.ProcessFunction<I, add_args> {
	      public add() {
	        super("add");
	      }

	      public add_args getEmptyArgsInstance() {
	        return new add_args();
	      }

	      protected boolean isOneway() {
	        return false;
	      }

	      public add_result getResult(I iface, add_args args) throws org.apache.thrift.TException {
	        add_result result = new add_result();
	        result.success = iface.add(args.num1, args.num2);
	        result.setSuccessIsSet(true);
	        return result;
	      }
	    }

	    public static class zip<I extends Iface> extends org.apache.thrift.ProcessFunction<I, zip_args> {
	      public zip() {
	        super("zip");
	      }

	      public zip_args getEmptyArgsInstance() {
	        return new zip_args();
	      }

	      protected boolean isOneway() {
	        return true;
	      }

	      public org.apache.thrift.TBase getResult(I iface, zip_args args) throws org.apache.thrift.TException {
	        iface.zip(args.str, args.type);
	        return null;
	      }
	    }

	    public static class uploadAction<I extends Iface> extends org.apache.thrift.ProcessFunction<I, uploadAction_args> {
	      public uploadAction() {
	        super("uploadAction");
	      }

	      public uploadAction_args getEmptyArgsInstance() {
	        return new uploadAction_args();
	      }

	      protected boolean isOneway() {
	        return false;
	      }

	      public uploadAction_result getResult(I iface, uploadAction_args args) throws org.apache.thrift.TException {
	        uploadAction_result result = new uploadAction_result();
	        iface.uploadAction(args.str);
	        return result;
	      }
	    }

	  }

	  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
	    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());
	    public AsyncProcessor(I iface) {
	      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
	    }

	    protected AsyncProcessor(I iface, Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
	      super(iface, getProcessMap(processMap));
	    }

	    private static <I extends AsyncIface> Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
	      processMap.put("ping", new ping());
	      processMap.put("add", new add());
	      processMap.put("zip", new zip());
	      processMap.put("uploadAction", new uploadAction());
	      return processMap;
	    }

	    public static class ping<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, ping_args, String> {
	      public ping() {
	        super("ping");
	      }

	      public ping_args getEmptyArgsInstance() {
	        return new ping_args();
	      }

	      public AsyncMethodCallback<String> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
	        final org.apache.thrift.AsyncProcessFunction fcall = this;
	        return new AsyncMethodCallback<String>() { 
	          public void onComplete(String o) {
	            ping_result result = new ping_result();
	            result.success = o;
	            try {
	              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
	              return;
	            } catch (Exception e) {
	              LOGGER.error("Exception writing to internal frame buffer", e);
	            }
	            fb.close();
	          }
	          public void onError(Exception e) {
	            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
	            org.apache.thrift.TBase msg;
	            ping_result result = new ping_result();
	            {
	              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
	              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
	            }
	            try {
	              fcall.sendResponse(fb,msg,msgType,seqid);
	              return;
	            } catch (Exception ex) {
	              LOGGER.error("Exception writing to internal frame buffer", ex);
	            }
	            fb.close();
	          }
	        };
	      }

	      protected boolean isOneway() {
	        return false;
	      }

	      public void start(I iface, ping_args args, org.apache.thrift.async.AsyncMethodCallback<String> resultHandler) throws TException {
	        iface.ping(resultHandler);
	      }
	    }

	    public static class add<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, add_args, Integer> {
	      public add() {
	        super("add");
	      }

	      public add_args getEmptyArgsInstance() {
	        return new add_args();
	      }

	      public AsyncMethodCallback<Integer> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
	        final org.apache.thrift.AsyncProcessFunction fcall = this;
	        return new AsyncMethodCallback<Integer>() { 
	          public void onComplete(Integer o) {
	            add_result result = new add_result();
	            result.success = o;
	            result.setSuccessIsSet(true);
	            try {
	              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
	              return;
	            } catch (Exception e) {
	              LOGGER.error("Exception writing to internal frame buffer", e);
	            }
	            fb.close();
	          }
	          public void onError(Exception e) {
	            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
	            org.apache.thrift.TBase msg;
	            add_result result = new add_result();
	            {
	              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
	              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
	            }
	            try {
	              fcall.sendResponse(fb,msg,msgType,seqid);
	              return;
	            } catch (Exception ex) {
	              LOGGER.error("Exception writing to internal frame buffer", ex);
	            }
	            fb.close();
	          }
	        };
	      }

	      protected boolean isOneway() {
	        return false;
	      }

	      public void start(I iface, add_args args, org.apache.thrift.async.AsyncMethodCallback<Integer> resultHandler) throws TException {
	        iface.add(args.num1, args.num2,resultHandler);
	      }
	    }

	    public static class zip<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, zip_args, Void> {
	      public zip() {
	        super("zip");
	      }

	      public zip_args getEmptyArgsInstance() {
	        return new zip_args();
	      }

	      public AsyncMethodCallback<Void> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
	        final org.apache.thrift.AsyncProcessFunction fcall = this;
	        return new AsyncMethodCallback<Void>() { 
	          public void onComplete(Void o) {
	          }
	          public void onError(Exception e) {
	          }
	        };
	      }

	      protected boolean isOneway() {
	        return true;
	      }

	      public void start(I iface, zip_args args, org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler) throws TException {
	        iface.zip(args.str, args.type,resultHandler);
	      }
	    }

	    public static class uploadAction<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, uploadAction_args, Void> {
	      public uploadAction() {
	        super("uploadAction");
	      }

	      public uploadAction_args getEmptyArgsInstance() {
	        return new uploadAction_args();
	      }

	      public AsyncMethodCallback<Void> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
	        final org.apache.thrift.AsyncProcessFunction fcall = this;
	        return new AsyncMethodCallback<Void>() { 
	          public void onComplete(Void o) {
	            uploadAction_result result = new uploadAction_result();
	            try {
	              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
	              return;
	            } catch (Exception e) {
	              LOGGER.error("Exception writing to internal frame buffer", e);
	            }
	            fb.close();
	          }
	          public void onError(Exception e) {
	            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
	            org.apache.thrift.TBase msg;
	            uploadAction_result result = new uploadAction_result();
	            {
	              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
	              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
	            }
	            try {
	              fcall.sendResponse(fb,msg,msgType,seqid);
	              return;
	            } catch (Exception ex) {
	              LOGGER.error("Exception writing to internal frame buffer", ex);
	            }
	            fb.close();
	          }
	        };
	      }

	      protected boolean isOneway() {
	        return false;
	      }

	      public void start(I iface, uploadAction_args args, org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler) throws TException {
	        iface.uploadAction(args.str,resultHandler);
	      }
	    }

	  }

	  public static class ping_args implements org.apache.thrift.TBase<ping_args, ping_args._Fields>, java.io.Serializable, Cloneable, Comparable<ping_args>   {
	    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ping_args");


	    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
	    static {
	      schemes.put(StandardScheme.class, new ping_argsStandardSchemeFactory());
	      schemes.put(TupleScheme.class, new ping_argsTupleSchemeFactory());
	    }


	    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
	    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
	;

	      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

	      static {
	        for (_Fields field : EnumSet.allOf(_Fields.class)) {
	          byName.put(field.getFieldName(), field);
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, or null if its not found.
	       */
	      public static _Fields findByThriftId(int fieldId) {
	        switch(fieldId) {
	          default:
	            return null;
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, throwing an exception
	       * if it is not found.
	       */
	      public static _Fields findByThriftIdOrThrow(int fieldId) {
	        _Fields fields = findByThriftId(fieldId);
	        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
	        return fields;
	      }

	      /**
	       * Find the _Fields constant that matches name, or null if its not found.
	       */
	      public static _Fields findByName(String name) {
	        return byName.get(name);
	      }

	      private final short _thriftId;
	      private final String _fieldName;

	      _Fields(short thriftId, String fieldName) {
	        _thriftId = thriftId;
	        _fieldName = fieldName;
	      }

	      public short getThriftFieldId() {
	        return _thriftId;
	      }

	      public String getFieldName() {
	        return _fieldName;
	      }
	    }
	    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
	    static {
	      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
	      metaDataMap = Collections.unmodifiableMap(tmpMap);
	      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ping_args.class, metaDataMap);
	    }

	    public ping_args() {
	    }

	    /**
	     * Performs a deep copy on <i>other</i>.
	     */
	    public ping_args(ping_args other) {
	    }

	    public ping_args deepCopy() {
	      return new ping_args(this);
	    }

	    @Override
	    public void clear() {
	    }

	    public void setFieldValue(_Fields field, Object value) {
	      switch (field) {
	      }
	    }

	    public Object getFieldValue(_Fields field) {
	      switch (field) {
	      }
	      throw new IllegalStateException();
	    }

	    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
	    public boolean isSet(_Fields field) {
	      if (field == null) {
	        throw new IllegalArgumentException();
	      }

	      switch (field) {
	      }
	      throw new IllegalStateException();
	    }

	    @Override
	    public boolean equals(Object that) {
	      if (that == null)
	        return false;
	      if (that instanceof ping_args)
	        return this.equals((ping_args)that);
	      return false;
	    }

	    public boolean equals(ping_args that) {
	      if (that == null)
	        return false;

	      return true;
	    }

	    @Override
	    public int hashCode() {
	      List<Object> list = new ArrayList<Object>();

	      return list.hashCode();
	    }

	    @Override
	    public int compareTo(ping_args other) {
	      if (!getClass().equals(other.getClass())) {
	        return getClass().getName().compareTo(other.getClass().getName());
	      }

	      int lastComparison = 0;

	      return 0;
	    }

	    public _Fields fieldForId(int fieldId) {
	      return _Fields.findByThriftId(fieldId);
	    }

	    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
	      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
	    }

	    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
	      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
	    }

	    @Override
	    public String toString() {
	      StringBuilder sb = new StringBuilder("ping_args(");
	      boolean first = true;

	      sb.append(")");
	      return sb.toString();
	    }

	    public void validate() throws org.apache.thrift.TException {
	      // check for required fields
	      // check for sub-struct validity
	    }

	    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
	      try {
	        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
	      try {
	        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private static class ping_argsStandardSchemeFactory implements SchemeFactory {
	      public ping_argsStandardScheme getScheme() {
	        return new ping_argsStandardScheme();
	      }
	    }

	    private static class ping_argsStandardScheme extends StandardScheme<ping_args> {

	      public void read(org.apache.thrift.protocol.TProtocol iprot, ping_args struct) throws org.apache.thrift.TException {
	        org.apache.thrift.protocol.TField schemeField;
	        iprot.readStructBegin();
	        while (true)
	        {
	          schemeField = iprot.readFieldBegin();
	          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
	            break;
	          }
	          switch (schemeField.id) {
	            default:
	              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	          }
	          iprot.readFieldEnd();
	        }
	        iprot.readStructEnd();

	        // check for required fields of primitive type, which can't be checked in the validate method
	        struct.validate();
	      }

	      public void write(org.apache.thrift.protocol.TProtocol oprot, ping_args struct) throws org.apache.thrift.TException {
	        struct.validate();

	        oprot.writeStructBegin(STRUCT_DESC);
	        oprot.writeFieldStop();
	        oprot.writeStructEnd();
	      }

	    }

	    private static class ping_argsTupleSchemeFactory implements SchemeFactory {
	      public ping_argsTupleScheme getScheme() {
	        return new ping_argsTupleScheme();
	      }
	    }

	    private static class ping_argsTupleScheme extends TupleScheme<ping_args> {

	      @Override
	      public void write(org.apache.thrift.protocol.TProtocol prot, ping_args struct) throws org.apache.thrift.TException {
	        TTupleProtocol oprot = (TTupleProtocol) prot;
	      }

	      @Override
	      public void read(org.apache.thrift.protocol.TProtocol prot, ping_args struct) throws org.apache.thrift.TException {
	        TTupleProtocol iprot = (TTupleProtocol) prot;
	      }
	    }

	  }

	  public static class ping_result implements org.apache.thrift.TBase<ping_result, ping_result._Fields>, java.io.Serializable, Cloneable, Comparable<ping_result>   {
	    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ping_result");

	    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRING, (short)0);

	    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
	    static {
	      schemes.put(StandardScheme.class, new ping_resultStandardSchemeFactory());
	      schemes.put(TupleScheme.class, new ping_resultTupleSchemeFactory());
	    }

	    public String success; // required

	    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
	    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
	      SUCCESS((short)0, "success");

	      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

	      static {
	        for (_Fields field : EnumSet.allOf(_Fields.class)) {
	          byName.put(field.getFieldName(), field);
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, or null if its not found.
	       */
	      public static _Fields findByThriftId(int fieldId) {
	        switch(fieldId) {
	          case 0: // SUCCESS
	            return SUCCESS;
	          default:
	            return null;
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, throwing an exception
	       * if it is not found.
	       */
	      public static _Fields findByThriftIdOrThrow(int fieldId) {
	        _Fields fields = findByThriftId(fieldId);
	        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
	        return fields;
	      }

	      /**
	       * Find the _Fields constant that matches name, or null if its not found.
	       */
	      public static _Fields findByName(String name) {
	        return byName.get(name);
	      }

	      private final short _thriftId;
	      private final String _fieldName;

	      _Fields(short thriftId, String fieldName) {
	        _thriftId = thriftId;
	        _fieldName = fieldName;
	      }

	      public short getThriftFieldId() {
	        return _thriftId;
	      }

	      public String getFieldName() {
	        return _fieldName;
	      }
	    }

	    // isset id assignments
	    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
	    static {
	      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
	      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
	          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
	      metaDataMap = Collections.unmodifiableMap(tmpMap);
	      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ping_result.class, metaDataMap);
	    }

	    public ping_result() {
	    }

	    public ping_result(
	      String success)
	    {
	      this();
	      this.success = success;
	    }

	    /**
	     * Performs a deep copy on <i>other</i>.
	     */
	    public ping_result(ping_result other) {
	      if (other.isSetSuccess()) {
	        this.success = other.success;
	      }
	    }

	    public ping_result deepCopy() {
	      return new ping_result(this);
	    }

	    @Override
	    public void clear() {
	      this.success = null;
	    }

	    public String getSuccess() {
	      return this.success;
	    }

	    public ping_result setSuccess(String success) {
	      this.success = success;
	      return this;
	    }

	    public void unsetSuccess() {
	      this.success = null;
	    }

	    /** Returns true if field success is set (has been assigned a value) and false otherwise */
	    public boolean isSetSuccess() {
	      return this.success != null;
	    }

	    public void setSuccessIsSet(boolean value) {
	      if (!value) {
	        this.success = null;
	      }
	    }

	    public void setFieldValue(_Fields field, Object value) {
	      switch (field) {
	      case SUCCESS:
	        if (value == null) {
	          unsetSuccess();
	        } else {
	          setSuccess((String)value);
	        }
	        break;

	      }
	    }

	    public Object getFieldValue(_Fields field) {
	      switch (field) {
	      case SUCCESS:
	        return getSuccess();

	      }
	      throw new IllegalStateException();
	    }

	    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
	    public boolean isSet(_Fields field) {
	      if (field == null) {
	        throw new IllegalArgumentException();
	      }

	      switch (field) {
	      case SUCCESS:
	        return isSetSuccess();
	      }
	      throw new IllegalStateException();
	    }

	    @Override
	    public boolean equals(Object that) {
	      if (that == null)
	        return false;
	      if (that instanceof ping_result)
	        return this.equals((ping_result)that);
	      return false;
	    }

	    public boolean equals(ping_result that) {
	      if (that == null)
	        return false;

	      boolean this_present_success = true && this.isSetSuccess();
	      boolean that_present_success = true && that.isSetSuccess();
	      if (this_present_success || that_present_success) {
	        if (!(this_present_success && that_present_success))
	          return false;
	        if (!this.success.equals(that.success))
	          return false;
	      }

	      return true;
	    }

	    @Override
	    public int hashCode() {
	      List<Object> list = new ArrayList<Object>();

	      boolean present_success = true && (isSetSuccess());
	      list.add(present_success);
	      if (present_success)
	        list.add(success);

	      return list.hashCode();
	    }

	    @Override
	    public int compareTo(ping_result other) {
	      if (!getClass().equals(other.getClass())) {
	        return getClass().getName().compareTo(other.getClass().getName());
	      }

	      int lastComparison = 0;

	      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
	      if (lastComparison != 0) {
	        return lastComparison;
	      }
	      if (isSetSuccess()) {
	        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
	        if (lastComparison != 0) {
	          return lastComparison;
	        }
	      }
	      return 0;
	    }

	    public _Fields fieldForId(int fieldId) {
	      return _Fields.findByThriftId(fieldId);
	    }

	    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
	      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
	    }

	    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
	      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
	      }

	    @Override
	    public String toString() {
	      StringBuilder sb = new StringBuilder("ping_result(");
	      boolean first = true;

	      sb.append("success:");
	      if (this.success == null) {
	        sb.append("null");
	      } else {
	        sb.append(this.success);
	      }
	      first = false;
	      sb.append(")");
	      return sb.toString();
	    }

	    public void validate() throws org.apache.thrift.TException {
	      // check for required fields
	      // check for sub-struct validity
	    }

	    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
	      try {
	        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
	      try {
	        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private static class ping_resultStandardSchemeFactory implements SchemeFactory {
	      public ping_resultStandardScheme getScheme() {
	        return new ping_resultStandardScheme();
	      }
	    }

	    private static class ping_resultStandardScheme extends StandardScheme<ping_result> {

	      public void read(org.apache.thrift.protocol.TProtocol iprot, ping_result struct) throws org.apache.thrift.TException {
	        org.apache.thrift.protocol.TField schemeField;
	        iprot.readStructBegin();
	        while (true)
	        {
	          schemeField = iprot.readFieldBegin();
	          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
	            break;
	          }
	          switch (schemeField.id) {
	            case 0: // SUCCESS
	              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
	                struct.success = iprot.readString();
	                struct.setSuccessIsSet(true);
	              } else { 
	                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	              }
	              break;
	            default:
	              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	          }
	          iprot.readFieldEnd();
	        }
	        iprot.readStructEnd();

	        // check for required fields of primitive type, which can't be checked in the validate method
	        struct.validate();
	      }

	      public void write(org.apache.thrift.protocol.TProtocol oprot, ping_result struct) throws org.apache.thrift.TException {
	        struct.validate();

	        oprot.writeStructBegin(STRUCT_DESC);
	        if (struct.success != null) {
	          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
	          oprot.writeString(struct.success);
	          oprot.writeFieldEnd();
	        }
	        oprot.writeFieldStop();
	        oprot.writeStructEnd();
	      }

	    }

	    private static class ping_resultTupleSchemeFactory implements SchemeFactory {
	      public ping_resultTupleScheme getScheme() {
	        return new ping_resultTupleScheme();
	      }
	    }

	    private static class ping_resultTupleScheme extends TupleScheme<ping_result> {

	      @Override
	      public void write(org.apache.thrift.protocol.TProtocol prot, ping_result struct) throws org.apache.thrift.TException {
	        TTupleProtocol oprot = (TTupleProtocol) prot;
	        BitSet optionals = new BitSet();
	        if (struct.isSetSuccess()) {
	          optionals.set(0);
	        }
	        oprot.writeBitSet(optionals, 1);
	        if (struct.isSetSuccess()) {
	          oprot.writeString(struct.success);
	        }
	      }

	      @Override
	      public void read(org.apache.thrift.protocol.TProtocol prot, ping_result struct) throws org.apache.thrift.TException {
	        TTupleProtocol iprot = (TTupleProtocol) prot;
	        BitSet incoming = iprot.readBitSet(1);
	        if (incoming.get(0)) {
	          struct.success = iprot.readString();
	          struct.setSuccessIsSet(true);
	        }
	      }
	    }

	  }

	  public static class add_args implements org.apache.thrift.TBase<add_args, add_args._Fields>, java.io.Serializable, Cloneable, Comparable<add_args>   {
	    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("add_args");

	    private static final org.apache.thrift.protocol.TField NUM1_FIELD_DESC = new org.apache.thrift.protocol.TField("num1", org.apache.thrift.protocol.TType.I32, (short)1);
	    private static final org.apache.thrift.protocol.TField NUM2_FIELD_DESC = new org.apache.thrift.protocol.TField("num2", org.apache.thrift.protocol.TType.I32, (short)2);

	    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
	    static {
	      schemes.put(StandardScheme.class, new add_argsStandardSchemeFactory());
	      schemes.put(TupleScheme.class, new add_argsTupleSchemeFactory());
	    }

	    public int num1; // required
	    public int num2; // required

	    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
	    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
	      NUM1((short)1, "num1"),
	      NUM2((short)2, "num2");

	      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

	      static {
	        for (_Fields field : EnumSet.allOf(_Fields.class)) {
	          byName.put(field.getFieldName(), field);
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, or null if its not found.
	       */
	      public static _Fields findByThriftId(int fieldId) {
	        switch(fieldId) {
	          case 1: // NUM1
	            return NUM1;
	          case 2: // NUM2
	            return NUM2;
	          default:
	            return null;
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, throwing an exception
	       * if it is not found.
	       */
	      public static _Fields findByThriftIdOrThrow(int fieldId) {
	        _Fields fields = findByThriftId(fieldId);
	        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
	        return fields;
	      }

	      /**
	       * Find the _Fields constant that matches name, or null if its not found.
	       */
	      public static _Fields findByName(String name) {
	        return byName.get(name);
	      }

	      private final short _thriftId;
	      private final String _fieldName;

	      _Fields(short thriftId, String fieldName) {
	        _thriftId = thriftId;
	        _fieldName = fieldName;
	      }

	      public short getThriftFieldId() {
	        return _thriftId;
	      }

	      public String getFieldName() {
	        return _fieldName;
	      }
	    }

	    // isset id assignments
	    private static final int __NUM1_ISSET_ID = 0;
	    private static final int __NUM2_ISSET_ID = 1;
	    private byte __isset_bitfield = 0;
	    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
	    static {
	      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
	      tmpMap.put(_Fields.NUM1, new org.apache.thrift.meta_data.FieldMetaData("num1", org.apache.thrift.TFieldRequirementType.DEFAULT, 
	          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
	      tmpMap.put(_Fields.NUM2, new org.apache.thrift.meta_data.FieldMetaData("num2", org.apache.thrift.TFieldRequirementType.DEFAULT, 
	          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
	      metaDataMap = Collections.unmodifiableMap(tmpMap);
	      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(add_args.class, metaDataMap);
	    }

	    public add_args() {
	    }

	    public add_args(
	      int num1,
	      int num2)
	    {
	      this();
	      this.num1 = num1;
	      setNum1IsSet(true);
	      this.num2 = num2;
	      setNum2IsSet(true);
	    }

	    /**
	     * Performs a deep copy on <i>other</i>.
	     */
	    public add_args(add_args other) {
	      __isset_bitfield = other.__isset_bitfield;
	      this.num1 = other.num1;
	      this.num2 = other.num2;
	    }

	    public add_args deepCopy() {
	      return new add_args(this);
	    }

	    @Override
	    public void clear() {
	      setNum1IsSet(false);
	      this.num1 = 0;
	      setNum2IsSet(false);
	      this.num2 = 0;
	    }

	    public int getNum1() {
	      return this.num1;
	    }

	    public add_args setNum1(int num1) {
	      this.num1 = num1;
	      setNum1IsSet(true);
	      return this;
	    }

	    public void unsetNum1() {
	      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NUM1_ISSET_ID);
	    }

	    /** Returns true if field num1 is set (has been assigned a value) and false otherwise */
	    public boolean isSetNum1() {
	      return EncodingUtils.testBit(__isset_bitfield, __NUM1_ISSET_ID);
	    }

	    public void setNum1IsSet(boolean value) {
	      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NUM1_ISSET_ID, value);
	    }

	    public int getNum2() {
	      return this.num2;
	    }

	    public add_args setNum2(int num2) {
	      this.num2 = num2;
	      setNum2IsSet(true);
	      return this;
	    }

	    public void unsetNum2() {
	      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NUM2_ISSET_ID);
	    }

	    /** Returns true if field num2 is set (has been assigned a value) and false otherwise */
	    public boolean isSetNum2() {
	      return EncodingUtils.testBit(__isset_bitfield, __NUM2_ISSET_ID);
	    }

	    public void setNum2IsSet(boolean value) {
	      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NUM2_ISSET_ID, value);
	    }

	    public void setFieldValue(_Fields field, Object value) {
	      switch (field) {
	      case NUM1:
	        if (value == null) {
	          unsetNum1();
	        } else {
	          setNum1((Integer)value);
	        }
	        break;

	      case NUM2:
	        if (value == null) {
	          unsetNum2();
	        } else {
	          setNum2((Integer)value);
	        }
	        break;

	      }
	    }

	    public Object getFieldValue(_Fields field) {
	      switch (field) {
	      case NUM1:
	        return getNum1();

	      case NUM2:
	        return getNum2();

	      }
	      throw new IllegalStateException();
	    }

	    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
	    public boolean isSet(_Fields field) {
	      if (field == null) {
	        throw new IllegalArgumentException();
	      }

	      switch (field) {
	      case NUM1:
	        return isSetNum1();
	      case NUM2:
	        return isSetNum2();
	      }
	      throw new IllegalStateException();
	    }

	    @Override
	    public boolean equals(Object that) {
	      if (that == null)
	        return false;
	      if (that instanceof add_args)
	        return this.equals((add_args)that);
	      return false;
	    }

	    public boolean equals(add_args that) {
	      if (that == null)
	        return false;

	      boolean this_present_num1 = true;
	      boolean that_present_num1 = true;
	      if (this_present_num1 || that_present_num1) {
	        if (!(this_present_num1 && that_present_num1))
	          return false;
	        if (this.num1 != that.num1)
	          return false;
	      }

	      boolean this_present_num2 = true;
	      boolean that_present_num2 = true;
	      if (this_present_num2 || that_present_num2) {
	        if (!(this_present_num2 && that_present_num2))
	          return false;
	        if (this.num2 != that.num2)
	          return false;
	      }

	      return true;
	    }

	    @Override
	    public int hashCode() {
	      List<Object> list = new ArrayList<Object>();

	      boolean present_num1 = true;
	      list.add(present_num1);
	      if (present_num1)
	        list.add(num1);

	      boolean present_num2 = true;
	      list.add(present_num2);
	      if (present_num2)
	        list.add(num2);

	      return list.hashCode();
	    }

	    @Override
	    public int compareTo(add_args other) {
	      if (!getClass().equals(other.getClass())) {
	        return getClass().getName().compareTo(other.getClass().getName());
	      }

	      int lastComparison = 0;

	      lastComparison = Boolean.valueOf(isSetNum1()).compareTo(other.isSetNum1());
	      if (lastComparison != 0) {
	        return lastComparison;
	      }
	      if (isSetNum1()) {
	        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.num1, other.num1);
	        if (lastComparison != 0) {
	          return lastComparison;
	        }
	      }
	      lastComparison = Boolean.valueOf(isSetNum2()).compareTo(other.isSetNum2());
	      if (lastComparison != 0) {
	        return lastComparison;
	      }
	      if (isSetNum2()) {
	        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.num2, other.num2);
	        if (lastComparison != 0) {
	          return lastComparison;
	        }
	      }
	      return 0;
	    }

	    public _Fields fieldForId(int fieldId) {
	      return _Fields.findByThriftId(fieldId);
	    }

	    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
	      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
	    }

	    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
	      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
	    }

	    @Override
	    public String toString() {
	      StringBuilder sb = new StringBuilder("add_args(");
	      boolean first = true;

	      sb.append("num1:");
	      sb.append(this.num1);
	      first = false;
	      if (!first) sb.append(", ");
	      sb.append("num2:");
	      sb.append(this.num2);
	      first = false;
	      sb.append(")");
	      return sb.toString();
	    }

	    public void validate() throws org.apache.thrift.TException {
	      // check for required fields
	      // check for sub-struct validity
	    }

	    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
	      try {
	        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
	      try {
	        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
	        __isset_bitfield = 0;
	        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private static class add_argsStandardSchemeFactory implements SchemeFactory {
	      public add_argsStandardScheme getScheme() {
	        return new add_argsStandardScheme();
	      }
	    }

	    private static class add_argsStandardScheme extends StandardScheme<add_args> {

	      public void read(org.apache.thrift.protocol.TProtocol iprot, add_args struct) throws org.apache.thrift.TException {
	        org.apache.thrift.protocol.TField schemeField;
	        iprot.readStructBegin();
	        while (true)
	        {
	          schemeField = iprot.readFieldBegin();
	          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
	            break;
	          }
	          switch (schemeField.id) {
	            case 1: // NUM1
	              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
	                struct.num1 = iprot.readI32();
	                struct.setNum1IsSet(true);
	              } else { 
	                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	              }
	              break;
	            case 2: // NUM2
	              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
	                struct.num2 = iprot.readI32();
	                struct.setNum2IsSet(true);
	              } else { 
	                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	              }
	              break;
	            default:
	              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	          }
	          iprot.readFieldEnd();
	        }
	        iprot.readStructEnd();

	        // check for required fields of primitive type, which can't be checked in the validate method
	        struct.validate();
	      }

	      public void write(org.apache.thrift.protocol.TProtocol oprot, add_args struct) throws org.apache.thrift.TException {
	        struct.validate();

	        oprot.writeStructBegin(STRUCT_DESC);
	        oprot.writeFieldBegin(NUM1_FIELD_DESC);
	        oprot.writeI32(struct.num1);
	        oprot.writeFieldEnd();
	        oprot.writeFieldBegin(NUM2_FIELD_DESC);
	        oprot.writeI32(struct.num2);
	        oprot.writeFieldEnd();
	        oprot.writeFieldStop();
	        oprot.writeStructEnd();
	      }

	    }

	    private static class add_argsTupleSchemeFactory implements SchemeFactory {
	      public add_argsTupleScheme getScheme() {
	        return new add_argsTupleScheme();
	      }
	    }

	    private static class add_argsTupleScheme extends TupleScheme<add_args> {

	      @Override
	      public void write(org.apache.thrift.protocol.TProtocol prot, add_args struct) throws org.apache.thrift.TException {
	        TTupleProtocol oprot = (TTupleProtocol) prot;
	        BitSet optionals = new BitSet();
	        if (struct.isSetNum1()) {
	          optionals.set(0);
	        }
	        if (struct.isSetNum2()) {
	          optionals.set(1);
	        }
	        oprot.writeBitSet(optionals, 2);
	        if (struct.isSetNum1()) {
	          oprot.writeI32(struct.num1);
	        }
	        if (struct.isSetNum2()) {
	          oprot.writeI32(struct.num2);
	        }
	      }

	      @Override
	      public void read(org.apache.thrift.protocol.TProtocol prot, add_args struct) throws org.apache.thrift.TException {
	        TTupleProtocol iprot = (TTupleProtocol) prot;
	        BitSet incoming = iprot.readBitSet(2);
	        if (incoming.get(0)) {
	          struct.num1 = iprot.readI32();
	          struct.setNum1IsSet(true);
	        }
	        if (incoming.get(1)) {
	          struct.num2 = iprot.readI32();
	          struct.setNum2IsSet(true);
	        }
	      }
	    }

	  }

	  public static class add_result implements org.apache.thrift.TBase<add_result, add_result._Fields>, java.io.Serializable, Cloneable, Comparable<add_result>   {
	    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("add_result");

	    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.I32, (short)0);

	    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
	    static {
	      schemes.put(StandardScheme.class, new add_resultStandardSchemeFactory());
	      schemes.put(TupleScheme.class, new add_resultTupleSchemeFactory());
	    }

	    public int success; // required

	    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
	    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
	      SUCCESS((short)0, "success");

	      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

	      static {
	        for (_Fields field : EnumSet.allOf(_Fields.class)) {
	          byName.put(field.getFieldName(), field);
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, or null if its not found.
	       */
	      public static _Fields findByThriftId(int fieldId) {
	        switch(fieldId) {
	          case 0: // SUCCESS
	            return SUCCESS;
	          default:
	            return null;
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, throwing an exception
	       * if it is not found.
	       */
	      public static _Fields findByThriftIdOrThrow(int fieldId) {
	        _Fields fields = findByThriftId(fieldId);
	        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
	        return fields;
	      }

	      /**
	       * Find the _Fields constant that matches name, or null if its not found.
	       */
	      public static _Fields findByName(String name) {
	        return byName.get(name);
	      }

	      private final short _thriftId;
	      private final String _fieldName;

	      _Fields(short thriftId, String fieldName) {
	        _thriftId = thriftId;
	        _fieldName = fieldName;
	      }

	      public short getThriftFieldId() {
	        return _thriftId;
	      }

	      public String getFieldName() {
	        return _fieldName;
	      }
	    }

	    // isset id assignments
	    private static final int __SUCCESS_ISSET_ID = 0;
	    private byte __isset_bitfield = 0;
	    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
	    static {
	      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
	      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
	          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
	      metaDataMap = Collections.unmodifiableMap(tmpMap);
	      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(add_result.class, metaDataMap);
	    }

	    public add_result() {
	    }

	    public add_result(
	      int success)
	    {
	      this();
	      this.success = success;
	      setSuccessIsSet(true);
	    }

	    /**
	     * Performs a deep copy on <i>other</i>.
	     */
	    public add_result(add_result other) {
	      __isset_bitfield = other.__isset_bitfield;
	      this.success = other.success;
	    }

	    public add_result deepCopy() {
	      return new add_result(this);
	    }

	    @Override
	    public void clear() {
	      setSuccessIsSet(false);
	      this.success = 0;
	    }

	    public int getSuccess() {
	      return this.success;
	    }

	    public add_result setSuccess(int success) {
	      this.success = success;
	      setSuccessIsSet(true);
	      return this;
	    }

	    public void unsetSuccess() {
	      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SUCCESS_ISSET_ID);
	    }

	    /** Returns true if field success is set (has been assigned a value) and false otherwise */
	    public boolean isSetSuccess() {
	      return EncodingUtils.testBit(__isset_bitfield, __SUCCESS_ISSET_ID);
	    }

	    public void setSuccessIsSet(boolean value) {
	      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SUCCESS_ISSET_ID, value);
	    }

	    public void setFieldValue(_Fields field, Object value) {
	      switch (field) {
	      case SUCCESS:
	        if (value == null) {
	          unsetSuccess();
	        } else {
	          setSuccess((Integer)value);
	        }
	        break;

	      }
	    }

	    public Object getFieldValue(_Fields field) {
	      switch (field) {
	      case SUCCESS:
	        return getSuccess();

	      }
	      throw new IllegalStateException();
	    }

	    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
	    public boolean isSet(_Fields field) {
	      if (field == null) {
	        throw new IllegalArgumentException();
	      }

	      switch (field) {
	      case SUCCESS:
	        return isSetSuccess();
	      }
	      throw new IllegalStateException();
	    }

	    @Override
	    public boolean equals(Object that) {
	      if (that == null)
	        return false;
	      if (that instanceof add_result)
	        return this.equals((add_result)that);
	      return false;
	    }

	    public boolean equals(add_result that) {
	      if (that == null)
	        return false;

	      boolean this_present_success = true;
	      boolean that_present_success = true;
	      if (this_present_success || that_present_success) {
	        if (!(this_present_success && that_present_success))
	          return false;
	        if (this.success != that.success)
	          return false;
	      }

	      return true;
	    }

	    @Override
	    public int hashCode() {
	      List<Object> list = new ArrayList<Object>();

	      boolean present_success = true;
	      list.add(present_success);
	      if (present_success)
	        list.add(success);

	      return list.hashCode();
	    }

	    @Override
	    public int compareTo(add_result other) {
	      if (!getClass().equals(other.getClass())) {
	        return getClass().getName().compareTo(other.getClass().getName());
	      }

	      int lastComparison = 0;

	      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
	      if (lastComparison != 0) {
	        return lastComparison;
	      }
	      if (isSetSuccess()) {
	        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
	        if (lastComparison != 0) {
	          return lastComparison;
	        }
	      }
	      return 0;
	    }

	    public _Fields fieldForId(int fieldId) {
	      return _Fields.findByThriftId(fieldId);
	    }

	    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
	      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
	    }

	    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
	      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
	      }

	    @Override
	    public String toString() {
	      StringBuilder sb = new StringBuilder("add_result(");
	      boolean first = true;

	      sb.append("success:");
	      sb.append(this.success);
	      first = false;
	      sb.append(")");
	      return sb.toString();
	    }

	    public void validate() throws org.apache.thrift.TException {
	      // check for required fields
	      // check for sub-struct validity
	    }

	    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
	      try {
	        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
	      try {
	        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
	        __isset_bitfield = 0;
	        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private static class add_resultStandardSchemeFactory implements SchemeFactory {
	      public add_resultStandardScheme getScheme() {
	        return new add_resultStandardScheme();
	      }
	    }

	    private static class add_resultStandardScheme extends StandardScheme<add_result> {

	      public void read(org.apache.thrift.protocol.TProtocol iprot, add_result struct) throws org.apache.thrift.TException {
	        org.apache.thrift.protocol.TField schemeField;
	        iprot.readStructBegin();
	        while (true)
	        {
	          schemeField = iprot.readFieldBegin();
	          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
	            break;
	          }
	          switch (schemeField.id) {
	            case 0: // SUCCESS
	              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
	                struct.success = iprot.readI32();
	                struct.setSuccessIsSet(true);
	              } else { 
	                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	              }
	              break;
	            default:
	              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	          }
	          iprot.readFieldEnd();
	        }
	        iprot.readStructEnd();

	        // check for required fields of primitive type, which can't be checked in the validate method
	        struct.validate();
	      }

	      public void write(org.apache.thrift.protocol.TProtocol oprot, add_result struct) throws org.apache.thrift.TException {
	        struct.validate();

	        oprot.writeStructBegin(STRUCT_DESC);
	        if (struct.isSetSuccess()) {
	          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
	          oprot.writeI32(struct.success);
	          oprot.writeFieldEnd();
	        }
	        oprot.writeFieldStop();
	        oprot.writeStructEnd();
	      }

	    }

	    private static class add_resultTupleSchemeFactory implements SchemeFactory {
	      public add_resultTupleScheme getScheme() {
	        return new add_resultTupleScheme();
	      }
	    }

	    private static class add_resultTupleScheme extends TupleScheme<add_result> {

	      @Override
	      public void write(org.apache.thrift.protocol.TProtocol prot, add_result struct) throws org.apache.thrift.TException {
	        TTupleProtocol oprot = (TTupleProtocol) prot;
	        BitSet optionals = new BitSet();
	        if (struct.isSetSuccess()) {
	          optionals.set(0);
	        }
	        oprot.writeBitSet(optionals, 1);
	        if (struct.isSetSuccess()) {
	          oprot.writeI32(struct.success);
	        }
	      }

	      @Override
	      public void read(org.apache.thrift.protocol.TProtocol prot, add_result struct) throws org.apache.thrift.TException {
	        TTupleProtocol iprot = (TTupleProtocol) prot;
	        BitSet incoming = iprot.readBitSet(1);
	        if (incoming.get(0)) {
	          struct.success = iprot.readI32();
	          struct.setSuccessIsSet(true);
	        }
	      }
	    }

	  }

	  public static class zip_args implements org.apache.thrift.TBase<zip_args, zip_args._Fields>, java.io.Serializable, Cloneable, Comparable<zip_args>   {
	    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("zip_args");

	    private static final org.apache.thrift.protocol.TField STR_FIELD_DESC = new org.apache.thrift.protocol.TField("str", org.apache.thrift.protocol.TType.STRING, (short)1);
	    private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)2);

	    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
	    static {
	      schemes.put(StandardScheme.class, new zip_argsStandardSchemeFactory());
	      schemes.put(TupleScheme.class, new zip_argsTupleSchemeFactory());
	    }

	    public String str; // required
	    public int type; // required

	    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
	    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
	      STR((short)1, "str"),
	      TYPE((short)2, "type");

	      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

	      static {
	        for (_Fields field : EnumSet.allOf(_Fields.class)) {
	          byName.put(field.getFieldName(), field);
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, or null if its not found.
	       */
	      public static _Fields findByThriftId(int fieldId) {
	        switch(fieldId) {
	          case 1: // STR
	            return STR;
	          case 2: // TYPE
	            return TYPE;
	          default:
	            return null;
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, throwing an exception
	       * if it is not found.
	       */
	      public static _Fields findByThriftIdOrThrow(int fieldId) {
	        _Fields fields = findByThriftId(fieldId);
	        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
	        return fields;
	      }

	      /**
	       * Find the _Fields constant that matches name, or null if its not found.
	       */
	      public static _Fields findByName(String name) {
	        return byName.get(name);
	      }

	      private final short _thriftId;
	      private final String _fieldName;

	      _Fields(short thriftId, String fieldName) {
	        _thriftId = thriftId;
	        _fieldName = fieldName;
	      }

	      public short getThriftFieldId() {
	        return _thriftId;
	      }

	      public String getFieldName() {
	        return _fieldName;
	      }
	    }

	    // isset id assignments
	    private static final int __TYPE_ISSET_ID = 0;
	    private byte __isset_bitfield = 0;
	    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
	    static {
	      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
	      tmpMap.put(_Fields.STR, new org.apache.thrift.meta_data.FieldMetaData("str", org.apache.thrift.TFieldRequirementType.DEFAULT, 
	          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
	      tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
	          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
	      metaDataMap = Collections.unmodifiableMap(tmpMap);
	      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(zip_args.class, metaDataMap);
	    }

	    public zip_args() {
	    }

	    public zip_args(
	      String str,
	      int type)
	    {
	      this();
	      this.str = str;
	      this.type = type;
	      setTypeIsSet(true);
	    }

	    /**
	     * Performs a deep copy on <i>other</i>.
	     */
	    public zip_args(zip_args other) {
	      __isset_bitfield = other.__isset_bitfield;
	      if (other.isSetStr()) {
	        this.str = other.str;
	      }
	      this.type = other.type;
	    }

	    public zip_args deepCopy() {
	      return new zip_args(this);
	    }

	    @Override
	    public void clear() {
	      this.str = null;
	      setTypeIsSet(false);
	      this.type = 0;
	    }

	    public String getStr() {
	      return this.str;
	    }

	    public zip_args setStr(String str) {
	      this.str = str;
	      return this;
	    }

	    public void unsetStr() {
	      this.str = null;
	    }

	    /** Returns true if field str is set (has been assigned a value) and false otherwise */
	    public boolean isSetStr() {
	      return this.str != null;
	    }

	    public void setStrIsSet(boolean value) {
	      if (!value) {
	        this.str = null;
	      }
	    }

	    public int getType() {
	      return this.type;
	    }

	    public zip_args setType(int type) {
	      this.type = type;
	      setTypeIsSet(true);
	      return this;
	    }

	    public void unsetType() {
	      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TYPE_ISSET_ID);
	    }

	    /** Returns true if field type is set (has been assigned a value) and false otherwise */
	    public boolean isSetType() {
	      return EncodingUtils.testBit(__isset_bitfield, __TYPE_ISSET_ID);
	    }

	    public void setTypeIsSet(boolean value) {
	      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TYPE_ISSET_ID, value);
	    }

	    public void setFieldValue(_Fields field, Object value) {
	      switch (field) {
	      case STR:
	        if (value == null) {
	          unsetStr();
	        } else {
	          setStr((String)value);
	        }
	        break;

	      case TYPE:
	        if (value == null) {
	          unsetType();
	        } else {
	          setType((Integer)value);
	        }
	        break;

	      }
	    }

	    public Object getFieldValue(_Fields field) {
	      switch (field) {
	      case STR:
	        return getStr();

	      case TYPE:
	        return getType();

	      }
	      throw new IllegalStateException();
	    }

	    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
	    public boolean isSet(_Fields field) {
	      if (field == null) {
	        throw new IllegalArgumentException();
	      }

	      switch (field) {
	      case STR:
	        return isSetStr();
	      case TYPE:
	        return isSetType();
	      }
	      throw new IllegalStateException();
	    }

	    @Override
	    public boolean equals(Object that) {
	      if (that == null)
	        return false;
	      if (that instanceof zip_args)
	        return this.equals((zip_args)that);
	      return false;
	    }

	    public boolean equals(zip_args that) {
	      if (that == null)
	        return false;

	      boolean this_present_str = true && this.isSetStr();
	      boolean that_present_str = true && that.isSetStr();
	      if (this_present_str || that_present_str) {
	        if (!(this_present_str && that_present_str))
	          return false;
	        if (!this.str.equals(that.str))
	          return false;
	      }

	      boolean this_present_type = true;
	      boolean that_present_type = true;
	      if (this_present_type || that_present_type) {
	        if (!(this_present_type && that_present_type))
	          return false;
	        if (this.type != that.type)
	          return false;
	      }

	      return true;
	    }

	    @Override
	    public int hashCode() {
	      List<Object> list = new ArrayList<Object>();

	      boolean present_str = true && (isSetStr());
	      list.add(present_str);
	      if (present_str)
	        list.add(str);

	      boolean present_type = true;
	      list.add(present_type);
	      if (present_type)
	        list.add(type);

	      return list.hashCode();
	    }

	    @Override
	    public int compareTo(zip_args other) {
	      if (!getClass().equals(other.getClass())) {
	        return getClass().getName().compareTo(other.getClass().getName());
	      }

	      int lastComparison = 0;

	      lastComparison = Boolean.valueOf(isSetStr()).compareTo(other.isSetStr());
	      if (lastComparison != 0) {
	        return lastComparison;
	      }
	      if (isSetStr()) {
	        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.str, other.str);
	        if (lastComparison != 0) {
	          return lastComparison;
	        }
	      }
	      lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
	      if (lastComparison != 0) {
	        return lastComparison;
	      }
	      if (isSetType()) {
	        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
	        if (lastComparison != 0) {
	          return lastComparison;
	        }
	      }
	      return 0;
	    }

	    public _Fields fieldForId(int fieldId) {
	      return _Fields.findByThriftId(fieldId);
	    }

	    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
	      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
	    }

	    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
	      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
	    }

	    @Override
	    public String toString() {
	      StringBuilder sb = new StringBuilder("zip_args(");
	      boolean first = true;

	      sb.append("str:");
	      if (this.str == null) {
	        sb.append("null");
	      } else {
	        sb.append(this.str);
	      }
	      first = false;
	      if (!first) sb.append(", ");
	      sb.append("type:");
	      sb.append(this.type);
	      first = false;
	      sb.append(")");
	      return sb.toString();
	    }

	    public void validate() throws org.apache.thrift.TException {
	      // check for required fields
	      // check for sub-struct validity
	    }

	    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
	      try {
	        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
	      try {
	        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
	        __isset_bitfield = 0;
	        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private static class zip_argsStandardSchemeFactory implements SchemeFactory {
	      public zip_argsStandardScheme getScheme() {
	        return new zip_argsStandardScheme();
	      }
	    }

	    private static class zip_argsStandardScheme extends StandardScheme<zip_args> {

	      public void read(org.apache.thrift.protocol.TProtocol iprot, zip_args struct) throws org.apache.thrift.TException {
	        org.apache.thrift.protocol.TField schemeField;
	        iprot.readStructBegin();
	        while (true)
	        {
	          schemeField = iprot.readFieldBegin();
	          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
	            break;
	          }
	          switch (schemeField.id) {
	            case 1: // STR
	              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
	                struct.str = iprot.readString();
	                struct.setStrIsSet(true);
	              } else { 
	                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	              }
	              break;
	            case 2: // TYPE
	              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
	                struct.type = iprot.readI32();
	                struct.setTypeIsSet(true);
	              } else { 
	                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	              }
	              break;
	            default:
	              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	          }
	          iprot.readFieldEnd();
	        }
	        iprot.readStructEnd();

	        // check for required fields of primitive type, which can't be checked in the validate method
	        struct.validate();
	      }

	      public void write(org.apache.thrift.protocol.TProtocol oprot, zip_args struct) throws org.apache.thrift.TException {
	        struct.validate();

	        oprot.writeStructBegin(STRUCT_DESC);
	        if (struct.str != null) {
	          oprot.writeFieldBegin(STR_FIELD_DESC);
	          oprot.writeString(struct.str);
	          oprot.writeFieldEnd();
	        }
	        oprot.writeFieldBegin(TYPE_FIELD_DESC);
	        oprot.writeI32(struct.type);
	        oprot.writeFieldEnd();
	        oprot.writeFieldStop();
	        oprot.writeStructEnd();
	      }

	    }

	    private static class zip_argsTupleSchemeFactory implements SchemeFactory {
	      public zip_argsTupleScheme getScheme() {
	        return new zip_argsTupleScheme();
	      }
	    }

	    private static class zip_argsTupleScheme extends TupleScheme<zip_args> {

	      @Override
	      public void write(org.apache.thrift.protocol.TProtocol prot, zip_args struct) throws org.apache.thrift.TException {
	        TTupleProtocol oprot = (TTupleProtocol) prot;
	        BitSet optionals = new BitSet();
	        if (struct.isSetStr()) {
	          optionals.set(0);
	        }
	        if (struct.isSetType()) {
	          optionals.set(1);
	        }
	        oprot.writeBitSet(optionals, 2);
	        if (struct.isSetStr()) {
	          oprot.writeString(struct.str);
	        }
	        if (struct.isSetType()) {
	          oprot.writeI32(struct.type);
	        }
	      }

	      @Override
	      public void read(org.apache.thrift.protocol.TProtocol prot, zip_args struct) throws org.apache.thrift.TException {
	        TTupleProtocol iprot = (TTupleProtocol) prot;
	        BitSet incoming = iprot.readBitSet(2);
	        if (incoming.get(0)) {
	          struct.str = iprot.readString();
	          struct.setStrIsSet(true);
	        }
	        if (incoming.get(1)) {
	          struct.type = iprot.readI32();
	          struct.setTypeIsSet(true);
	        }
	      }
	    }

	  }

	  public static class uploadAction_args implements org.apache.thrift.TBase<uploadAction_args, uploadAction_args._Fields>, java.io.Serializable, Cloneable, Comparable<uploadAction_args>   {
	    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("uploadAction_args");

	    private static final org.apache.thrift.protocol.TField STR_FIELD_DESC = new org.apache.thrift.protocol.TField("str", org.apache.thrift.protocol.TType.STRING, (short)1);

	    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
	    static {
	      schemes.put(StandardScheme.class, new uploadAction_argsStandardSchemeFactory());
	      schemes.put(TupleScheme.class, new uploadAction_argsTupleSchemeFactory());
	    }

	    public String str; // required

	    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
	    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
	      STR((short)1, "str");

	      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

	      static {
	        for (_Fields field : EnumSet.allOf(_Fields.class)) {
	          byName.put(field.getFieldName(), field);
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, or null if its not found.
	       */
	      public static _Fields findByThriftId(int fieldId) {
	        switch(fieldId) {
	          case 1: // STR
	            return STR;
	          default:
	            return null;
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, throwing an exception
	       * if it is not found.
	       */
	      public static _Fields findByThriftIdOrThrow(int fieldId) {
	        _Fields fields = findByThriftId(fieldId);
	        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
	        return fields;
	      }

	      /**
	       * Find the _Fields constant that matches name, or null if its not found.
	       */
	      public static _Fields findByName(String name) {
	        return byName.get(name);
	      }

	      private final short _thriftId;
	      private final String _fieldName;

	      _Fields(short thriftId, String fieldName) {
	        _thriftId = thriftId;
	        _fieldName = fieldName;
	      }

	      public short getThriftFieldId() {
	        return _thriftId;
	      }

	      public String getFieldName() {
	        return _fieldName;
	      }
	    }

	    // isset id assignments
	    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
	    static {
	      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
	      tmpMap.put(_Fields.STR, new org.apache.thrift.meta_data.FieldMetaData("str", org.apache.thrift.TFieldRequirementType.DEFAULT, 
	          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
	      metaDataMap = Collections.unmodifiableMap(tmpMap);
	      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(uploadAction_args.class, metaDataMap);
	    }

	    public uploadAction_args() {
	    }

	    public uploadAction_args(
	      String str)
	    {
	      this();
	      this.str = str;
	    }

	    /**
	     * Performs a deep copy on <i>other</i>.
	     */
	    public uploadAction_args(uploadAction_args other) {
	      if (other.isSetStr()) {
	        this.str = other.str;
	      }
	    }

	    public uploadAction_args deepCopy() {
	      return new uploadAction_args(this);
	    }

	    @Override
	    public void clear() {
	      this.str = null;
	    }

	    public String getStr() {
	      return this.str;
	    }

	    public uploadAction_args setStr(String str) {
	      this.str = str;
	      return this;
	    }

	    public void unsetStr() {
	      this.str = null;
	    }

	    /** Returns true if field str is set (has been assigned a value) and false otherwise */
	    public boolean isSetStr() {
	      return this.str != null;
	    }

	    public void setStrIsSet(boolean value) {
	      if (!value) {
	        this.str = null;
	      }
	    }

	    public void setFieldValue(_Fields field, Object value) {
	      switch (field) {
	      case STR:
	        if (value == null) {
	          unsetStr();
	        } else {
	          setStr((String)value);
	        }
	        break;

	      }
	    }

	    public Object getFieldValue(_Fields field) {
	      switch (field) {
	      case STR:
	        return getStr();

	      }
	      throw new IllegalStateException();
	    }

	    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
	    public boolean isSet(_Fields field) {
	      if (field == null) {
	        throw new IllegalArgumentException();
	      }

	      switch (field) {
	      case STR:
	        return isSetStr();
	      }
	      throw new IllegalStateException();
	    }

	    @Override
	    public boolean equals(Object that) {
	      if (that == null)
	        return false;
	      if (that instanceof uploadAction_args)
	        return this.equals((uploadAction_args)that);
	      return false;
	    }

	    public boolean equals(uploadAction_args that) {
	      if (that == null)
	        return false;

	      boolean this_present_str = true && this.isSetStr();
	      boolean that_present_str = true && that.isSetStr();
	      if (this_present_str || that_present_str) {
	        if (!(this_present_str && that_present_str))
	          return false;
	        if (!this.str.equals(that.str))
	          return false;
	      }

	      return true;
	    }

	    @Override
	    public int hashCode() {
	      List<Object> list = new ArrayList<Object>();

	      boolean present_str = true && (isSetStr());
	      list.add(present_str);
	      if (present_str)
	        list.add(str);

	      return list.hashCode();
	    }

	    @Override
	    public int compareTo(uploadAction_args other) {
	      if (!getClass().equals(other.getClass())) {
	        return getClass().getName().compareTo(other.getClass().getName());
	      }

	      int lastComparison = 0;

	      lastComparison = Boolean.valueOf(isSetStr()).compareTo(other.isSetStr());
	      if (lastComparison != 0) {
	        return lastComparison;
	      }
	      if (isSetStr()) {
	        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.str, other.str);
	        if (lastComparison != 0) {
	          return lastComparison;
	        }
	      }
	      return 0;
	    }

	    public _Fields fieldForId(int fieldId) {
	      return _Fields.findByThriftId(fieldId);
	    }

	    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
	      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
	    }

	    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
	      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
	    }

	    @Override
	    public String toString() {
	      StringBuilder sb = new StringBuilder("uploadAction_args(");
	      boolean first = true;

	      sb.append("str:");
	      if (this.str == null) {
	        sb.append("null");
	      } else {
	        sb.append(this.str);
	      }
	      first = false;
	      sb.append(")");
	      return sb.toString();
	    }

	    public void validate() throws org.apache.thrift.TException {
	      // check for required fields
	      // check for sub-struct validity
	    }

	    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
	      try {
	        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
	      try {
	        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private static class uploadAction_argsStandardSchemeFactory implements SchemeFactory {
	      public uploadAction_argsStandardScheme getScheme() {
	        return new uploadAction_argsStandardScheme();
	      }
	    }

	    private static class uploadAction_argsStandardScheme extends StandardScheme<uploadAction_args> {

	      public void read(org.apache.thrift.protocol.TProtocol iprot, uploadAction_args struct) throws org.apache.thrift.TException {
	        org.apache.thrift.protocol.TField schemeField;
	        iprot.readStructBegin();
	        while (true)
	        {
	          schemeField = iprot.readFieldBegin();
	          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
	            break;
	          }
	          switch (schemeField.id) {
	            case 1: // STR
	              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
	                struct.str = iprot.readString();
	                struct.setStrIsSet(true);
	              } else { 
	                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	              }
	              break;
	            default:
	              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	          }
	          iprot.readFieldEnd();
	        }
	        iprot.readStructEnd();

	        // check for required fields of primitive type, which can't be checked in the validate method
	        struct.validate();
	      }

	      public void write(org.apache.thrift.protocol.TProtocol oprot, uploadAction_args struct) throws org.apache.thrift.TException {
	        struct.validate();

	        oprot.writeStructBegin(STRUCT_DESC);
	        if (struct.str != null) {
	          oprot.writeFieldBegin(STR_FIELD_DESC);
	          oprot.writeString(struct.str);
	          oprot.writeFieldEnd();
	        }
	        oprot.writeFieldStop();
	        oprot.writeStructEnd();
	      }

	    }

	    private static class uploadAction_argsTupleSchemeFactory implements SchemeFactory {
	      public uploadAction_argsTupleScheme getScheme() {
	        return new uploadAction_argsTupleScheme();
	      }
	    }

	    private static class uploadAction_argsTupleScheme extends TupleScheme<uploadAction_args> {

	      @Override
	      public void write(org.apache.thrift.protocol.TProtocol prot, uploadAction_args struct) throws org.apache.thrift.TException {
	        TTupleProtocol oprot = (TTupleProtocol) prot;
	        BitSet optionals = new BitSet();
	        if (struct.isSetStr()) {
	          optionals.set(0);
	        }
	        oprot.writeBitSet(optionals, 1);
	        if (struct.isSetStr()) {
	          oprot.writeString(struct.str);
	        }
	      }

	      @Override
	      public void read(org.apache.thrift.protocol.TProtocol prot, uploadAction_args struct) throws org.apache.thrift.TException {
	        TTupleProtocol iprot = (TTupleProtocol) prot;
	        BitSet incoming = iprot.readBitSet(1);
	        if (incoming.get(0)) {
	          struct.str = iprot.readString();
	          struct.setStrIsSet(true);
	        }
	      }
	    }

	  }

	  public static class uploadAction_result implements org.apache.thrift.TBase<uploadAction_result, uploadAction_result._Fields>, java.io.Serializable, Cloneable, Comparable<uploadAction_result>   {
	    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("uploadAction_result");


	    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
	    static {
	      schemes.put(StandardScheme.class, new uploadAction_resultStandardSchemeFactory());
	      schemes.put(TupleScheme.class, new uploadAction_resultTupleSchemeFactory());
	    }


	    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
	    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
	;

	      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

	      static {
	        for (_Fields field : EnumSet.allOf(_Fields.class)) {
	          byName.put(field.getFieldName(), field);
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, or null if its not found.
	       */
	      public static _Fields findByThriftId(int fieldId) {
	        switch(fieldId) {
	          default:
	            return null;
	        }
	      }

	      /**
	       * Find the _Fields constant that matches fieldId, throwing an exception
	       * if it is not found.
	       */
	      public static _Fields findByThriftIdOrThrow(int fieldId) {
	        _Fields fields = findByThriftId(fieldId);
	        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
	        return fields;
	      }

	      /**
	       * Find the _Fields constant that matches name, or null if its not found.
	       */
	      public static _Fields findByName(String name) {
	        return byName.get(name);
	      }

	      private final short _thriftId;
	      private final String _fieldName;

	      _Fields(short thriftId, String fieldName) {
	        _thriftId = thriftId;
	        _fieldName = fieldName;
	      }

	      public short getThriftFieldId() {
	        return _thriftId;
	      }

	      public String getFieldName() {
	        return _fieldName;
	      }
	    }
	    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
	    static {
	      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
	      metaDataMap = Collections.unmodifiableMap(tmpMap);
	      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(uploadAction_result.class, metaDataMap);
	    }

	    public uploadAction_result() {
	    }

	    /**
	     * Performs a deep copy on <i>other</i>.
	     */
	    public uploadAction_result(uploadAction_result other) {
	    }

	    public uploadAction_result deepCopy() {
	      return new uploadAction_result(this);
	    }

	    @Override
	    public void clear() {
	    }

	    public void setFieldValue(_Fields field, Object value) {
	      switch (field) {
	      }
	    }

	    public Object getFieldValue(_Fields field) {
	      switch (field) {
	      }
	      throw new IllegalStateException();
	    }

	    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
	    public boolean isSet(_Fields field) {
	      if (field == null) {
	        throw new IllegalArgumentException();
	      }

	      switch (field) {
	      }
	      throw new IllegalStateException();
	    }

	    @Override
	    public boolean equals(Object that) {
	      if (that == null)
	        return false;
	      if (that instanceof uploadAction_result)
	        return this.equals((uploadAction_result)that);
	      return false;
	    }

	    public boolean equals(uploadAction_result that) {
	      if (that == null)
	        return false;

	      return true;
	    }

	    @Override
	    public int hashCode() {
	      List<Object> list = new ArrayList<Object>();

	      return list.hashCode();
	    }

	    @Override
	    public int compareTo(uploadAction_result other) {
	      if (!getClass().equals(other.getClass())) {
	        return getClass().getName().compareTo(other.getClass().getName());
	      }

	      int lastComparison = 0;

	      return 0;
	    }

	    public _Fields fieldForId(int fieldId) {
	      return _Fields.findByThriftId(fieldId);
	    }

	    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
	      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
	    }

	    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
	      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
	      }

	    @Override
	    public String toString() {
	      StringBuilder sb = new StringBuilder("uploadAction_result(");
	      boolean first = true;

	      sb.append(")");
	      return sb.toString();
	    }

	    public void validate() throws org.apache.thrift.TException {
	      // check for required fields
	      // check for sub-struct validity
	    }

	    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
	      try {
	        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
	      try {
	        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
	      } catch (org.apache.thrift.TException te) {
	        throw new java.io.IOException(te);
	      }
	    }

	    private static class uploadAction_resultStandardSchemeFactory implements SchemeFactory {
	      public uploadAction_resultStandardScheme getScheme() {
	        return new uploadAction_resultStandardScheme();
	      }
	    }

	    private static class uploadAction_resultStandardScheme extends StandardScheme<uploadAction_result> {

	      public void read(org.apache.thrift.protocol.TProtocol iprot, uploadAction_result struct) throws org.apache.thrift.TException {
	        org.apache.thrift.protocol.TField schemeField;
	        iprot.readStructBegin();
	        while (true)
	        {
	          schemeField = iprot.readFieldBegin();
	          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
	            break;
	          }
	          switch (schemeField.id) {
	            default:
	              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
	          }
	          iprot.readFieldEnd();
	        }
	        iprot.readStructEnd();

	        // check for required fields of primitive type, which can't be checked in the validate method
	        struct.validate();
	      }

	      public void write(org.apache.thrift.protocol.TProtocol oprot, uploadAction_result struct) throws org.apache.thrift.TException {
	        struct.validate();

	        oprot.writeStructBegin(STRUCT_DESC);
	        oprot.writeFieldStop();
	        oprot.writeStructEnd();
	      }

	    }

	    private static class uploadAction_resultTupleSchemeFactory implements SchemeFactory {
	      public uploadAction_resultTupleScheme getScheme() {
	        return new uploadAction_resultTupleScheme();
	      }
	    }

	    private static class uploadAction_resultTupleScheme extends TupleScheme<uploadAction_result> {

	      @Override
	      public void write(org.apache.thrift.protocol.TProtocol prot, uploadAction_result struct) throws org.apache.thrift.TException {
	        TTupleProtocol oprot = (TTupleProtocol) prot;
	      }

	      @Override
	      public void read(org.apache.thrift.protocol.TProtocol prot, uploadAction_result struct) throws org.apache.thrift.TException {
	        TTupleProtocol iprot = (TTupleProtocol) prot;
	      }
	    }

	  }

}
