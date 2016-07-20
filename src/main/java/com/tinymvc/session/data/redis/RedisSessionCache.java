package com.tinymvc.session.data.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinymvc.session.data.SerializeUtil;
import com.tinymvc.session.data.SessionCache;
import com.tinymvc.session.data.SessionMap;

import redis.clients.jedis.Jedis;

public class RedisSessionCache implements SessionCache{
	
	private static final Logger LOG =LoggerFactory.getLogger(RedisSessionCache.class);
	
	private RedisSentinelPool cachePool;
	
	@Override
	public void put(String sessionId, SessionMap sessionMap, int timeout) {
		Jedis jedis = null;
		try {
			jedis = cachePool.getResource();
            jedis.set(sessionId.getBytes(), SerializeUtil.serialize(sessionMap));
            jedis.expire(sessionId, timeout);
		} catch (Exception e) {
			LOG.error("Put session to redis error", e);
		} finally {
			jedis.close();
		}
	}

	@Override
	public SessionMap get(String sessionId) {
		Jedis jedis = null;
		SessionMap sessionMap = null;
		byte[] reslut = null;
        try {
            jedis = cachePool.getResource();
            if (jedis.exists(sessionId)) {
                reslut = jedis.get(sessionId.getBytes());
                sessionMap = (SessionMap) SerializeUtil.unserialize(reslut);
            }
        } catch (Exception e) {
        	LOG.error("Read session from redis error", e);
            return null;
        } finally {
        	jedis.close();
        }
        return sessionMap;
	}

	@Override
	public void setMaxInactiveInterval(String sessionId, int interval) {
		Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            if (jedis.exists(sessionId)) {
            	jedis.expire(sessionId, interval);
            }
        } catch (Exception e) {
        	LOG.error("Set session max inactive interval to redis error", e);
        } finally {
        	jedis.close();
        }
	}

	@Override
	public void destroy(String sessionId) {
		Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            if (jedis.exists(sessionId)) {
            	jedis.expire(sessionId, 0);
            }
        } catch (Exception e) {
        	LOG.error("Destroy session from redis error", e);
        } finally {
        	jedis.close();
        }

	}

	public void setCachePool(RedisSentinelPool cachePool) {
		this.cachePool = cachePool;
	}

	

}
