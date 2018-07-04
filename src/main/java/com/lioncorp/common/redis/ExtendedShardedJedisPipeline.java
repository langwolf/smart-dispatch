package com.lioncorp.common.redis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import redis.clients.jedis.BinaryShardedJedis;
import redis.clients.jedis.BuilderFactory;
import redis.clients.jedis.Client;
import redis.clients.jedis.Queable;
import redis.clients.jedis.Response;

public class ExtendedShardedJedisPipeline extends Queable {
    private BinaryShardedJedis jedis;

    private List<FutureResult> results = new ArrayList<FutureResult>();

    private Queue<Client> clients = new LinkedList<Client>();

    private static class FutureResult {
        private Client client;

        public FutureResult(Client client) {
            this.client = client;
        }

        public Object get() {
            return client.getOne();
        }
    }

    public ExtendedShardedJedisPipeline() {}

    public ExtendedShardedJedisPipeline(BinaryShardedJedis jedis) {
        this.jedis = jedis;
    }

    public void setShardedJedis(BinaryShardedJedis jedis) {
        this.jedis = jedis;
    }

    public Response<String> set(String key, String value) {
        Client c = getClient(key);
        c.set(key, value);
        results.add(new FutureResult(c));
        return getResponse(BuilderFactory.STRING);
    }

    public Response<String> setex(byte[] key, int seconds, byte[] value) {
        Client c = getClient(key);
        c.setex(key, seconds, value);
        results.add(new FutureResult(c));
        return getResponse(BuilderFactory.STRING);
    }

    public Response<Long> setnx(String key, String value) {
        Client c = getClient(key);
        c.setnx(key, value);
        results.add(new FutureResult(c));
        return getResponse(BuilderFactory.LONG);
    }

    public Response<byte[]> getByte(byte[] key) {
        Client c = getClient(key);
        c.get(key);
        results.add(new FutureResult(c));
        return getResponse(BuilderFactory.BYTE_ARRAY);
    }

    public Response<String> get(String key) {
        Client c = getClient(key);
        c.get(key);
        results.add(new FutureResult(c));
        return getResponse(BuilderFactory.STRING);
    }

    public Response<Boolean> exists(String key) {
        Client c = getClient(key);
        c.exists(key);
        results.add(new FutureResult(c));
        return getResponse(BuilderFactory.BOOLEAN);
    }

    public Response<Long> expire(String key, int seconds) {
        Client c = getClient(key);
        c.expire(key, seconds);
        results.add(new FutureResult(c));
        return getResponse(BuilderFactory.LONG);
    }

    public Response<Long> expireAt(String key, long unixTime) {
        Client c = getClient(key);
        c.expireAt(key, unixTime);
        results.add(new FutureResult(c));
        return getResponse(BuilderFactory.LONG);
    }

    public Response<Long> ttl(String key) {
        Client c = getClient(key);
        c.ttl(key);
        results.add(new FutureResult(c));
        return getResponse(BuilderFactory.LONG);
    }

    public Response<Long> append(String key, String value) {
        Client c = getClient(key);
        c.append(key, value);
        results.add(new FutureResult(c));
        return getResponse(BuilderFactory.LONG);
    }

    public List<Object> getResults() {
        List<Object> r = new ArrayList<Object>();
        for (FutureResult fr: results) {
            r.add(fr.get());
        }
        return r;
    }

    /**
     * Syncronize pipeline by reading all responses. This operation closes the
     * pipeline. In order to get return values from pipelined commands, capture
     * the different Response&lt;?&gt; of the commands you execute.
     */
    public void sync() {
        for (Client client: clients) {
            generateResponse(client.getOne());
        }
    }

    /**
     * Syncronize pipeline by reading all responses. This operation closes the
     * pipeline. Whenever possible try to avoid using this version and use
     * ShardedJedisPipeline.sync() as it won't go through all the responses and
     * generate the right response type (usually it is a waste of time).
     * 
     * @return A list of all the responses in the order you executed them.
     */
    public List<Object> syncAndReturnAll() {
        List<Object> formatted = new ArrayList<Object>();
        for (Client client: clients) {
            formatted.add(generateResponse(client.getOne()).get());
        }
        return formatted;
    }

    /**
     * This method will be removed in Jedis 3.0. Use the methods that return
     * Response's and call sync().
     */
    @Deprecated
    public void execute() {}

    private Client getClient(byte[] key) {
        Client client = jedis.getShard(key).getClient();
        clients.add(client);
        return client;
    }

    private Client getClient(String key) {
        Client client = jedis.getShard(key).getClient();
        clients.add(client);
        return client;
    }
}
