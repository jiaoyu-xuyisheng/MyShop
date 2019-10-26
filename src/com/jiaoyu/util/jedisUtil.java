package com.jiaoyu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class jedisUtil {
	 private static JedisPool pool=null;
	static {
		InputStream in=jedisUtil.class.getClassLoader().getResourceAsStream("redis.properties");
		Properties pro=new Properties();
		try {
			pro.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	JedisPoolConfig poocon=new JedisPoolConfig();
	poocon.setMaxIdle(Integer.parseInt( pro.get("maxIdle").toString()));//最大闲置个数Integer.parseInt(pro.get("redis.maxIdle").toString())
	poocon.setMinIdle(Integer.parseInt(pro.get("minIdle").toString()));//最小闲置个数
	poocon.setMaxTotal(Integer.parseInt(pro.get("maxTotal").toString()));
	//2.创建一个reds的连接池
	 pool=new JedisPool(poocon,pro.getProperty("url").toString(),Integer.parseInt( pro.get("port").toString()));
	}
	
	public static Jedis getPool() {
		return pool.getResource();
	}
	
	public static void main(String[] args) {
		Jedis myjedis=getPool();
		myjedis.get("username");
	}
	
	
}
