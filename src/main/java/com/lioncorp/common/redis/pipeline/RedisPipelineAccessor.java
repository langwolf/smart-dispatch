package com.lioncorp.common.redis.pipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Response;

import com.lioncorp.common.redis.RedisAccessorBase;
import com.lioncorp.common.redis.RedisClusterContext;

public class RedisPipelineAccessor extends RedisAccessorBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisPipelineAccessor.class);

    private static final int MAX_QUEUE_LENGTH = 10000;

    private Queue<RedisCommand> commands;

    public RedisPipelineAccessor(RedisClusterContext context) {
        super("", context);
        commands = new LinkedList<>();
    }

    public String setex(String prefix, String key, String value, int expireSeconds) {
//        logWrite(prefix, key, value);
        addCommand(new RedisCommand<String>(Protocol.Command.SETEX, prefix + key, value, adjustExpireTime(expireSeconds)));
        if (shouldSync()) {
            sync();
        }
        return "";
    }

    public long delete(String prefix, String key) {
        logWrite(prefix, key, null);
        addCommand(new RedisCommand<Long>(Protocol.Command.DEL, prefix + key, null, 0));
        if (shouldSync()) {
            sync();
        }
        return 0;
    }

    public boolean sync() {
        final Queue<RedisCommand> imageForCommands = commands;
        commands = new LinkedList<>();

        if (CollectionUtils.isEmpty(imageForCommands)) {
            return true;
        }

        return new WriteCommandTemplateBoolean(StringUtils.EMPTY, 0, StringUtils.EMPTY) {
            @Override
            public Boolean write(JedisCluster client, String redisKey, int seconds) {
                RedisPipelineExecutor pipelineExecutor = new RedisPipelineExecutor(client);
                for (RedisCommand command : imageForCommands) {
                    command.response = pipelineExecutor.executeCommand(command);
                }
                pipelineExecutor.sync();

                for (RedisCommand command : imageForCommands) {
                    try {
                        command.response.get();
                    } catch (Exception ignored) {
                        executeCommand(client, command);
                    }
                }

                return true;
            }
        }.run();
    }

	public <T> List<T> readByPip(String prefix, String[] keys) {
		if(ArrayUtils.isEmpty(keys) || StringUtils.isBlank(prefix))
			return Collections.emptyList();
		
		List<T> ts = new ArrayList<T>(keys.length);
		List<Response<String>> responses = new ArrayList<Response<String>>(
				keys.length);
		RedisPipelineExecutor pipelineExecutor = new RedisPipelineExecutor(
				getCluster());
		for (String key : keys) {
			Response<String> tmp = pipelineExecutor
					.executeCommand(new RedisCommand<String>(
							Protocol.Command.GET, prefix + key, "", 0));
			if(null != tmp){
				responses.add(tmp);
			}
		}
		pipelineExecutor.sync();
		for (Response response : responses) {
			if(null != response) {
				T t = (T) (String) response.get();
				ts.add(t);
			}
		}
		return ts;
	}
    
	public <T> List<T> existsByPip(String prefix, String[] keys) {
		if(ArrayUtils.isEmpty(keys) || StringUtils.isBlank(prefix))
			return Collections.emptyList();
		
		List<T> ts = new ArrayList<T>(keys.length);
		List<Response<Boolean>> responses = new ArrayList<Response<Boolean>>(
				keys.length);
		RedisPipelineExecutor pipelineExecutor = new RedisPipelineExecutor(
				getCluster());
		for (String key : keys) {
			responses.add(pipelineExecutor
					.executeCommand(new RedisCommand<Boolean>(
							Protocol.Command.EXISTS, prefix + key, "", 0)));
		}
		pipelineExecutor.sync();
		for (Response response : responses) {
			T t = (T) (Boolean) response.get();
			ts.add(t);
		}
		return ts;
	}
    
    private void executeCommand(JedisCluster client, RedisCommand command) {
        try {
            switch (command.op) {
                case SETEX:
                    client.setex(command.key, command.expireTime, command.value);
                    break;
                case DEL:
                    client.del(command.key);
                    break;
                default:
                    throw new RuntimeException("Unsupported Redis command!");
            }
        } catch (Exception e) {
        	LOGGER.error("Execute Redis command failure! key:{}, value:{}", 
        			command.key, command.expireTime);
            LOGGER.error("Execute Redis command failure!", e);
        }
    }

    private void addCommand(RedisCommand redisCommand) {
        commands.add(redisCommand);
    }

    private boolean shouldSync() {
        return commands.size() >= MAX_QUEUE_LENGTH;
    }

}
